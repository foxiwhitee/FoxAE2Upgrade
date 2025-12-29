package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import appeng.util.Platform;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.client.gui.assemblers.GuiUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SimpleGuiHandler(tile = TileUltimateMolecularAssembler.class, gui = GuiUltimateMolecularAssembler.class, container = ContainerUltimateMolecularAssembler.class)
public class BlockUltimateMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockUltimateMolecularAssembler(String name) {
        super(name);
        setTileEntity(TileUltimateMolecularAssembler.class);
    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileUltimateMolecularAssembler)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.getHandler(BlockUltimateMolecularAssembler.class), world, x, y, z);
        return true;
    }

}
