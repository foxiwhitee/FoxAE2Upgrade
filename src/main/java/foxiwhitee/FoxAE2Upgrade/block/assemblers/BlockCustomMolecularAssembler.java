package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.block.BlockApplied;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public abstract class BlockCustomMolecularAssembler extends BlockApplied {
    private final double speed;

    public BlockCustomMolecularAssembler(String name, Class<? extends TileEntity> tile, double speed) {
        super(name, tile);
        this.speed = speed;
        setBlockTextureName(FoxCore.MODID + ":drive/advancedDriveBottom");
        renderId = -1;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        list.add(LocalizationUtils.localizeF("tooltip.assembler.speed", speed));
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        return false;
    }
}
