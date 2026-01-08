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

@SimpleGuiHandler(tile = TileCobblestoneDuper.class, gui = GuiCobblestoneDuper.class, container = ContainerCobblestoneDuper.class)
public class BlockCobblestoneDuper extends BlockApplied {
    public BlockCobblestoneDuper(String name) {
        super(name, TileCobblestoneDuper.class);
        setHardness(0.5F);
    }

    @Override
    public String getFolder() {
        return "cobblestoneDuper/";
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
}
