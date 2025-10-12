package foxiwhitee.FoxAE2Upgrade.event;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ServerEventHandler {

    @SubscribeEvent
    public final void worldLoad(WorldEvent.Load e) {
        //CellInventory.addBasicBlackList(Item.getIdFromItem(ItemFluidDrop.DROP), 32767);
        //CustomCellInventory.addBasicBlackList(Item.getIdFromItem(ItemFluidDrop.DROP), 32767);
    }
}
