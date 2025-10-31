package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.items.ItemProductivityCard;
import foxiwhitee.FoxAE2Upgrade.items.part.ItemParts;
import foxiwhitee.FoxLib.register.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item ITEM_PARTS = new ItemParts(AEApi.instance().partHelper());

    public static final Item PRODUCTIVITY_CARDS = new ItemProductivityCard("productivityCard");

    public static void registerItems() {
        RegisterUtils.registerItem(PRODUCTIVITY_CARDS);
        RegisterUtils.registerItem(ITEM_PARTS, "fox-part");
    }
}
