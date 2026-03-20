package foxiwhitee.FoxAE2Upgrade.items;

import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AllItemBlock extends ModItemBlock {
    public AllItemBlock(Block b) {
        super(b);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        if (FoxConfig.enableTooltips) {
            if (isBlock(ModBlocks.autoCrystallizer)) {
                list.add(LocalizationUtils.localize("tooltip.autoCrystallizer"));
            } else if (isBlock(ModBlocks.autoPress)) {
                list.add(LocalizationUtils.localize("tooltip.autoPress"));
            }
            if (isBlock(ModBlocks.advancedMolecularAssembler)) {
                list.add(LocalizationUtils.localizeF("tooltip.assembler.speed", FoxConfig.advancedMolecularAssemblerSpeed));
            } else if (isBlock(ModBlocks.ultimateMolecularAssembler)) {
                list.add(LocalizationUtils.localizeF("tooltip.assembler.speed", FoxConfig.ultimateMolecularAssemblerSpeed));
                if (FoxConfig.hasUltimateMolecularAssemblerProductivity) {
                    list.add(LocalizationUtils.localizeF("tooltip.productivity.base", FoxConfig.ultimateMolecularAssemblerProductivity));
                }
            } else if (isBlock(ModBlocks.quantumMolecularAssembler)) {
                list.add(LocalizationUtils.localizeF("tooltip.assembler.speed", FoxConfig.quantumMolecularAssemblerSpeed));
                if (FoxConfig.hasQuantumMolecularAssemblerProductivity) {
                    list.add(LocalizationUtils.localizeF("tooltip.productivity.base", FoxConfig.quantumMolecularAssemblerProductivity));
                }
            } else if (isBlock(ModBlocks.cobblestoneDuper)) {
                list.add(LocalizationUtils.localize("tooltip.cobblestoneDuper"));
                list.add(LocalizationUtils.localizeF("tooltip.productivity.base", 0));
                list.add(LocalizationUtils.localize("tooltip.productivity.warning"));
            } else if (isBlock(ModBlocks.meServer)) {
                list.add(LocalizationUtils.localize("tooltip.meServer"));
            } else if (isBlock(ModBlocks.levelMaintainer)) {
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.1"));
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.2"));
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.3"));
            }
        }
    }
}
