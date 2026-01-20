package foxiwhitee.FoxAE2Upgrade.tile.assemblers;


import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileAdvancedMolecularAssembler extends TileCustomMolecularAssembler {

    public TileAdvancedMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.advancedMolecularAssemblerPower);
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.advancedMolecularAssembler);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.advancedMolecularAssemblerSpeed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.advancedMolecularAssemblerPower;
    }

    public String getName() {
        return ModBlocks.advancedMolecularAssembler.getUnlocalizedName();
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
