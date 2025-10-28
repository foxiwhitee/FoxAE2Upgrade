package foxiwhitee.FoxLib.container.slots;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItems;
import appeng.api.definitions.IMaterials;

import appeng.block.crafting.BlockCraftingStorage;
import appeng.block.crafting.BlockCraftingUnit;
import foxiwhitee.FoxAE2Upgrade.items.ItemProductivityCard;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CustomSlotRestrictedInput extends CustomAppEngSlot {
    private final PlacableItemType which;
    private final InventoryPlayer p;
    private boolean allowEdit = true;
    private int stackLimit = -1;

    public CustomSlotRestrictedInput(PlacableItemType valid, IInventory i, int slotIndex, int x, int y, InventoryPlayer p) {
        super(i, slotIndex, x, y);
        this.which = valid;
        this.p = p;
    }

    public int getSlotStackLimit() {
        return this.stackLimit != -1 ? this.stackLimit : super.getSlotStackLimit();
    }

    public Slot setStackLimit(int i) {
        this.stackLimit = i;
        return this;
    }

    public boolean isItemValid(ItemStack i) {
        if (!this.getContainer().isValidForSlot(this, i)) {
            return false;
        } else if (i == null) {
            return false;
        } else if (i.getItem() == null) {
            return false;
        } else if (!this.inventory.isItemValidForSlot(this.getSlotIndex(), i)) {
            return false;
        } else if (!this.isAllowEdit()) {
            return false;
        } else {
            IDefinitions definitions = AEApi.instance().definitions();
            IMaterials materials = definitions.materials();
            IItems items = definitions.items();
            switch (this.which) {
                case PRODUCTIVITY:
                    return i.getItem() instanceof ItemProductivityCard;
                case STORAGE:
                    return Block.getBlockFromItem(i.getItem()) instanceof BlockCraftingStorage;
                case ACCELERATOR:
                    return (Block.getBlockFromItem(i.getItem()) instanceof BlockCraftingUnit && !(Block.getBlockFromItem(i.getItem()) instanceof BlockCraftingStorage));
                default:
                    return false;
            }
        }
    }

    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return this.isAllowEdit();
    }

    public ItemStack getDisplayStack() {
        return super.getStack();
    }

    private boolean isAllowEdit() {
        return this.allowEdit;
    }


    public static enum PlacableItemType {
        STORAGE, PRODUCTIVITY, ACCELERATOR
    }
}
