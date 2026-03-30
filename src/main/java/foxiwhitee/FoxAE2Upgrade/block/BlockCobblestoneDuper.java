package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockCobblestoneDuper extends BlockApplied {
    public BlockCobblestoneDuper(String name) {
        super(name, TileCobblestoneDuper.class);
        setHardness(0.5F);
    }

    @Override
    public String getFolder() {
        return "cobblestoneDuper/";
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        list.add(LocalizationUtils.localize("tooltip.cobblestoneDuper"));
    }
}
