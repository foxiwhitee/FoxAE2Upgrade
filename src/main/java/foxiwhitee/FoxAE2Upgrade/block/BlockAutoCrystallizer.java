package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.auto.TileAutoCrystallizer;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockAutoCrystallizer extends BlockApplied {
    public BlockAutoCrystallizer(String name) {
        super(name, TileAutoCrystallizer.class);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        list.add(LocalizationUtils.localize("tooltip.autoCrystallizer"));
    }
}
