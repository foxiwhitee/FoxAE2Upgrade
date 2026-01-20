package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerQuantumMolecularAssembler extends FoxBaseContainer {
    public ContainerQuantumMolecularAssembler(EntityPlayer ip, TileQuantumMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(ip.inventory, 17, 189);

        for (int x = 0; x < 108; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, te.getInternalInventory(), x, 17 + 18 * x - 162 * y, 22 + 18 * y,
                getInventoryPlayer()));
        }
    }
}
