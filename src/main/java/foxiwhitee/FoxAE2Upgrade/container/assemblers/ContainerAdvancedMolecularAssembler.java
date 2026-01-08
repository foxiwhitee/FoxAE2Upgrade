package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import appeng.container.AEBaseContainer;
import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileCustomMolecularAssembler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerAdvancedMolecularAssembler extends AEBaseContainer {
    public ContainerAdvancedMolecularAssembler(EntityPlayer ip, TileAdvancedMolecularAssembler te) {
        super(ip.inventory, te, null);
        for (int x = 0; x < 36; x++) {
            int y = x / 9;
            addSlotToContainer((Slot) new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, te.getInternalInventory(), x, 8 + 18 * x - 162 * y, 29 + 18 * y,
                getInventoryPlayer()));
        }
        bindPlayerInventory(ip.inventory, 0, 124);
    }
}
