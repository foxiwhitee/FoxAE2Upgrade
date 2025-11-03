package foxiwhitee.FoxAE2Upgrade.container.assemblers;

import appeng.container.AEBaseContainer;
import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class ContainerQuantumMolecularAssembler extends AEBaseContainer {
    public ContainerQuantumMolecularAssembler(InventoryPlayer ip, TileQuantumMolecularAssembler te) {
        super(ip, te, null);
        for (int x = 0; x < 108; x++) {
            int y = x / 9;
            addSlotToContainer((Slot) new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, te.getInternalInventory(), x, 8 + 18 * x - 162 * y, 29 + 18 * y,
                getInventoryPlayer()));
        }
        bindPlayerInventory(ip, 0, 268);
    }
}
