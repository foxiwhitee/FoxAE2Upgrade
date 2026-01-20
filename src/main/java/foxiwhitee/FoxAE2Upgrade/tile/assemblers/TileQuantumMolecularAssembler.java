package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.tile.inventory.AppEngInternalInventory;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;

public class TileQuantumMolecularAssembler extends TileUltimateMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 108, 1);

    public TileQuantumMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.quantumMolecularAssemblerPower);
    }

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

    public String getName() {
        return ModBlocks.quantumMolecularAssembler.getUnlocalizedName();
    }

    @Override
    public AppEngInternalInventory getPatterns() {
        return patternInventory;
    }

    @Override
    public int rows() {
        return 12;
    }

    @Override
    protected int getProductivity() {
        return FoxConfig.quantumMolecularAssemblerProductivity;
    }

    @Override
    protected boolean hasProductivity() {
        return FoxConfig.hasQuantumMolecularAssemblerProductivity;
    }
}
