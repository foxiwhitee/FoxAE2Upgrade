package foxiwhitee.FoxAE2Upgrade;


import foxiwhitee.FoxAE2Upgrade.block.BlockAENetwork;
import foxiwhitee.FoxAE2Upgrade.block.BlockAdvancedDriver;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.block.BlockMEServer;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.auto.TileAutoCrystallizer;
import foxiwhitee.FoxAE2Upgrade.tile.auto.TileAutoPress;
import foxiwhitee.FoxLib.client.render.StaticRender;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.Locale;


public class ModBlocks {

    public static final Block COBBLESTONE_DUPER = new BlockCobblestoneDuper("cobblestoneDuper");

    public static final Block ADVANCED_DRIVER = new BlockAdvancedDriver("advancedDriver");
    public static final Block ME_SERVER = new BlockMEServer("meServer");

    public static final Block AUTO_CRYSTALLIZER = new BlockAENetwork("autoCrystallizer", TileAutoCrystallizer.class);
    public static final Block AUTO_PRESS = new BlockAENetwork("autoPress", TileAutoPress.class);

    @StaticRender(modID = "foxae2upgrade", tile = TileAdvancedMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/advancedMolecularAssembler.png")
    public static final Block ADVANCED_MOLECULAR_ASSEMBLER = new BlockAdvancedMolecularAssembler("advancedMolecularAssembler");

    @StaticRender(modID = "foxae2upgrade", tile = TileUltimateMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/ultimateMolecularAssembler.png")
    public static final Block ULTIMATE_MOLECULAR_ASSEMBLER = new BlockUltimateMolecularAssembler("ultimateMolecularAssembler");

    @StaticRender(modID = "foxae2upgrade", tile = TileQuantumMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/quantumMolecularAssembler.png")
    public static final Block QUANTUM_MOLECULAR_ASSEMBLER = new BlockQuantumMolecularAssembler("quantumMolecularAssembler");

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
            RegisterUtils.registerBlocks(ADVANCED_MOLECULAR_ASSEMBLER, ULTIMATE_MOLECULAR_ASSEMBLER, QUANTUM_MOLECULAR_ASSEMBLER);
            RegisterUtils.findClasses("foxiwhitee.FoxAE2Upgrade.tile.assemblers", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
        if (ContentConfig.enableMEServer) {
            RegisterUtils.registerBlock(ME_SERVER);
            RegisterUtils.registerTile(TileMEServer.class);
        }
    }
}
