package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import appeng.block.AEBaseTileBlock;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.block.BlockApplied;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCustomMolecularAssembler extends BlockApplied {
    public BlockCustomMolecularAssembler(String name, Class<? extends TileEntity> tile) {
        super(name, tile);
        setBlockTextureName(FoxCore.MODID + ":driver/advancedDriverBottom");
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

    @Override
    public int getRenderType() {
        return -1;
    }
}
