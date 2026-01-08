package foxiwhitee.FoxAE2Upgrade.block.assemblers;

import appeng.util.Platform;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.client.gui.GuiCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.client.gui.assemblers.GuiQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.handler.GuiHandlers;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SimpleGuiHandler(tile = TileQuantumMolecularAssembler.class, gui = GuiQuantumMolecularAssembler.class, container = ContainerQuantumMolecularAssembler.class)
public class BlockQuantumMolecularAssembler extends BlockCustomMolecularAssembler{
    public BlockQuantumMolecularAssembler(String name) {
        super(name, TileQuantumMolecularAssembler.class);
    }
}
