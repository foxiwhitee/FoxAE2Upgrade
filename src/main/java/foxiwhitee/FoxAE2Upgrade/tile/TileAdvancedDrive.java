package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.implementations.tiles.IChestOrDrive;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.*;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.helpers.IPriorityHost;
import appeng.items.storage.ItemBasicStorageCell;
import appeng.me.GridAccessException;
import appeng.me.storage.MEInventoryHandler;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkInvTile;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static appeng.tile.storage.TileDrive.*;

public class TileAdvancedDrive extends AENetworkInvTile implements IChestOrDrive, IPriorityHost, IGridTickable {
    private final int[] sides = new int[0];

    private final AppEngInternalInventory inv = new AppEngInternalInventory(this, 30);

    private final ICellHandler[] handlersBySlot = new ICellHandler[30];

    private final MEInventoryHandler<IAEItemStack>[] invBySlot = new MEInventoryHandler[30];

    private final BaseActionSource mySrc;

    private boolean isCached = false;

    private List<MEInventoryHandler<?>> items = new LinkedList<>();

    private List<MEInventoryHandler<?>> fluids = new LinkedList<>();

    private final int[] state = new int[30];
    private final int[] cellType = new int[30];

    private int priority = 0;

    private boolean wasActive = false;

    public TileAdvancedDrive() {
        this.mySrc = new MachineSource(this);
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        Arrays.fill(cellType, -1);
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode node) {
        return new TickingRequest(15, 15, false, false);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        this.recalculateDisplay();
        return TickRateModulation.SAME;
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream_TileDrive(ByteBuf data) {
        for (int i = 0; i < getCellCount(); i++) {
            state[i] = getCellStatus(i);
            cellType[i] = getCellType(i);
            data.writeInt(state[i]);
            data.writeInt(cellType[i]);
        }
    }

    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream_TileDrive(ByteBuf data) {
        int[] oldState = Arrays.copyOf(state, state.length);
        int[] oldCellType = Arrays.copyOf(cellType, state.length);
        boolean changed = false;
        for (int i = 0; i < getCellCount(); i++) {
            state[i] = data.readInt();
            cellType[i] = data.readInt();
            changed |= state[i] != oldState[i] || cellType[i] != oldCellType[i];
        }
        return changed;
    }

    public int getCellCount() {
        return 30;
    }

    public int getCellStatus(int slot) {
        if (Platform.isClient()) {
            return this.state[slot];
        }
        ItemStack cell = this.inv.getStackInSlot(slot);
        ICellHandler ch = this.handlersBySlot[slot];
        MEInventoryHandler<IAEItemStack> handler = this.invBySlot[slot];
        if (handler == null || !isPowered()) {
            return 0;
        }
        if ((handler.getChannel() == StorageChannel.ITEMS || handler.getChannel() == StorageChannel.FLUIDS) && ch != null) {
            return ch.getStatusForCell(cell, handler.getInternal());
        }
        return 0;
    }

    @Override
    public int getCellType(int slot) {
        if (Platform.isClient()) {
            return cellType[slot];
        }

        MEInventoryHandler<IAEItemStack> handler = this.invBySlot[slot];
        if (handler == null) {
            return -1;
        }
        if (handler.getInternal() instanceof ICellCacheRegistry iccr) {
            return switch (iccr.getCellType()) {
                case ITEM -> 0;
                case FLUID -> 1;
                case ESSENTIA -> 2;
            };
        }
        return -1;
    }

    public boolean isPowered() {
        return getProxy().isActive();
    }

    public boolean toggleItemStorageCellLocking() {
        boolean res = false;

        for (int i = 0; i < this.handlersBySlot.length; ++i) {
            ICellHandler cellHandler = this.handlersBySlot[i];
            ItemStack cell = this.inv.getStackInSlot(i);
            if (!ItemBasicStorageCell.checkInvalidForLockingAndStickyCarding(cell, cellHandler)) {
                IMEInventoryHandler<?> inv = cellHandler.getCellInventory(cell, this, StorageChannel.ITEMS);
                if (inv instanceof ICellInventoryHandler<?> handler) {
                    if (ItemBasicStorageCell.cellIsPartitioned(handler)) {
                        unpartitionStorageCell(handler);
                    } else {
                        partitionStorageCellToItemsOnCell(handler);
                    }

                    res = true;
                }
            }
        }

        if (this.isCached) {
            this.isCached = false;
            this.updateState();
        }

        try {
            this.getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
        } catch (GridAccessException ignored) {
        }

        return res;
    }

    public int applyStickyToItemStorageCells(ItemStack cards) {
        int res = 0;

        for (int i = 0; i < this.handlersBySlot.length; ++i) {
            ICellHandler cellHandler = this.handlersBySlot[i];
            ItemStack cell = this.inv.getStackInSlot(i);
            if (!ItemBasicStorageCell.checkInvalidForLockingAndStickyCarding(cell, cellHandler)) {
                Item var7 = cell.getItem();
                if (var7 instanceof ICellWorkbenchItem cellItem) {
                    if (res + 1 <= cards.stackSize && applyStickyCardToItemStorageCell(cellHandler, cell, this, cellItem)) {
                        ++res;
                    }
                }
            }
        }

        if (this.isCached) {
            this.isCached = false;
            this.updateState();
        }

        try {
            this.getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
        } catch (GridAccessException ignored) {
        }

        return res;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_TileDrive(NBTTagCompound data) {
        data.setInteger("priority", this.priority);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_TileDrive(NBTTagCompound data) {
        this.isCached = false;
        this.priority = data.getInteger("priority");
    }

    @MENetworkEventSubscribe
    public void powerRender(MENetworkPowerStatusChange c) {
        recalculateDisplay();
    }

    private void recalculateDisplay() {
        if (Platform.isServer()) {
            boolean currentActive = getProxy().isActive();
            if (this.wasActive != currentActive) {
                this.wasActive = currentActive;
                try {
                    getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
                } catch (GridAccessException ignored) {
                }
            }
            markForUpdate();
        }
    }

    @MENetworkEventSubscribe
    public void channelRender(MENetworkChannelsChanged c) {
        if (Platform.isServer()) {
            recalculateDisplay();
        }
    }

    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.SMART;
    }

    public DimensionalCoord getLocation() {
        return new DimensionalCoord(this);
    }

    public IInventory getInternalInventory() {
        return this.inv;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return (itemstack != null && AEApi.instance().registries().cell().isCellHandled(itemstack));
    }

    public void onChangeInventory(IInventory inv, int slot, InvOperation mc, ItemStack removed, ItemStack added) {
        if (this.isCached) {
            this.isCached = false;
            updateState();
        }
        try {
            getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
            IStorageGrid gs = getProxy().getStorage();
            Platform.postChanges(gs, removed, added, this.mySrc);
        } catch (GridAccessException ignored) {
        }
        markForUpdate();
    }

    public int[] getAccessibleSlotsBySide(ForgeDirection side) {
        return this.sides;
    }

    private void updateState() {
        if (!this.isCached) {
            this.items = new LinkedList<>();
            this.fluids = new LinkedList<>();
            double power = 2;
            for (int x = 0; x < this.inv.getSizeInventory(); x++) {
                ItemStack is = this.inv.getStackInSlot(x);
                this.invBySlot[x] = null;
                this.handlersBySlot[x] = null;
                if (is != null) {
                    this.handlersBySlot[x] = AEApi.instance().registries().cell().getHandler(is);
                    if (this.handlersBySlot[x] != null) {
                        IMEInventoryHandler<?> cell = this.handlersBySlot[x].getCellInventory(is, this, StorageChannel.ITEMS);
                        if (cell != null) {
                            power += this.handlersBySlot[x].cellIdleDrain(is, cell);
                            MEInventoryHandler<IAEItemStack> ih = new MEInventoryHandler<>(cell, cell.getChannel());
                            ih.setPriority(this.priority);
                            this.invBySlot[x] = ih;
                            this.items.add(ih);
                        } else {
                            cell = this.handlersBySlot[x].getCellInventory(is, this, StorageChannel.FLUIDS);
                            if (cell != null) {
                                power += this.handlersBySlot[x].cellIdleDrain(is, cell);
                                MEInventoryHandler<IAEItemStack> ih = new MEInventoryHandler<>(cell, cell.getChannel());
                                ih.setPriority(this.priority);
                                this.invBySlot[x] = ih;
                                this.fluids.add(ih);
                            }
                        }
                    }
                }
            }
            getProxy().setIdlePowerUsage(power);
            this.isCached = true;
        }
    }

    public void onReady() {
        super.onReady();
        updateState();
    }

    public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
        if (getProxy().isActive()) {
            updateState();
            return (channel == StorageChannel.ITEMS) ? (List) this.items : (List) this.fluids;
        }
        return new ArrayList<>();
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int newValue) {
        this.priority = newValue;
        markDirty();
        this.isCached = false;
        updateState();
        try {
            getProxy().getGrid().postEvent(new MENetworkCellArrayUpdate());
        } catch (GridAccessException ignored) {
        }
    }

    public void saveChanges(IMEInventory cellInventory) {
        this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
    }
}
