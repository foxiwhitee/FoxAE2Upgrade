package foxiwhitee.FoxAE2Upgrade.block;

import appeng.block.AEBaseTileBlock;
import appeng.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.render.RenderAdvDrive;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockAdvancedDriver extends AEBaseTileBlock {
    public BlockAdvancedDriver(String name) {
        super(Material.iron);
        setHardness(1.0F);
        setBlockName(name);
        setCreativeTab(FoxCore.FOX_TAB);
        setBlockTextureName(FoxCore.MODID + ":driver/" + name);
        setTileEntity(TileAdvancedDrive.class);
        this.isOpaque = false;
        this.lightOpacity = 1;

    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking())
            return false;
        TileAdvancedDrive tg = (TileAdvancedDrive)getTileEntity((IBlockAccess)world, x, y, z);
        if (tg != null) {
            if (Platform.isServer())
                Platform.openGUI(player, (TileEntity)tg, ForgeDirection.getOrientation(side), CommonProxy.getGuiAdvMeDrive());
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    protected RenderAdvDrive getRenderer() {
        return new RenderAdvDrive();
    }
}
