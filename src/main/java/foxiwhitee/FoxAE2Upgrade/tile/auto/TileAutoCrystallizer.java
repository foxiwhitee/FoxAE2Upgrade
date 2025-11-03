package foxiwhitee.FoxAE2Upgrade.tile.auto;


import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TileAutoCrystallizer extends TileAutomatedBlock {
    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.AUTO_CRYSTALLIZER);
    }

    @Override
    protected List<BaseAutoBlockRecipe> getRecipes() {
        return ModRecipes.autoCrystallizerRecipes;
    }

    @Override
    protected long getMaxCount() {
        return FoxConfig.speedAutoCrystallizer;
    }
}
