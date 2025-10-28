package foxiwhitee.FoxAE2Upgrade.container;

import appeng.container.AEBaseContainer;
import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.container.slots.CustomSlotRestrictedInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerCobblestoneDuper extends AEBaseContainer {

    public ContainerCobblestoneDuper(EntityPlayer ip, TileCobblestoneDuper chest) {
        super(ip.inventory, chest, null);
        addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.STORAGE_CELLS, chest, 1, 61, 44, getInventoryPlayer()));
        addSlotToContainer(new CustomSlotRestrictedInput(CustomSlotRestrictedInput.PlacableItemType.PRODUCTIVITY, chest, 2, 133, 44, getInventoryPlayer()));
        bindPlayerInventory(ip.inventory, 17, 117);
    }
}
