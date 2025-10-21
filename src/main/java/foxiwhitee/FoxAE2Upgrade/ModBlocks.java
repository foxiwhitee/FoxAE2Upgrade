package foxiwhitee.FoxAE2Upgrade;


import foxiwhitee.FoxAE2Upgrade.block.BlockAENetwork;
import foxiwhitee.FoxAE2Upgrade.block.BlockAdvancedDriver;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.utils.helpers.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;


public class ModBlocks {

    public static final Block COBBLESTONE_DUPER = new BlockCobblestoneDuper("cobblestoneDuper");

    public static final Block ADVANCED_DRIVER = new BlockAdvancedDriver("advancedDriver");

    public static void registerBlocks() {
        if (ContentConfig.enableCobblestoneDuper) {
            RegisterUtils.registerBlock(COBBLESTONE_DUPER);
            RegisterUtils.registerTile(TileCobblestoneDuper.class);
        }
        if (ContentConfig.enableAdvancedDriver) {
            RegisterUtils.registerBlock(ADVANCED_DRIVER);
            RegisterUtils.registerTile(TileAdvancedDrive.class);
        }
    }
}
