package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerUltimateMolecularAssembler extends FoxBaseContainer {
    public ContainerUltimateMolecularAssembler(EntityPlayer ip, TileUltimateMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(ip.inventory, 17, 261);

        for (int x = 0; x < 72; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, te.getInternalInventory(), x, 17 + 18 * x - 162 * y, 22 + 18 * y,
                getInventoryPlayer()));
        }
    }
}
