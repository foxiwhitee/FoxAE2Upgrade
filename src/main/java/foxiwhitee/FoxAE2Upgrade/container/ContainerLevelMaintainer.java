package foxiwhitee.FoxAE2Upgrade.container;

import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import foxiwhitee.FoxLib.container.slots.SlotFake;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLevelMaintainer extends FoxBaseContainer {
    public ContainerLevelMaintainer(EntityPlayer player, TileLevelMaintainer tileEntity) {
        super(player, tileEntity);

        bindPlayerInventory(22, 147);

        for (int i = 0; i < 6; i++) {
            addSlotToContainer(new SlotFake(tileEntity.getInternalInventory(), i, 35, 19 + i * 20));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
}
