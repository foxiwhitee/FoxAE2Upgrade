package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.api.config.Actionable;
import appeng.api.config.PowerMultiplier;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.implementations.IPowerChannelState;
import appeng.api.implementations.tiles.ICraftingMachine;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProvider;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.api.networking.events.MENetworkEvent;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.api.util.IInterfaceViewable;
import appeng.container.ContainerNull;
import appeng.crafting.MECraftingInventory;
import appeng.me.GridAccessException;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.grid.AENetworkInvTile;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import appeng.util.item.AEItemStack;
import foxiwhitee.FoxLib.api.orientable.FastOrientableManager;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingCPUClusterAccessor;
import foxiwhitee.FoxLib.integration.applied.api.crafting.IPreCraftingMedium;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class TileCustomMolecularAssembler extends AENetworkInvTile implements IPowerChannelState, ICraftingMachine, ICraftingProvider, IGridTickable, IInterfaceViewable, IPreCraftingMedium, IOrientable {
    private static final int[] SIDES = {0};
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 36, 1);
    ICraftingPatternDetails activePattern;
    long craftCount;
    InventoryCrafting craftingGrid;
    List<ICraftingPatternDetails> patternList;
    private boolean isPowered;

    private final int orientableId = FastOrientableManager.nextId();

    protected abstract ItemStack getItemFromTile(Object obj);

    public TileCustomMolecularAssembler() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode node) {
        return new TickingRequest(1, 1, false, false);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        if (activePattern == null) return TickRateModulation.IDLE;
        List<IAEItemStack> outputs = new ArrayList<>();
        Arrays.stream(activePattern.getCondensedAEOutputs())
            .map(stack -> {
                IAEItemStack copy = (IAEItemStack) stack.copy();
                copy.setStackSize(copy.getStackSize() * craftCount);
                return copy;
            }).forEach(outputs::add);
        for (int i = 0; i < craftingGrid.getSizeInventory(); i++) {
            ItemStack item = Platform.getContainerItem(craftingGrid.getStackInSlot(i));
            if (item != null) {
                outputs.add(AEItemStack.create(item));
            }
        }
        boolean canCraft = true;
        for (IAEItemStack output : outputs) {
            try {
                IAEItemStack remainder = getProxy().getStorage().getItemInventory()
                    .injectItems(output.copy(), Actionable.SIMULATE, new MachineSource(this));
                if (remainder != null && remainder.getStackSize() > 0) {
                    canCraft = false;
                    break;
                }
            } catch (GridAccessException e) {
                canCraft = false;
                break;
            }
        }
        if (canCraft) {
            for (IAEItemStack output : outputs) {
                try {
                    getProxy().getStorage().getItemInventory()
                        .injectItems(output.copy(), Actionable.MODULATE, new MachineSource(this));
                } catch (GridAccessException ignored) {
                }
            }
            activePattern = null;
            craftCount = 0;
        }
        return TickRateModulation.IDLE;
    }

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        if (patternList != null) {
            patternList.stream()
                .filter(ICraftingPatternDetails::isCraftable)
                .forEach(pattern -> helper.addCraftingOption(this, pattern));
        }
    }

    public boolean pushPattern(ICraftingPatternDetails pattern, InventoryCrafting grid, CraftingCPUCluster cluster) {
        if (patternList == null || !patternList.contains(pattern) || !pattern.isCraftable()) return false;
        ICraftingCPUClusterAccessor accessor = (ICraftingCPUClusterAccessor) ((Object) cluster);
        long required = accessor.getWaitingFor(pattern) - 1;
        long actualRequired = required + 1;
        required = Math.min(required, getMaxCount());
        MECraftingInventory inventory = cluster.getInventory();
        for (IAEStack<?> input : pattern.getCondensedAEInputs()) {
            IAEItemStack copy = (IAEItemStack) input.copy();
            copy.setStackSize(copy.getStackSize() * required);
            IAEItemStack extracted = (IAEItemStack) inventory.extractItems(copy, Actionable.SIMULATE, cluster.getActionSource());
            long available = extracted == null ? 0 : extracted.getStackSize();
            if (copy.getStackSize() > available) {
                required = available / input.getStackSize();
            }
        }
        if (required < 0) return false;
        if (required >= 1) {
            for (IAEStack<?> input : pattern.getCondensedAEInputs()) {
                IAEItemStack copy = (IAEItemStack) input.copy();
                copy.setStackSize(copy.getStackSize() * required);
                inventory.extractItems(copy, Actionable.MODULATE, cluster.getActionSource());
            }
            for (IAEStack<?> output : pattern.getCondensedAEOutputs()) {
                IAEItemStack copy = (IAEItemStack) output.copy();
                copy.setStackSize(copy.getStackSize() * required);
                accessor.callPostChange(copy, cluster.getActionSource());
                accessor.getWaitingFor().add(copy.copy());
                accessor.callPostCraftingStatusChange(copy.copy());
            }
            accessor.setWaitingFor(pattern, actualRequired - required);
        }
        activePattern = pattern;
        craftCount = required + 1;
        craftingGrid = grid;
        return true;
    }

    @Override
    public void onReady() {
        super.onReady();
        updatePatternList();
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_MA(NBTTagCompound compound) {
        if (this.craftingGrid != null) {
            compound.setInteger("invC_size", this.craftingGrid.getSizeInventory());
            for (int i = 0; i < this.craftingGrid.getSizeInventory(); i++) {
                NBTTagCompound tag = new NBTTagCompound();
                ItemStack is = this.craftingGrid.getStackInSlot(i);
                if (is != null)
                    is.writeToNBT(tag);
                compound.setTag("invC_" + i, (NBTBase) tag);
            }
        }
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_MA(NBTTagCompound compound) {
        if (compound.hasKey("invC")) {
            int size = compound.getInteger("invC_size");
            this.craftingGrid = new InventoryCrafting((Container) new ContainerNull(), size, 1);
            for (int i = 0; i < size; i++) {
                NBTTagCompound tag = compound.getCompoundTag("invC_" + i);
                if (!tag.hasNoTags())
                    try {
                        this.craftingGrid.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(tag));
                    } catch (Exception exception) {
                    }
            }
        }
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream(ByteBuf data) {
        if (this.canBeRotated()) {
            data.writeByte((byte) getForward().ordinal());
            data.writeByte((byte) getUp().ordinal());
        }
    }

    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean output = false;
        if (this.canBeRotated()) {
            ForgeDirection oldForward = this.getForward(), oldUp = this.getUp();
            byte orientationForward = data.readByte(), orientationUp = data.readByte();
            ForgeDirection newForward = ForgeDirection.getOrientation(orientationForward & 7);
            ForgeDirection newUp = ForgeDirection.getOrientation(orientationUp & 7);
            this.setOrientation(newForward, newUp);
            output = newForward != oldForward || newUp != oldUp;
        }
        return output;
    }

    private void addPattern(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ICraftingPatternItem)) return;
        ICraftingPatternDetails pattern = ((ICraftingPatternItem) stack.getItem()).getPatternForItem(stack, worldObj);
        if (pattern != null) {
            if (patternList == null) {
                patternList = new LinkedList<>();
            }
            patternList.add(pattern);
        }
    }

    void updatePatternList() {
        if (!getProxy().isReady()) return;
        Boolean[] tracked = new Boolean[getPatterns().getSizeInventory()];
        Arrays.fill(tracked, false);
        if (patternList != null) {
            patternList.removeIf(pattern -> {
                for (int i = 0; i < getPatterns().getSizeInventory(); i++) {
                    if (pattern.getPattern() == getPatterns().getStackInSlot(i)) {
                        tracked[i] = true;
                        return false;
                    }
                }
                return true;
            });
        }
        for (int i = 0; i < tracked.length; i++) {
            if (!tracked[i]) {
                addPattern(getPatterns().getStackInSlot(i));
            }
        }
        try {
            getProxy().getGrid().postEvent(new MENetworkCraftingPatternChange(this, getProxy().getNode()));
        } catch (GridAccessException ignored) {
            ignored.printStackTrace();
        }
    }

    @Override
    public boolean isBusy() {
        return activePattern != null;
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(this);
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection dir) {
        return AECableType.SMART;
    }

    @Override
    public IInventory getInternalInventory() {
        return getPatterns();
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation op, ItemStack removed, ItemStack added) {
        if (inv == getPatterns()) {
            updatePatternList();
        }
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection dir) {
        return SIDES;
    }

    protected abstract long getMaxCount();

    @MENetworkEventSubscribe
    public void onNetworkChange(MENetworkEvent event) {
        try {
            getProxy().getGrid().postEvent(new MENetworkCraftingPatternChange(this, getProxy().getNode()));
        } catch (GridAccessException ignored) {
        }
    }

    @MENetworkEventSubscribe
    public void onPowerStatusChange(MENetworkPowerStatusChange event) {
        boolean newState;
        try {
            newState = getProxy().isActive() &&
                getProxy().getEnergy().extractAEPower(getPower(), Actionable.SIMULATE, PowerMultiplier.CONFIG) > 0.0001;
        } catch (GridAccessException e) {
            newState = false;
        }
        if (newState != isPowered) {
            isPowered = newState;
            markForUpdate();
        }
    }

    @Override
    public boolean isPowered() {
        return isPowered;
    }

    @Override
    public boolean isActive() {
        return isPowered;
    }

    protected abstract double getPower();

    @Override
    public boolean pushPattern(ICraftingPatternDetails details, InventoryCrafting ic, ForgeDirection direction) {
        return false;
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails iCraftingPatternDetails, InventoryCrafting inventoryCrafting) {
        return false;
    }

    public AppEngInternalInventory getPatterns() {
        return this.patternInventory;
    }

    public int rows() {
        return 4;
    }

    public int rowSize() {
        return 9;
    }

    public boolean shouldDisplay() {
        return true;
    }

    @Override
    public ItemStack getSelfRep() {
        return getItemFromTile(null);
    }

    @Override
    public ItemStack getDisplayRep() {
        return getItemFromTile(null);
    }

    @Override
    public ForgeDirection getForward() { return FastOrientableManager.getForward(orientableId); }

    @Override
    public ForgeDirection getUp() { return FastOrientableManager.getUp(orientableId); }

    @Override
    public void setOrientation(ForgeDirection forward, ForgeDirection up) {
        FastOrientableManager.set(orientableId, forward, up);
        this.markForUpdate();
        if (worldObj != null) worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
    }

    @Override
    public boolean canBeRotated() {
        return false;
    }
}
