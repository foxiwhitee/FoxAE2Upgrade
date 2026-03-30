package foxiwhitee.FoxAE2Upgrade.container;

import appeng.container.AEBaseContainer;
import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCobblestoneDuper extends AEBaseContainer {
    public ContainerCobblestoneDuper(EntityPlayer ip, TileCobblestoneDuper chest) {
        super(ip.inventory, chest);
        addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.STORAGE_CELLS, chest, 0, 97, 44, getInventoryPlayer()));
        bindPlayerInventory(ip.inventory, 17, 117);
    }
}
