package foxiwhitee.FoxAE2Upgrade.block;

import appeng.block.AEBaseTileBlock;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.api.FoxLibApi;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAdvancedDrive extends AEBaseTileBlock {
    public BlockAdvancedDrive(String name) {
        super(Material.iron);
        setHardness(1.0F);
        setBlockName(name);
        setCreativeTab(FoxCore.FOX_TAB);
        setBlockTextureName(FoxCore.MODID + ":drive/advancedDriveBottom");
        setTileEntity(TileAdvancedDrive.class);
        this.isOpaque = false;
        this.lightOpacity = 1;
    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileAdvancedDrive)
            FMLNetworkHandler.openGui(player, FoxLib.instance, FoxLibApi.instance.registries().registerGui().getIdByBlock(BlockAdvancedDrive.class), world, x, y, z);
        return true;
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
