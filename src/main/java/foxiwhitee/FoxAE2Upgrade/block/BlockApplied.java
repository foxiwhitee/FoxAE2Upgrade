package foxiwhitee.FoxAE2Upgrade.block;

import appeng.tile.AEBaseInvTile;
import appeng.tile.grid.AENetworkInvTile;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.block.FoxBaseBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class BlockApplied extends FoxBaseBlock {
    public BlockApplied(String name, Class<? extends TileEntity> tileEntityClass) {
        super(FoxCore.MODID, name);
        setTileEntityType(tileEntityClass);
        setCreativeTab(FoxCore.FOX_TAB);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof IOrientable tile) {
            if (tile.canBeRotated()) {
                int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + (double)0.5F) & 3;
                ForgeDirection horizontal = l == 0 ? ForgeDirection.SOUTH : (l == 1 ? ForgeDirection.WEST : (l == 2 ? ForgeDirection.NORTH : ForgeDirection.EAST));
                ForgeDirection forward;
                ForgeDirection up;
                if (entity.rotationPitch > 65.0F && this.hasUpDownRotate()) {
                    forward = ForgeDirection.UP;
                    up = horizontal;
                } else if (entity.rotationPitch < -65.0F && this.hasUpDownRotate()) {
                    forward = ForgeDirection.DOWN;
                    up = horizontal.getOpposite();
                } else {
                    forward = horizontal.getOpposite();
                    up = ForgeDirection.UP;
                }

                tile.setOrientation(forward, up);
            }
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int b) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof AEBaseInvTile invTile) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            invTile.getDrops(world, x, y, z, drops);
            spawnDrops(world, x, y, z, drops);
        } else if (te instanceof AENetworkInvTile invTile) {
            ArrayList<ItemStack> drops = new ArrayList<>();
            invTile.getDrops(world, x, y, z, drops);
            spawnDrops(world, x, y, z, drops);
        }

        super.breakBlock(world, x, y, z, block, b);
    }
}
