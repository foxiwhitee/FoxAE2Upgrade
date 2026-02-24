package foxiwhitee.FoxAE2Upgrade.block;

import appeng.util.Platform;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

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
