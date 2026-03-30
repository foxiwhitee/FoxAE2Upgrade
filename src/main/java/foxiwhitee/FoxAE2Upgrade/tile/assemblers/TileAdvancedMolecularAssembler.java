package foxiwhitee.FoxAE2Upgrade.tile.assemblers;


import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;

public class TileAdvancedMolecularAssembler extends TileCustomMolecularAssembler {
    public TileAdvancedMolecularAssembler() {}

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
}
