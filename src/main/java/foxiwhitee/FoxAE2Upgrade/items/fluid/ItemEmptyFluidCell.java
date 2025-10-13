package foxiwhitee.FoxAE2Upgrade.items.fluid;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import net.minecraft.item.Item;

public final class ItemEmptyFluidCell extends Item {
    public ItemEmptyFluidCell(String name) {
        setUnlocalizedName(name);
        setTextureName(FoxCore.MODID + ":cells/" + name);
    }
}

