package foxiwhitee.FoxAE2Upgrade;


import appeng.block.AEBaseItemBlock;
import foxiwhitee.FoxAE2Upgrade.block.BlockAENetwork;
import foxiwhitee.FoxAE2Upgrade.block.BlockAdvancedDriver;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.block.BlockMEServer;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.items.ModItemBlock;
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


public class ModBlocks {

    public static final Block cobblestoneDuper = new BlockCobblestoneDuper("cobblestoneDuper");

    public static final Block advancedDriver = new BlockAdvancedDriver("advancedDriver");
    public static final Block meServer = new BlockMEServer("meServer");

    public static final Block autoCrystallizer = new BlockAENetwork("autoCrystallizer", TileAutoCrystallizer.class);
    public static final Block autoPress = new BlockAENetwork("autoPress", TileAutoPress.class);

    @StaticRender(modID = "foxae2upgrade", tile = TileAdvancedMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/advancedMolecularAssembler.png")
    public static final Block advancedMolecularAssembler = new BlockAdvancedMolecularAssembler("advancedMolecularAssembler");

    @StaticRender(modID = "foxae2upgrade", tile = TileUltimateMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/ultimateMolecularAssembler.png")
    public static final Block ultimateMolecularAssembler = new BlockUltimateMolecularAssembler("ultimateMolecularAssembler");

    @StaticRender(modID = "foxae2upgrade", tile = TileQuantumMolecularAssembler.class,
        model = "models/molecularAssembler.obj", texture = "textures/blocks/assemblers/quantumMolecularAssembler.png")
    public static final Block quantumMolecularAssembler = new BlockQuantumMolecularAssembler("quantumMolecularAssembler");

    public static void registerBlocks() {
        if (ContentConfig.enableCobblestoneDuper) {
            RegisterUtils.registerBlock(cobblestoneDuper, ModItemBlock.class);
            RegisterUtils.registerTile(TileCobblestoneDuper.class);
        }
        if (ContentConfig.enableAdvancedDriver) {
            RegisterUtils.registerBlock(advancedDriver, AEBaseItemBlock.class);
            RegisterUtils.registerTile(TileAdvancedDrive.class);
        }
        if (ContentConfig.enableAutoCrystallizer) {
            RegisterUtils.registerBlock(autoCrystallizer, ModItemBlock.class);
            RegisterUtils.registerTile(TileAutoCrystallizer.class);
        }
        if (ContentConfig.enableAutoPress) {
            RegisterUtils.registerBlock(autoPress, ModItemBlock.class);
            RegisterUtils.registerTile(TileAutoPress.class);
        }
        if (ContentConfig.enableMolecularAssemblers) {
            RegisterUtils.registerBlocks(ModItemBlock.class, advancedMolecularAssembler, ultimateMolecularAssembler, quantumMolecularAssembler);
            RegisterUtils.findClasses("foxiwhitee.FoxAE2Upgrade.tile.assemblers", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
        if (ContentConfig.enableMEServer) {
            RegisterUtils.registerBlock(meServer, ModItemBlock.class);
            RegisterUtils.registerTile(TileMEServer.class);
        }
    }
}
