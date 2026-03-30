package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.tile.inventory.AppEngInternalInventory;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;

public class TileQuantumMolecularAssembler extends TileCustomMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 108, 1);

    public TileQuantumMolecularAssembler() {}

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.quantumMolecularAssembler);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.quantumMolecularAssemblerSpeed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.quantumMolecularAssemblerPower;
    }

    @Override
    public AppEngInternalInventory getPatterns() {
        return patternInventory;
    }

}
