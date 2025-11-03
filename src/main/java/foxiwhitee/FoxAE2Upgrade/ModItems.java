package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.items.ItemParts;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item ITEM_PARTS = new ItemParts(AEApi.instance().partHelper());

    public static void registerItems() {
        RegisterUtils.registerItem(ITEM_PARTS, "fox-part");
    }
}
