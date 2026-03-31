package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.api.networking.security.MachineSource;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.integration.applied.processors.ProcessorMultiThreadPatternMachine;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import net.minecraft.item.ItemStack;

public class TileQuantumMolecularAssembler extends TileCustomMolecularAssembler {
    public TileQuantumMolecularAssembler() {
        this.processor = new ProcessorMultiThreadPatternMachine(new FoxInternalInventory(this, 108, 1), new MachineSource(this), this, FoxConfig.quantumMolecularAssemblerThreads);
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

}
