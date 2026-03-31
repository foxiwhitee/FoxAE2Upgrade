package foxiwhitee.FoxAE2Upgrade.container;

import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerMEServer extends FoxBaseContainer {
    public ContainerMEServer(EntityPlayer ip, TileMEServer myTile) {
        super(ip, myTile);
        bindPlayerInventory(70, 184);

        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 0, 60, 37));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 1, 141, 37));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 2, 222, 37));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 3, 60, 71));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 4, 141, 71));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 5, 222, 71));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 6, 60, 105));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 7, 141, 105));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 8, 222, 105));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 9, 60, 139));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 10, 141, 139));
        this.addSlotToContainer(new SlotFiltered("storage", myTile.getStorage(), 11, 222, 139));

        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 0, 78, 37));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 1, 159, 37));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 2, 240, 37));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 3, 78, 71));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 4, 159, 71));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 5, 240, 71));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 6, 78, 105));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 7, 159, 105));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 8, 240, 105));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 9, 78, 139));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 10, 159, 139));
        this.addSlotToContainer(new SlotFiltered("accelerator", myTile.getAccelerators(), 11, 240, 139));
    }

}
