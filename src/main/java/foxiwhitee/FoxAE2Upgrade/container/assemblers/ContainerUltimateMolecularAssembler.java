package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerUltimateMolecularAssembler extends FoxBaseContainer {
    public ContainerUltimateMolecularAssembler(EntityPlayer ip, TileUltimateMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(17, 189);

        for (int x = 0; x < 72; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotFiltered("encodedPattern", te.getInternalInventory(), x, 25 + 18 * x - 162 * y, 22 + 18 * y));
        }
    }
}
