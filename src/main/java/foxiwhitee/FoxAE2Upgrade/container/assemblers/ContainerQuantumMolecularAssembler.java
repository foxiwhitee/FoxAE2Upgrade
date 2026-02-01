package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerQuantumMolecularAssembler extends FoxBaseContainer {
    public ContainerQuantumMolecularAssembler(EntityPlayer ip, TileQuantumMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(17, 261);

        for (int x = 0; x < 108; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotFiltered("encodedPattern", te.getInternalInventory(), x, 25 + 18 * x - 162 * y, 22 + 18 * y));
        }
    }
}
