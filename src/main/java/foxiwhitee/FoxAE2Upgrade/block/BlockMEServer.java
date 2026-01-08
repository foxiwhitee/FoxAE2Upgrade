package foxiwhitee.FoxAE2Upgrade.block;


import appeng.block.AEBaseTileBlock;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiMEServer;
import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(tile = TileMEServer.class, gui = GuiMEServer.class, container = ContainerMEServer.class)
public class BlockMEServer extends BlockApplied {
    public BlockMEServer(String name) {
        super(name, TileMEServer.class);
    }

    @Override
    public String getFolder() {
        return "meServer/";
    }

    @Override
    protected boolean hasUpDownRotate() {
        return true;
    }
}
