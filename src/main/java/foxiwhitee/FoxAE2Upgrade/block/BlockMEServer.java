package foxiwhitee.FoxAE2Upgrade.block;


import appeng.block.AEBaseTileBlock;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiMEServer;
import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(index = GuiHandlers.meServer, tile = TileMEServer.class, gui = GuiMEServer.class, container = ContainerMEServer.class)
public class BlockMEServer extends AEBaseTileBlock {
    public BlockMEServer(String name) {
        super(Material.iron);
        this.setTileEntity( TileMEServer.class );
        this.setBlockName(name);
        this.setCreativeTab(FoxCore.FOX_TAB);
        this.setBlockTextureName(FoxCore.MODID.toLowerCase() + ":meServer/" + name);
    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileMEServer)
            FMLNetworkHandler.openGui(player, FoxCore.instance, GuiHandlers.meServer, world, x, y, z);
        return true;
    }
}
