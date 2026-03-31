package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;

public class BlockAdvancedMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockAdvancedMolecularAssembler(String name) {
        super(name, TileAdvancedMolecularAssembler.class, FoxConfig.advancedMolecularAssemblerSpeed, 1);
    }
}
