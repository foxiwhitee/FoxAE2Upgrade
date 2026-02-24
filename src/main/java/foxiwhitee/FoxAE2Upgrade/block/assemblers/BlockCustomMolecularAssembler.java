package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.block.BlockApplied;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public abstract class BlockCustomMolecularAssembler extends BlockApplied {
    public BlockCustomMolecularAssembler(String name, Class<? extends TileEntity> tile) {
        super(name, tile);
        setBlockTextureName(FoxCore.MODID + ":drive/advancedDriveBottom");
        renderId = -1;
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
