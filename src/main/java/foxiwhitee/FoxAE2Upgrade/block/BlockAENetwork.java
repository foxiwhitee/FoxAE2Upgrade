package foxiwhitee.FoxAE2Upgrade.block;

import appeng.block.AEBaseTileBlock;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockAENetwork extends AEBaseTileBlock {
    public BlockAENetwork(String name, Class<? extends TileEntity> tile) {
        super(Material.iron);
        setBlockName(name);
        setTileEntity(tile);
        setCreativeTab(FoxCore.FOX_TAB);
        setHardness(1.0F);
        setBlockTextureName(FoxCore.MODID + ":" + name);
        this.lightOpacity = 1;
    }
}
