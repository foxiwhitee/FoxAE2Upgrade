package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.items.part.ItemParts;
import foxiwhitee.FoxLib.utils.helpers.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item ITEM_PARTS = new ItemParts(AEApi.instance().partHelper());

    public static void registerItems() {
        RegisterUtils.registerItem(ITEM_PARTS, "fox-part");
    }

}
