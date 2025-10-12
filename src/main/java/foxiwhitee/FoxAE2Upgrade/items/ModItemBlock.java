package foxiwhitee.FoxAE2Upgrade.items;

import appeng.block.AEBaseItemBlock;
import appeng.me.helpers.IGridProxyable;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ModItemBlock extends AEBaseItemBlock {
    private final Block blockType;

    public ModItemBlock(Block b) {
        super(b);
        this.blockType = b;
    }

    public String getUnlocalizedName() {
        return this.blockType.getUnlocalizedName();
    }

    public String getUnlocalizedName(ItemStack i) {
        return this.blockType.getUnlocalizedName();
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
            return false;
        if (this.blockType instanceof net.minecraft.block.ITileEntityProvider) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof IGridProxyable)
                ((IGridProxyable) tile).getProxy().setOwner(player);
        }
        return true;
    }

    @Override
    public void addCheckedInformation(ItemStack itemStack, EntityPlayer player, List<String> list, boolean advancedToolTips) {
        super.addCheckedInformation(itemStack, player, list, advancedToolTips);
        if (FoxConfig.enable_tooltips) {

        }
    }
}

