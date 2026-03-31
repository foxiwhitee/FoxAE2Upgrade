package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;

public class BlockUltimateMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockUltimateMolecularAssembler(String name) {
        super(name, TileUltimateMolecularAssembler.class, FoxConfig.ultimateMolecularAssemblerSpeed, FoxConfig.ultimateMolecularAssemblerThreads);
    }
}
