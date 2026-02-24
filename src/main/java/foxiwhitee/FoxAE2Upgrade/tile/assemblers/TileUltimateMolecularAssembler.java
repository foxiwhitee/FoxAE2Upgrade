package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.items.misc.ItemEncodedPattern;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.ProductivityBlackListHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

import static foxiwhitee.FoxAE2Upgrade.helpers.CrafterHelper.calculateOutputs;
import static foxiwhitee.FoxAE2Upgrade.helpers.CrafterHelper.trySendItems;

public class TileUltimateMolecularAssembler extends TileCustomMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 72, 1);
    private final Map<ICraftingPatternDetails, Double> productivityHistory = new HashMap<>();

    @Override
    public void provideCrafting(ICraftingProviderHelper helper) {
        if (patternList != null) {
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
            productivityHistory.remove(details);
        }
        if (inv == getPatterns()) {
            updatePatternList();
        }
    }

    public TileUltimateMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.ultimateMolecularAssemblerPower);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode node, int ticksSinceLastCall) {
        if (activePattern != null && needSend.isEmpty()) {
            List<IAEItemStack> outputs = calculateOutputs(activePattern, craftCount, craftingGrid);
            needSend.addAll(outputs);

            int bonusCount = 0;
            if (hasProductivity()) {
                boolean isBlackListed = false;
                for (IAEItemStack stack : outputs) {
                    if (ProductivityBlackListHelper.isInBlackList(stack.getItemStack())) {
                        isBlackListed = true;
                        break;
                    }
                }
                if (!isBlackListed) {
                    productivityHistory.merge(activePattern, (double) craftCount, Double::sum);
                    bonusCount = ProductivityUtil.check(productivityHistory, activePattern, getProductivity());
                }
            }
            if (bonusCount > 0) {
                outputs.clear();
                for (IAEStack<?> stack : activePattern.getCondensedAEOutputs()) {
                    IAEItemStack copy = (IAEItemStack) stack.copy();
                    copy.setStackSize(copy.getStackSize() * bonusCount);
                    outputs.add(copy);
                }
                needSend.addAll(outputs);
            }
        }

        trySendItems(getProxy(), source, needSend);
        if (needSend.isEmpty()) {
            activePattern = null;
            craftCount = 0;
        }

        return TickRateModulation.IDLE;
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.ultimateMolecularAssembler);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.ultimateMolecularAssemblerSpeed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.ultimateMolecularAssemblerPower;
    }

    public String getName() {
        return ModBlocks.ultimateMolecularAssembler.getUnlocalizedName();
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
        return FoxConfig.ultimateMolecularAssemblerProductivity;
    }

    protected boolean hasProductivity() {
        return FoxConfig.hasUltimateMolecularAssemblerProductivity;
    }
}
