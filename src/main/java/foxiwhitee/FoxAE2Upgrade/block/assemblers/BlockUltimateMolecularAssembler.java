package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import appeng.util.Platform;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUltimateMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockUltimateMolecularAssembler(String name) {
        super(name);
        setTileEntity(TileUltimateMolecularAssembler.class);
    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileUltimateMolecularAssembler tile = (TileUltimateMolecularAssembler) world.getTileEntity(x, y, z);
        if (tile != null) {
            if (Platform.isServer())
                Platform.openGUI(player, (TileEntity) tile, ForgeDirection.getOrientation(side), CommonProxy.getGuiUltimateMolecularAssembler());
            return true;
        }
        return false;
    }

}
