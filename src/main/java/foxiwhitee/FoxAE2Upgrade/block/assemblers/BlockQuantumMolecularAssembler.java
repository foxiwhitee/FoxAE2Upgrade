package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import appeng.util.Platform;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockQuantumMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockQuantumMolecularAssembler(String name) {
        super(name);
        setTileEntity(TileQuantumMolecularAssembler.class);
    }

    public boolean onActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileQuantumMolecularAssembler tile = (TileQuantumMolecularAssembler) world.getTileEntity(x, y, z);
        if (tile != null) {
            if (Platform.isServer())
                Platform.openGUI(player, (TileEntity) tile, ForgeDirection.getOrientation(side), CommonProxy.getGuiQuantumMolecularAssembler());
            return true;
        }
        return false;
    }

}
