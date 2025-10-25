package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileUltimateMolecularAssembler extends TileCustomMolecularAssembler{

    public TileUltimateMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.ultimate_molecular_assembler_power);
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
}
