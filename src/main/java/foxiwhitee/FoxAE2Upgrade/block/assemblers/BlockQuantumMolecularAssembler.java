package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;

public class BlockQuantumMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockQuantumMolecularAssembler(String name) {
        super(name, TileQuantumMolecularAssembler.class, FoxConfig.quantumMolecularAssemblerSpeed);
    }
}
