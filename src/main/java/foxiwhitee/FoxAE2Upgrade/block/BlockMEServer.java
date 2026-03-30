package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockMEServer extends BlockApplied {
    public BlockMEServer(String name) {
        super(name, TileMEServer.class);
    }

    @Override
    public String getFolder() {
        return "meServer/";
    }

    @Override
    protected boolean hasUpDownRotate() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        list.add(LocalizationUtils.localize("tooltip.meServer"));
    }
}
