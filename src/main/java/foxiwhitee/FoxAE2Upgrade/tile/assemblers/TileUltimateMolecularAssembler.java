package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.tile.inventory.AppEngInternalInventory;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;

public class TileUltimateMolecularAssembler extends TileCustomMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 72, 1);

    public TileUltimateMolecularAssembler() {}

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

    @Override
    public AppEngInternalInventory getPatterns() {
        return patternInventory;
    }

}
