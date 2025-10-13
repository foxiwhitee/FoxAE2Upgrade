package foxiwhitee.FoxAE2Upgrade.block.fluid;

import appeng.block.AEBaseBlock;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.fluid.GuiFluidReceiver;
import foxiwhitee.FoxAE2Upgrade.container.fluid.ContainerFluidReceiver;
import foxiwhitee.FoxAE2Upgrade.tile.fluid.TileFluidReceiver;
import foxiwhitee.FoxAE2Upgrade.utils.handler.GuiHandlers;
import foxiwhitee.FoxAE2Upgrade.utils.handler.SimpleGuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@SimpleGuiHandler(index = GuiHandlers.fluidReceiver, tile = TileFluidReceiver.class, gui = GuiFluidReceiver.class, container = ContainerFluidReceiver.class)
public class BlockFluidReceiver extends AEBaseBlock implements ITileEntityProvider {
    private final String name;

    public BlockFluidReceiver(String name) {
        super(Material.iron);
        this.name = name;
        setCreativeTab(FoxCore.FOX_TAB);
        setBlockName(name);
        setHardness(0.5F);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return (TileEntity)new TileFluidReceiver();
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileFluidReceiver)
            FMLNetworkHandler.openGui(player, FoxCore.instance, GuiHandlers.fluidReceiver, world, x, y, z);
        return true;
    }
}
