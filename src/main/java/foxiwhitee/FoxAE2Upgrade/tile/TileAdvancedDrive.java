package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.implementations.tiles.IChestOrDrive;
import appeng.api.networking.GridFlags;
import appeng.api.networking.events.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
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
import appeng.tile.inventory.IAEAppEngInventory;
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
import java.util.LinkedList;
import java.util.List;

import static appeng.tile.storage.TileDrive.*;

public class TileAdvancedDrive extends AENetworkInvTile implements IChestOrDrive, IPriorityHost {
    private final int[] sides = new int[0];

    private final AppEngInternalInventory inv = new AppEngInternalInventory(this, 30);

    private final ICellHandler[] handlersBySlot = new ICellHandler[30];

    private final MEInventoryHandler<IAEItemStack>[] invBySlot = new MEInventoryHandler[30];

    private final BaseActionSource mySrc;

    private boolean isCached = false;

    private List<MEInventoryHandler<?>> items = new LinkedList<>();

    private List<MEInventoryHandler<?>> fluids = new LinkedList<>();

    private long lastStateChange = 0L;

    private final int[] state = new int[]{0, 0, 0};

    private int priority = 0;

    private boolean wasActive = false;

    public TileAdvancedDrive() {
        this.mySrc = new MachineSource(this);
        getProxy().setFlags(new GridFlags[]{GridFlags.REQUIRE_CHANNEL});
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream_TileDrive(ByteBuf data) {
        if (Platform.isServer()) {
            if (this.worldObj.getTotalWorldTime() - this.lastStateChange > 8L) {
                for (int j = 0; j < 3; j++)
                    this.state[j] = 0;
            } else {
                for (int j = 0; j < 3; j++)
                    this.state[j] = this.state[j] & 0x24924924;
            }
            if (getProxy().isActive()) {
                this.state[0] = this.state[0] | Integer.MIN_VALUE;
            } else {
                this.state[0] = this.state[0] & Integer.MAX_VALUE;
            }
            for (int x = 0; x < getCellCount(); x++) {
                int j = x / 10;
                this.state[j] = this.state[j] | getCellStatus(x) << (3 * (x % 10));
            }
            for (int i = 0; i < 3; i++)
                data.writeInt(this.state[i]);
        }
    }

    public int getCellCount() {
        return 30;
    }

    public int getCellStatus(int slot) {
        if (Platform.isClient())
            return this.state[slot / 10] >> slot % 10 * 3 & 0x3;
        ItemStack cell = this.inv.getStackInSlot(2);
        ICellHandler ch = this.handlersBySlot[slot];
        MEInventoryHandler<IAEItemStack> handler = this.invBySlot[slot];
        if (handler == null)
            return 0;
        if (handler.getChannel() == StorageChannel.ITEMS &&
            ch != null)
            return ch.getStatusForCell(cell, handler.getInternal());
        if (handler.getChannel() == StorageChannel.FLUIDS &&
            ch != null)
            return ch.getStatusForCell(cell, handler.getInternal());
        return 0;
    }

    public boolean isPowered() {
        if (Platform.isClient())
            return ((this.state[0] & Integer.MIN_VALUE) == Integer.MIN_VALUE);
        return getProxy().isActive();
    }

    public boolean toggleItemStorageCellLocking() {
        boolean res = false;

        for (int i = 0; i < this.handlersBySlot.length; ++i) {
            ICellHandler cellHandler = this.handlersBySlot[i];
            ItemStack cell = this.inv.getStackInSlot(i);
            if (!ItemBasicStorageCell.checkInvalidForLockingAndStickyCarding(cell, cellHandler)) {
                IMEInventoryHandler<?> inv = cellHandler.getCellInventory(cell, this, StorageChannel.ITEMS);
                if (inv instanceof ICellInventoryHandler) {
                    ICellInventoryHandler handler = (ICellInventoryHandler) inv;
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
        } catch (GridAccessException var7) {
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
                if (var7 instanceof ICellWorkbenchItem) {
                    ICellWorkbenchItem cellItem = (ICellWorkbenchItem) var7;
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
        } catch (GridAccessException var8) {
        }

        return res;
    }

    public boolean isCellBlinking(int slot) {
        long now = this.worldObj.getTotalWorldTime();
        if (now - this.lastStateChange > 8L)
            return false;
        int i = slot / 10;
        return ((this.state[i] >> slot % 10 * 3 + 2 & 0x1) == 1);
    }

    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream_TileDrive(ByteBuf data) {
        int[] oldState = new int[3];
        System.arraycopy(this.state, 0, oldState, 0, 3);
        int i;
        for (i = 0; i < 3; i++)
            this.state[i] = data.readInt();
        this.lastStateChange = this.worldObj.getTotalWorldTime();
        for (i = 0; i < 3; i++) {
            if ((this.state[i] & 0xDB6DB6DB) != (oldState[i] & 0xDB6DB6DB))
                return true;
        }
        return false;
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_TileDrive(NBTTagCompound data) {
        if (Platform.isServer()) {
            this.isCached = false;
            this.priority = data.getInteger("priority");
        }
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_TileDrive(NBTTagCompound data) {
        if (Platform.isServer()) {
            data.setInteger("priority", this.priority);
        }
    }

    @MENetworkEventSubscribe
    public void powerRender(MENetworkPowerStatusChange c) {
        if (Platform.isServer()) {
            recalculateDisplay();
        }
    }

    private void recalculateDisplay() {
        if (Platform.isServer()) {
            boolean currentActive = getProxy().isActive();
            if (currentActive) {
                this.state[0] = this.state[0] | Integer.MIN_VALUE;
            } else {
                this.state[0] = this.state[0] & Integer.MAX_VALUE;
            }
            if (this.wasActive != currentActive) {
                this.wasActive = currentActive;
                try {
                    getProxy().getGrid().postEvent((MENetworkEvent) new MENetworkCellArrayUpdate());
                } catch (GridAccessException gridAccessException) {
                }
            }
            for (int x = 0; x < getCellCount(); x++)
                this.state[x / 10] = this.state[x / 10] | getCellStatus(x) << 3 * x % 10;
            int oldState = 0;
            for (int i = 0; i < 3; i++) {
                if (0 != this.state[i]) {
                    markForUpdate();
                    return;
                }
            }
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
        return new DimensionalCoord((TileEntity) this);
    }

    public IInventory getInternalInventory() {
        return (IInventory) this.inv;
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
            getProxy().getGrid().postEvent((MENetworkEvent) new MENetworkCellArrayUpdate());
            IStorageGrid gs = getProxy().getStorage();
            Platform.postChanges(gs, removed, added, this.mySrc);
        } catch (GridAccessException gridAccessException) {
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
            double power = 2.0D;
            for (int x = 0; x < this.inv.getSizeInventory(); x++) {
                ItemStack is = this.inv.getStackInSlot(x);
                this.invBySlot[x] = null;
                this.handlersBySlot[x] = null;
                if (is != null) {
                    this.handlersBySlot[x] = AEApi.instance().registries().cell().getHandler(is);
                    if (this.handlersBySlot[x] != null) {
                        IMEInventoryHandler<?> cell = this.handlersBySlot[x].getCellInventory(is, (ISaveProvider) this, StorageChannel.ITEMS);
                        if (cell != null) {
                            power += this.handlersBySlot[x].cellIdleDrain(is, (IMEInventory) cell);
                            MEInventoryHandler<IAEItemStack> ih = new MEInventoryHandler(cell, cell.getChannel());
                            ih.setPriority(this.priority);
                            this.invBySlot[x] = ih;
                            this.items.add(ih);
                        } else {
                            cell = this.handlersBySlot[x].getCellInventory(is, (ISaveProvider) this, StorageChannel.FLUIDS);
                            if (cell != null) {
                                power += this.handlersBySlot[x].cellIdleDrain(is, (IMEInventory) cell);
                                MEInventoryHandler<IAEItemStack> ih = new MEInventoryHandler(cell, cell.getChannel());
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
            getProxy().getGrid().postEvent((MENetworkEvent) new MENetworkCellArrayUpdate());
        } catch (GridAccessException gridAccessException) {
        }
    }

    public void blinkCell(int slot) {
        long now = this.worldObj.getTotalWorldTime();
        if (now - this.lastStateChange > 8L)
            for (int i = 0; i < 3; i++)
                this.state[i] = 0;
        this.lastStateChange = now;
        this.state[slot / 10] = this.state[slot / 10] | 1 << slot % 10 * 3 + 2;
        if (!this.worldObj.isRemote)
            recalculateDisplay();
    }

    public void saveChanges(IMEInventory cellInventory) {
        this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, (TileEntity) this);
    }
}
