package foxiwhitee.FoxAE2Upgrade.tile.assemblers;


import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileAdvancedMolecularAssembler extends TileCustomMolecularAssembler{

    public TileAdvancedMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.advanced_molecular_assembler_power);
        getPatterns().setMaxStackSize(1);
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.ADVANCED_MOLECULAR_ASSEMBLER);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.advanced_molecular_assembler_speed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.advanced_molecular_assembler_power;
    }

    public String getName() {
        return ModBlocks.ADVANCED_MOLECULAR_ASSEMBLER.getUnlocalizedName();
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
