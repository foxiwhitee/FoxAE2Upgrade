package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.api.config.Actionable;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.items.misc.ItemEncodedPattern;
import appeng.me.GridAccessException;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import appeng.util.item.AEItemStack;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class TileUltimateMolecularAssembler extends TileCustomMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 72, 1);
    private Map<ICraftingPatternDetails, Double> productivityHistory = new HashMap<>();

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        if (getProxy().isActive() && patternList != null) {
            patternList.stream()
                .filter(ICraftingPatternDetails::isCraftable)
                .forEach(pattern -> {
                    helper.addCraftingOption(this, pattern);
                    if (!productivityHistory.containsKey(pattern)) {
                        productivityHistory.put(pattern, 0D);
                    }
                });
        }
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation op, ItemStack removed, ItemStack added) {
        if (removed != null && removed.getItem() instanceof ItemEncodedPattern) {
            ICraftingPatternDetails details = ((ItemEncodedPattern) removed.getItem()).getPatternForItem(removed, this.worldObj);
            if (productivityHistory.containsKey(details)) {
                productivityHistory.remove(details);
            }
        }
        if (inv == getPatterns()) {
            updatePatternList();
        }
    }

    public TileUltimateMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.ultimate_molecular_assembler_power);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        if (activePattern == null) return TickRateModulation.IDLE;

        IStorageGrid storage;
        try {
            storage = getProxy().getStorage();
        } catch (GridAccessException e) {
            return TickRateModulation.IDLE;
        }

        IMEInventory<IAEItemStack> itemInv = storage.getItemInventory();
        MachineSource src = new MachineSource(this);

        List<IAEItemStack> outputs = new ArrayList<>();
        for (IAEStack<?> stack : activePattern.getCondensedAEOutputs()) {
            IAEItemStack copy = (IAEItemStack) stack.copy();
            copy.setStackSize(copy.getStackSize() * craftCount);
            outputs.add(copy);
        }

        for (int i = 0; i < craftingGrid.getSizeInventory(); i++) {
            ItemStack container = Platform.getContainerItem(craftingGrid.getStackInSlot(i));
            if (container != null) outputs.add(AEItemStack.create(container));
        }
        if (!canInjectAll(outputs, itemInv, src)) return TickRateModulation.IDLE;
        injectAll(outputs, itemInv, src);
        productivityHistory.merge(activePattern, (double) craftCount, Double::sum);
        int bonusCount = ProductivityUtil.check(productivityHistory, activePattern, getProductivity());
        craftCount = 0;
        if (bonusCount > 0) {
            outputs.clear();
            for (IAEStack<?> stack : activePattern.getCondensedAEOutputs()) {
                IAEItemStack copy = (IAEItemStack) stack.copy();
                copy.setStackSize(copy.getStackSize() * bonusCount);
                outputs.add(copy);
            }

            if (canInjectAll(outputs, itemInv, src)) {
                injectAll(outputs, itemInv, src);
            }
        }
        activePattern = null;
        return TickRateModulation.IDLE;
    }

    private boolean canInjectAll(List<IAEItemStack> stacks, IMEInventory<IAEItemStack> inv, MachineSource src) {
        for (IAEItemStack stack : stacks) {
            IAEItemStack remainder = inv.injectItems(stack.copy(), Actionable.SIMULATE, src);
            if (remainder != null && remainder.getStackSize() > 0) return false;
        }
        return true;
    }

    private void injectAll(List<IAEItemStack> stacks, IMEInventory<IAEItemStack> inv, MachineSource src) {
        for (IAEItemStack stack : stacks) {
            inv.injectItems(stack.copy(), Actionable.MODULATE, src);
        }
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.ULTIMATE_MOLECULAR_ASSEMBLER);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.ultimate_molecular_assembler_speed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.ultimate_molecular_assembler_power;
    }

    public String getName() {
        return ModBlocks.ULTIMATE_MOLECULAR_ASSEMBLER.getUnlocalizedName();
    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @Override
    public boolean acceptsPlans() {
        return false;
    }

    @Override
    public AppEngInternalInventory getPatterns() {
        return patternInventory;
    }

    @Override
    public int rows() {
        return 8;
    }

    protected int getProductivity() {
        return FoxConfig.ultimate_molecular_assembler_productivity;
    }
}
