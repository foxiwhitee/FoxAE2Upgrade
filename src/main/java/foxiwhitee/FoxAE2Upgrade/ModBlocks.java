package foxiwhitee.FoxAE2Upgrade;


import foxiwhitee.FoxAE2Upgrade.block.BlockAENetwork;
import foxiwhitee.FoxAE2Upgrade.block.BlockAdvancedDriver;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockBaseMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockHybridMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.auto.TileAutoCrystallizer;
import foxiwhitee.FoxAE2Upgrade.tile.auto.TileAutoPress;
import foxiwhitee.FoxAE2Upgrade.utils.helpers.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;


public class ModBlocks {

    public static final Block COBBLESTONE_DUPER = new BlockCobblestoneDuper("cobblestoneDuper");

    public static final Block ADVANCED_DRIVER = new BlockAdvancedDriver("advancedDriver");

    public static final Block AUTO_CRYSTALLIZER = new BlockAENetwork("autoCrystallizer", TileAutoCrystallizer.class);
    public static final Block AUTO_PRESS = new BlockAENetwork("autoPress", TileAutoPress.class);

    public static final Block BASE_MOLECULAR_ASSEMBLER = new BlockBaseMolecularAssembler("baseMolecularAssembler");
    public static final Block HYBRID_MOLECULAR_ASSEMBLER = new BlockHybridMolecularAssembler("hybridMolecularAssembler");
    public static final Block ULTIMATE_MOLECULAR_ASSEMBLER = new BlockUltimateMolecularAssembler("ultimateMolecularAssembler");

    public static void registerBlocks() {
        if (ContentConfig.enableCobblestoneDuper) {
            RegisterUtils.registerBlock(COBBLESTONE_DUPER);
            RegisterUtils.registerTile(TileCobblestoneDuper.class);
        }
        if (ContentConfig.enableAdvancedDriver) {
            RegisterUtils.registerBlock(ADVANCED_DRIVER);
            RegisterUtils.registerTile(TileAdvancedDrive.class);
        }
        if (ContentConfig.enableAutoCrystallizer) {
            RegisterUtils.registerBlock(AUTO_CRYSTALLIZER);
            RegisterUtils.registerTile(TileAutoCrystallizer.class);
        }
        if (ContentConfig.enableAutoPress) {
            RegisterUtils.registerBlock(AUTO_PRESS);
            RegisterUtils.registerTile(TileAutoPress.class);
        }
        if (ContentConfig.enableMolecularAssemblers) {
            RegisterUtils.registerBlocks(BASE_MOLECULAR_ASSEMBLER, HYBRID_MOLECULAR_ASSEMBLER, ULTIMATE_MOLECULAR_ASSEMBLER);
            RegisterUtils.findClasses("foxiwhitee.FoxAE2Upgrade.tile.assemblers", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
    }
}
