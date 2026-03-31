package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.config.*;
import appeng.api.implementations.tiles.IMEChest;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCellArrayUpdate;
import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.ISecurityGrid;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.security.PlayerSource;
import appeng.api.networking.storage.IBaseMonitor;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.*;
import appeng.api.storage.data.*;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.api.util.IConfigManager;
import appeng.helpers.IPriorityHost;
import appeng.items.storage.ItemBasicStorageCell;
import appeng.me.GridAccessException;
import appeng.me.storage.MEInventoryHandler;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.storage.TileDrive;
import appeng.util.ConfigManager;
import appeng.util.IConfigManagerHost;
import appeng.util.Platform;
import appeng.util.item.AEItemStack;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.integration.applied.tile.TileNetworkInv;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static appeng.util.item.AEItemStackType.ITEM_STACK_TYPE;

@SuppressWarnings("unused")
public class TileCobblestoneDuper extends TileNetworkInv implements IMEChest, IPriorityHost, IConfigManagerHost {
    private static final int[] NO_SLOTS = {};
    private final FoxInternalInventory inventory = new FoxInternalInventory(this, 1);
    private final MachineSource source = new MachineSource(this);
    private final IConfigManager settings = new ConfigManager(this);
    private ItemStack cellType;
    private long lastBlinkTime;
    private int status;
    private boolean isActive;
    private boolean isHandlerCached;
    private ICellHandler cellHandler;
    private MEMonitorHandler<? extends IAEStack<?>> monitor;
    private int priority;
    private int tick;

    public TileCobblestoneDuper() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        settings.registerSetting(Settings.SORT_BY, SortOrder.NAME);
        settings.registerSetting(Settings.VIEW_MODE, ViewItems.ALL);
        settings.registerSetting(Settings.SORT_DIRECTION, SortDir.ASCENDING);
    }

    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.cobblestoneDuper);
    }

    private void updateStatus() {
        int prevStatus = status;
        status = getCellStatus(0);
        status |= isPowered() ? 0x40 : 0;
        boolean currentActive = getProxy().isActive();
        if (isActive != currentActive) {
            isActive = currentActive;
            try {
                getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
            } catch (GridAccessException ignored) {
            }
        }
        if (prevStatus != status) {
            markForUpdate();
        }
    }

    public int getCellCount() {
        return 1;
    }

    @SuppressWarnings("unchecked")
    private <T extends IAEStack<T>> IMEInventoryHandler<T> getInventoryHandler(IAEStackType<T> stackType) throws NoHandlerException {
        if (!isHandlerCached) {
            monitor = null;
            ItemStack cell = inventory.getStackInSlot(0);
            if (cell != null) {
                isHandlerCached = true;
                cellHandler = AEApi.instance().registries().cell().getHandler(cell);
                if (cellHandler != null) {
                    double power = 1.0;
                    IMEInventoryHandler<T> handler = cellHandler.getCellInventory(cell, this, stackType);
                    getProxy().setIdlePowerUsage(power);
                    monitor = createMonitor(handler, stackType);
                }
            }
        }
        if (monitor != null) {
            return (IMEInventoryHandler<T>) monitor;
        } else {
            throw new NoHandlerException();
        }
    }

    private <T extends IAEStack<T>> MEMonitorHandler<T> createMonitor(IMEInventoryHandler<T> handler, IAEStackType<T> stackType) {
        if (handler == null) return null;
        MEInventoryHandler<T> internal = new MEInventoryHandler<>(handler, stackType);
        internal.setPriority(priority);
        MEMonitorHandler<T> monitor = new StorageMonitor<>(internal);
        monitor.addListener(new StorageNotifier<>(stackType), monitor);
        return monitor;
    }

    @SuppressWarnings("unchecked")
    public int getCellStatus(int slot) {
        if (Platform.isClient()) {
            return status >> (slot * 3) & 0x3;
        }
        ItemStack cell = inventory.getStackInSlot(0);
        ICellHandler handler = AEApi.instance().registries().cell().getHandler(cell);
        if (handler != null) {
            for (IAEStackType<?> internalType : AEStackTypeRegistry.getAllTypes()) {
                try {
                    IMEInventoryHandler<?> inv = getInventoryHandler(internalType);
                    if (inv instanceof StorageMonitor<?> storageMonitor) {
                        return handler.getStatusForCell(cell, storageMonitor.getInternal());
                    }
                } catch (NoHandlerException ignored) {}
            }
        }
        return 0;
    }

    public boolean isPowered() {
        return Platform.isClient() ? (status & 0x40) == 0x40 : getProxy().isPowered();
    }

    public boolean toggleItemStorageCellLocking() {
        ItemStack cell = this.inventory.getStackInSlot(0);
        if (ItemBasicStorageCell.checkInvalidForLockingAndStickyCarding(cell, this.cellHandler)) {
            return false;
        } else {
            IMEInventoryHandler<?> inv = this.cellHandler.getCellInventory(cell, this, ITEM_STACK_TYPE);
            if (inv instanceof ICellInventoryHandler<?> handler) {
                if (ItemBasicStorageCell.cellIsPartitioned(handler)) {
                    TileDrive.unpartitionStorageCell(handler);
                } else {
                    TileDrive.partitionStorageCellToItemsOnCell(handler);
                }

                try {
                    this.getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
                } catch (GridAccessException ignored) {
                }
            }

            return true;
        }
    }

    public int applyStickyToItemStorageCells(ItemStack cards) {
        ItemStack cell = this.inventory.getStackInSlot(0);
        if (ItemBasicStorageCell.checkInvalidForLockingAndStickyCarding(cell, this.cellHandler) && cards.stackSize != 0) {
            return 0;
        } else {
            Item var4 = cell.getItem();
            if (var4 instanceof ICellWorkbenchItem cellItem) {
                if (TileDrive.applyStickyCardToItemStorageCell(this.cellHandler, cell, this, cellItem)) {
                    try {
                        this.getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
                    } catch (GridAccessException ignored) {
                    }
                    return 1;
                }
            }
            return 0;
        }
    }

    @TileEvent(TileEventType.TICK)
    public void onTick() {
        if (Platform.isServer()) {
            double idlePower = getProxy().getIdlePowerUsage();
            try {
                if (!getProxy().getEnergy().isNetworkPowered()) {
                    double used = extractAEPower(idlePower, Actionable.MODULATE, PowerMultiplier.CONFIG);
                    if ((used + 0.1 >= idlePower) != ((status & 0x40) > 0)) updateStatus();
                }
            } catch (GridAccessException e) {
                double used = extractAEPower(idlePower, Actionable.MODULATE, PowerMultiplier.CONFIG);
                if ((used + 0.1 >= idlePower) != ((status & 0x40) > 0)) updateStatus();
            }
            if (inventory.getStackInSlot(0) != null) {
                try {
                    IMEInventoryHandler<IAEItemStack> handler = getInventoryHandler(ITEM_STACK_TYPE);
                    if (handler != null && tick++ >= 20 * FoxConfig.cobblestoneDuperSecondsNeed) {
                        tick = 0;
                        storeContents();
                    }
                } catch (Exception ignored) {}
            }
            markForUpdate();
        }
    }

    @Override
    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeInt(tick);
        status = worldObj.getTotalWorldTime() - lastBlinkTime > 8 ? 0 : status & 0x24924924;
        status |= getCellStatus(0);
        status |= isPowered() ? 0x40 : 0;
        data.writeByte(status);
        ItemStack cell = inventory.getStackInSlot(0);
        data.writeInt(cell == null ? 0 : (cell.getItemDamage() << 16) | Item.getIdFromItem(cell.getItem()));
    }

    @Override
    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean result = super.readFromStream(data);
        tick = data.readInt();
        int prevStatus = status;
        ItemStack prevCell = cellType;
        status = data.readByte();
        int item = data.readInt();
        cellType = item == 0 ? null : new ItemStack(Item.getItemById(item & 0xFFFF), 1, item >> 16);
        lastBlinkTime = worldObj.getTotalWorldTime();
        return result || (status & 0xDB6DB6DB) != (prevStatus & 0xDB6DB6DB) || !Platform.isSameItemPrecise(prevCell, cellType);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBTC(NBTTagCompound data) {
        settings.readFromNBT(data);
        inventory.readFromNBT(data, "inv");
        priority = data.getInteger("priority");
        tick = data.getInteger("tick");
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBTC(NBTTagCompound data) {
        settings.writeToNBT(data);
        inventory.writeToNBT(data, "inv");
        data.setInteger("priority", priority);
        data.setInteger("tick", tick);
    }

    @MENetworkEventSubscribe
    public void onPowerChange(MENetworkPowerStatusChange event) {
        updateStatus();
    }

    @MENetworkEventSubscribe
    public void onChannelChange(MENetworkChannelsChanged event) {
        updateStatus();
    }

    public FoxInternalInventory getInternalInventory() {
        return inventory;
    }

    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory.setInventorySlotContents(slot, stack);
        storeContents();
    }

    public void onChangeInventory(IInventory inv, int slot, InvOperation op, ItemStack removed, ItemStack added) {
        if (slot == 0) {
            tick = 0;
            monitor = null;
            isHandlerCached = false;
            try {
                if (getProxy().getGrid() != null) {
                    getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
                }
                IStorageGrid storage = getProxy().getStorage();
                Platform.postChanges(storage, removed, added, source);
            } catch (GridAccessException ignored) {
            }
            markForUpdate();
        }
    }

    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 1;
    }

    public int[] getAccessibleSlotsBySide(ForgeDirection side) {
        return NO_SLOTS;
    }

    private void storeContents() {
        try {
            IMEInventoryHandler<IAEItemStack> handler = getInventoryHandler(ITEM_STACK_TYPE);
            assert handler != null;
            IAEItemStack cobble = AEItemStack.create(new ItemStack(Blocks.cobblestone));
            cobble.setStackSize(FoxConfig.cobblestoneDuperItemsGenerated);
            IAEItemStack result = Platform.poweredInsert(this, handler, cobble, source);
        } catch (NoHandlerException ignored) {
        }
    }

    @Nonnull
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<IMEInventoryHandler> getCellArray(IAEStackType<?> stackType) {
        if (getProxy().isActive()) {
            try {
                IMEInventoryHandler handler = getInventoryHandler(stackType);
                if (handler == null || handler.getStackType() != stackType) {
                    return new ArrayList<>();
                }
                return Collections.singletonList(handler);
            } catch (NoHandlerException ignored) {
            }
        }
        return new ArrayList<>();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int value) {
        priority = value;
        monitor = null;
        isHandlerCached = false;
        try {
            getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
        } catch (GridAccessException ignored) {
        }
    }

    public IStorageMonitorable getMonitorable(ForgeDirection side, BaseActionSource src) {
        return null;
    }

    public void updateSetting(IConfigManager manager, Enum setting, Enum value) {
    }

    public void saveChanges(IMEInventory inventory) {
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    public double extractAEPower(double amount, Actionable mode, PowerMultiplier multiplier) {
        return 200000.0;
    }

    public DimensionalCoord getLocation() {
        return new DimensionalCoord(worldObj, xCoord, yCoord, zCoord);
    }

    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.SMART;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null) {
                drops.add(stack);
            }
        }
    }

    private static class NoHandlerException extends Exception {
    }

    private class StorageNotifier<T extends IAEStack<T>> implements IMEMonitorHandlerReceiver<T> {
        private final IAEStackType<?> stackType;

        StorageNotifier(IAEStackType<?> stackType) {
            this.stackType = stackType;
        }

        public boolean isValid(Object token) {
            return token == monitor;
        }

        public void postChange(IBaseMonitor<T> monitor, Iterable<T> changes, BaseActionSource src) {
            if (src == source || (src instanceof PlayerSource && ((PlayerSource) src).via == TileCobblestoneDuper.this)) {
                try {
                    if (getProxy().isActive()) {
                        getProxy().getStorage().postAlterationOfStoredItems(stackType, changes, source);
                    }
                } catch (GridAccessException ignored) {
                }
            }
        }

        public void onListUpdate() {
        }
    }

    private class StorageMonitor<T extends IAEStack<T>> extends MEMonitorHandler<T> {
        StorageMonitor(IMEInventoryHandler<T> handler) {
            super(handler);
        }

        public IMEInventoryHandler<T> getInternal() {
            IMEInventoryHandler<T> handler = getHandler();
            return handler instanceof MEInventoryHandler ? (IMEInventoryHandler<T>)(handler).getInternal() : handler;
        }

        public T extractItems(T request, Actionable mode, BaseActionSource src) {
            if (src.isPlayer() && !hasPermission(((PlayerSource) src).player)) {
                return null;
            }
            if (request instanceof IAEItemStack && ((IAEItemStack) request).getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
                return request;
            }
            return super.extractItems(request, mode, src);
        }

        private boolean hasPermission(EntityPlayer player) {
            IGridNode node = getActionableNode();
            if (node == null) return false;
            IGrid grid = node.getGrid();
            if (grid == null) return false;
            ISecurityGrid security = grid.getCache(ISecurityGrid.class);
            return security.hasPermission(player, SecurityPermissions.EXTRACT);
        }
    }

    public int getTick() {
        return tick;
    }

}
