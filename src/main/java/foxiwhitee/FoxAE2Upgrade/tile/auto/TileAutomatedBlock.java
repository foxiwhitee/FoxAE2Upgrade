package foxiwhitee.FoxAE2Upgrade.tile.auto;

import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.*;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.core.Api;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import foxiwhitee.FoxLib.integration.applied.api.crafting.IPreCraftingMedium;
import foxiwhitee.FoxLib.integration.applied.processors.ProcessorNoPatternsCraftingMachine;
import foxiwhitee.FoxLib.integration.applied.tile.TileNetworkOrientable;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.List;

public abstract class TileAutomatedBlock extends TileNetworkOrientable implements IPreCraftingMedium, IGridTickable, ICraftingProvider, ICraftingMedium {
    private final ProcessorNoPatternsCraftingMachine processor;

    public TileAutomatedBlock() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        this.processor = new ProcessorNoPatternsCraftingMachine(new MachineSource(this), this);
    }

    @Override
    public void onReady() {
        super.onReady();
        this.processor.setWorld(this.worldObj);
        this.processor.setProxy(this.getProxy());
        this.processor.setMaxCount(this.getMaxCount());
        this.processor.setValidation(p -> true);
        initPatterns();
    }

    private void initPatterns() {
        Item patternItem = Api.INSTANCE.definitions().items().encodedPattern().maybeItem().orNull();
        if (patternItem == null) return;

        for (BaseAutoBlockRecipe recipe : getRecipes()) {
            ItemStack patternStack = new ItemStack(patternItem);

            ItemStack[] inputs = recipe.getInputs().stream()
                .filter(o -> o instanceof ItemStack)
                .map(o -> (ItemStack) o)
                .toArray(ItemStack[]::new);

            encodePattern(patternStack, recipe.getOut(), inputs);

            this.processor.addPattern(patternStack);
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
        this.processor.provideCrafting(helper);
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails pattern, InventoryCrafting ic, CraftingCPUCluster cluster) {
        return this.processor.pushPattern(pattern, ic, cluster);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode iGridNode, int ticksSinceLastCall) {
        this.processor.tick();
        return TickRateModulation.IDLE;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    @SuppressWarnings("unused")
    public void writeToNbt_(NBTTagCompound data) {
        this.processor.writeToNbt(data);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    @SuppressWarnings("unused")
    public void readFromNbt_(NBTTagCompound data) {
        this.processor.readFromNbt(data);
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        for (IAEStack<?> stack : this.processor.getNeedSend()) {
            if (stack instanceof IAEItemStack iaeItemStack) {
                drops.add(iaeItemStack.getItemStack());
            }
        }
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode node) {
        return new TickingRequest(1, 1, false, false);
    }

    @Override
    public boolean isBusy() {
        return this.processor.isBusy();
    }

    @Override
    public boolean pushPattern(ICraftingPatternDetails p, InventoryCrafting ic) {
        return false;
    }

    protected abstract List<BaseAutoBlockRecipe> getRecipes();

    protected abstract long getMaxCount();

    protected abstract ItemStack getItemFromTile(Object obj);
}
