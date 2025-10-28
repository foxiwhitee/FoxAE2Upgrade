package foxiwhitee.FoxAE2Upgrade.block;

import appeng.block.AEBaseBlock;
import appeng.util.Platform;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

@SimpleGuiHandler(index = GuiHandlers.cobblestoneDuper, tile = TileCobblestoneDuper.class, gui = GuiCobblestoneDuper.class, container = ContainerCobblestoneDuper.class)
public class BlockCobblestoneDuper extends AEBaseBlock implements ITileEntityProvider {
    IIcon[] icons = new IIcon[3];
    private String name;
    public BlockCobblestoneDuper(String name) {
        super(Material.iron);
        this.name = name;
        setCreativeTab(FoxCore.FOX_TAB);
        setBlockName(name);
        setHardness(0.5F);
    }

    public void registerBlockIcons(IIconRegister register) {
        this.icons[0] = register.registerIcon( FoxCore.MODID + ":cobblestone_duper/" + name + "_top");
        this.icons[2] = register.registerIcon(FoxCore.MODID + ":cobblestone_duper/" + name + "_bottom");
        this.icons[1] = register.registerIcon(FoxCore.MODID + ":cobblestone_duper/" + name + "_side");
    }

    public IIcon getIcon(int side, int meta) {
        if (side == 1)
            return this.icons[0];
        if (side > 1)
            return this.icons[1];
        if (side < 1)
            return this.icons[2];
        return this.icons[1];
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return (TileEntity)new TileCobblestoneDuper();
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int b) {
        TileCobblestoneDuper te = (TileCobblestoneDuper)world.getTileEntity(x, y, z);
        if (te != null) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            if (te.dropItems()) {
                te.getDrops(world, x, y, z, drops);
            } else {
                te.getNoDrops(world, x, y, z, drops);
            }
            Platform.spawnDrops(world, x, y, z, drops);
        }
        super.breakBlock(world, x, y, z, block, b);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileCobblestoneDuper)
            FMLNetworkHandler.openGui(player, FoxLib.instance, GuiHandlers.cobblestoneDuper, world, x, y, z);
        return true;
    }
}
