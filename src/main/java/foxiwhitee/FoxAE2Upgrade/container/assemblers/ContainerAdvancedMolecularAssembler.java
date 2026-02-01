package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerAdvancedMolecularAssembler extends FoxBaseContainer {
    public ContainerAdvancedMolecularAssembler(EntityPlayer ip, TileAdvancedMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(17, 117);

        for (int x = 0; x < 36; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotFiltered("encodedPattern", te.getInternalInventory(), x, 25 + 18 * x - 162 * y, 22 + 18 * y));
        }
    }
}
