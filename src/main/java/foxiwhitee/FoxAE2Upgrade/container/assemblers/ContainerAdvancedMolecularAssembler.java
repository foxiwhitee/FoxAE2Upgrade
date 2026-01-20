package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerAdvancedMolecularAssembler extends FoxBaseContainer {
    public ContainerAdvancedMolecularAssembler(EntityPlayer ip, TileAdvancedMolecularAssembler te) {
        super(ip, te);

        bindPlayerInventory(ip.inventory, 17, 117);

        for (int x = 0; x < 36; x++) {
            int y = x / 9;
            addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, te.getInternalInventory(), x, 17 + 18 * x - 162 * y, 22 + 18 * y,
                getInventoryPlayer()));
        }
    }
}
