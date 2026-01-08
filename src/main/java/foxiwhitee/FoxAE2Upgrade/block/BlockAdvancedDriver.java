package foxiwhitee.FoxAE2Upgrade.block;

import appeng.block.AEBaseTileBlock;
import appeng.util.Platform;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.client.render.RenderAdvDrive;
import foxiwhitee.FoxAE2Upgrade.container.ContainerAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SimpleGuiHandler(tile = TileAdvancedDrive.class, gui = GuiAdvancedDrive.class, container = ContainerAdvancedDrive.class)
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
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileAdvancedDrive)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockAdvancedDriver.class), world, x, y, z);
        return true;
    }

    @SideOnly(Side.CLIENT)
    protected RenderAdvDrive getRenderer() {
        return new RenderAdvDrive();
    }
}
