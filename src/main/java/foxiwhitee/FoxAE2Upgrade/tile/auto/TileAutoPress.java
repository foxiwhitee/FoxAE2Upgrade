package foxiwhitee.FoxAE2Upgrade.tile.auto;

import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TileAutoPress extends TileAutomatedBlock{
    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return null;//new ItemStack(ModBlocks.AUTO_PRESS);
    }

    @Override
    protected List<BaseAutoBlockRecipe> getRecipes() {
        return null;//ModRecipes.autoPressRecipes;
    }

    @Override
    protected long getMaxCount() {
        return 0;//FoxConfig.speedAutoPress;
    }
}
