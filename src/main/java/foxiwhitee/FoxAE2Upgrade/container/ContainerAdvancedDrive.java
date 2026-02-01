package foxiwhitee.FoxAE2Upgrade.container;

import appeng.container.AEBaseContainer;
import appeng.container.ContainerOpenContext;
import appeng.container.slot.SlotRestrictedInput;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerAdvancedDrive extends AEBaseContainer {
    public ContainerAdvancedDrive(EntityPlayer ip, TileAdvancedDrive drive) {
        super(ip.inventory, drive);

        bindPlayerInventory(ip.inventory, 22, 147);
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.STORAGE_CELLS, drive.getInternalInventory(), j + i * 5, 66 + j * 18, 26 + i * 18, ip.inventory));
            }
        }

        ContainerOpenContext context = new ContainerOpenContext(drive);
        context.setWorld(drive.getWorldObj());
        context.setX(drive.xCoord);
        context.setY(drive.yCoord);
        context.setZ(drive.zCoord);
        context.setSide(ForgeDirection.UNKNOWN);
        setOpenContext(context);
    }
}

