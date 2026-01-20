package foxiwhitee.FoxAE2Upgrade.tile.auto;

import appeng.api.config.Actionable;
import appeng.api.implementations.ICraftingPatternItem;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.core.Api;
import appeng.crafting.MECraftingInventory;
import appeng.me.GridAccessException;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import foxiwhitee.FoxAE2Upgrade.tile.TileAENetworkOrientable;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingCPUClusterAccessor;
import foxiwhitee.FoxLib.integration.applied.api.crafting.IPreCraftingMedium;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

public abstract class TileAutomatedBlock extends TileAENetworkOrientable implements IPreCraftingMedium, IGridTickable, ICraftingProvider, ICraftingMedium {
    private final List<ICraftingPatternDetails> patternList = new ArrayList<>();
    private ICraftingPatternDetails activePattern;
    private long craftCount;

    public TileAutomatedBlock() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
    }

    @Override
    public void validate() {
        super.validate();
        if (!getWorldObj().isRemote) {
            initPatterns();
        }
    }

    private void initPatterns() {
        patternList.clear();
        Item patternItem = Api.INSTANCE.definitions().items().encodedPattern().maybeItem().orNull();
        if (patternItem == null) return;

        for (BaseAutoBlockRecipe recipe : getRecipes()) {
            ItemStack patternStack = new ItemStack(patternItem);

            ItemStack[] inputs = recipe.getInputs().stream()
                .filter(o -> o instanceof ItemStack)
                .map(o -> (ItemStack) o)
                .toArray(ItemStack[]::new);

            encodePattern(patternStack, recipe.getOut(), inputs);

            if (patternStack.getItem() instanceof ICraftingPatternItem iep) {
                patternList.add(iep.getPatternForItem(patternStack, getWorldObj()));
            }
        }
    }

    private void encodePattern(ItemStack pattern, ItemStack output, ItemStack[] inputs) {
        if (output == null || inputs == null) return;

        NBTTagCompound encodedValue = new NBTTagCompound();
        encodedValue.setTag("in", createTagList(inputs));
        encodedValue.setTag("out", createTagList(output));
        encodedValue.setBoolean("crafting", false);
        encodedValue.setBoolean("substitute", false);

        pattern.setTagCompound(encodedValue);
    }

    private NBTTagList createTagList(ItemStack... items) {
        NBTTagList list = new NBTTagList();
        for (ItemStack item : items) {
            if (item != null) {
                NBTTagCompound tag = new NBTTagCompound();
                item.writeToNBT(tag);
                list.appendTag(tag);
            }
        }
        return list;
    }

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        patternList.forEach(pattern -> helper.addCraftingOption(this, pattern));
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails pattern, InventoryCrafting ic, CraftingCPUCluster cluster) {
        if (patternList.isEmpty() || !patternList.contains(pattern)) {
            return false;
        }
        ICraftingCPUClusterAccessor accessor = (ICraftingCPUClusterAccessor) ((Object) cluster);
        MECraftingInventory inventory = cluster.getInventory();
        BaseActionSource source = cluster.getActionSource();
        assert accessor != null;

        long required = accessor.getWaitingFor(pattern) - 1;
        long actualRequired = required + 1;
        required = Math.min(required, getMaxCount());

        for (IAEStack<?> input : pattern.getCondensedAEInputs()) {
            IAEStack<?> copy = input.copy().setStackSize(input.getStackSize() * required);
            IAEStack<?> extracted = inventory.extractItems(copy, Actionable.SIMULATE, source);
            long available = extracted == null ? 0 : extracted.getStackSize();
            if (copy.getStackSize() > available) {
                required = available / input.getStackSize();
            }
        }
        if (required < 0) {
            return false;
        }
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
        return true;
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode iGridNode, int ticksSinceLastCall) {
        if (activePattern == null) return TickRateModulation.IDLE;

        try {
            List<IAEItemStack> toInject = calculateOutputs();
            IMEMonitor<IAEItemStack> inv = getProxy().getStorage().getItemInventory();
            MachineSource source = new MachineSource(this);

            for (IAEItemStack stack : toInject) {
                IAEItemStack left = inv.injectItems(stack.copy(), Actionable.SIMULATE, source);
                if (left != null && left.getStackSize() > 0) return TickRateModulation.IDLE;
            }

            for (IAEItemStack stack : toInject) {
                inv.injectItems(stack, Actionable.MODULATE, source);
            }

            completeCrafting();
        } catch (GridAccessException ignored) {}

        return TickRateModulation.IDLE;
    }

    private List<IAEItemStack> calculateOutputs() {
        List<IAEItemStack> outputs = new ArrayList<>();
        for (IAEStack<?> s : activePattern.getCondensedAEOutputs()) {
            outputs.add(((IAEItemStack) s).copy().setStackSize(s.getStackSize() * craftCount));
        }
        return outputs;
    }

    private void completeCrafting() {
        this.activePattern = null;
        this.craftCount = 0;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        if (activePattern != null) {
            NBTTagCompound patTag = new NBTTagCompound();
            activePattern.getPattern().writeToNBT(patTag);
            data.setTag("activePattern", patTag);
            data.setLong("craftCount", craftCount);
        }
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        if (data.hasKey("activePattern")) {
            ItemStack stack = ItemStack.loadItemStackFromNBT(data.getCompoundTag("activePattern"));
            if (stack != null && stack.getItem() instanceof ICraftingPatternItem iep) {
                this.activePattern = iep.getPatternForItem(stack, getWorldObj());
            }
        }
        this.craftCount = data.getLong("craftCount");
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode node) {
        return new TickingRequest(1, 1, false, false);
    }

    @Override
    public boolean isBusy() {
        return activePattern != null;
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails p, InventoryCrafting ic) {
        return false;
    }

    protected abstract List<BaseAutoBlockRecipe> getRecipes();

    protected abstract long getMaxCount();

    protected abstract ItemStack getItemFromTile(Object obj);
}
