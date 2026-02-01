package foxiwhitee.FoxAE2Upgrade.items;

import appeng.me.helpers.IGridProxyable;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ModItemBlock extends ItemBlock {
    private final Block blockType;

    public ModItemBlock(Block b) {
        super(b);
        this.blockType = b;
    }

    public String getUnlocalizedName() {
        return this.blockType.getUnlocalizedName();
    }

    public String getUnlocalizedName(ItemStack i) {
        return this.blockType.getUnlocalizedName();
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
            return false;
        if (this.blockType instanceof net.minecraft.block.ITileEntityProvider) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof IGridProxyable)
                ((IGridProxyable) tile).getProxy().setOwner(player);
        }
        return true;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advancedToolTips) {
        if (FoxConfig.enableTooltips) {
            if (this.blockType.equals(ModBlocks.autoCrystallizer)) {
                list.add(LocalizationUtils.localize("tooltip.autoCrystallizer"));
            } else if (this.blockType.equals(ModBlocks.autoPress)) {
                list.add(LocalizationUtils.localize("tooltip.autoPress"));
            }
            if (this.blockType.equals(ModBlocks.advancedMolecularAssembler)) {
                list.add(LocalizationUtils.localize("tooltip.assembler.speed", FoxConfig.advancedMolecularAssemblerSpeed));
            } else if (this.blockType.equals(ModBlocks.ultimateMolecularAssembler)) {
                list.add(LocalizationUtils.localize("tooltip.assembler.speed", FoxConfig.ultimateMolecularAssemblerSpeed));
                if (FoxConfig.hasUltimateMolecularAssemblerProductivity) {
                    list.add(LocalizationUtils.localize("tooltip.productivity.base", FoxConfig.ultimateMolecularAssemblerProductivity));
                }
            } else if (this.blockType.equals(ModBlocks.quantumMolecularAssembler)) {
                list.add(LocalizationUtils.localize("tooltip.assembler.speed", FoxConfig.quantumMolecularAssemblerSpeed));
                if (FoxConfig.hasQuantumMolecularAssemblerProductivity) {
                    list.add(LocalizationUtils.localize("tooltip.productivity.base", FoxConfig.quantumMolecularAssemblerProductivity));
                }
            } else if (this.blockType.equals(ModBlocks.cobblestoneDuper)) {
                list.add(LocalizationUtils.localize("tooltip.cobblestoneDuper"));
                list.add(LocalizationUtils.localize("tooltip.productivity.base", 0));
                list.add(LocalizationUtils.localize("tooltip.productivity.warning"));
            } else if (this.blockType.equals(ModBlocks.meServer)) {
                list.add(LocalizationUtils.localize("tooltip.meServer"));
            } else if (this.blockType.equals(ModBlocks.levelMaintainer)) {
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.1"));
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.2"));
                list.add(LocalizationUtils.localize("tooltip.levelMaintainer.desc.3"));
            }
        }
    }
}

