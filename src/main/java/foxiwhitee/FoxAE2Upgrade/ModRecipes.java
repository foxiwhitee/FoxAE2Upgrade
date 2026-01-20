package foxiwhitee.FoxAE2Upgrade;

import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipesLocation;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    @RecipesLocation(modId = "foxae2upgrade")
    public static final String[] recipes = {"recipes"};

    public static List<BaseAutoBlockRecipe> autoCrystallizerRecipes = new ArrayList<>();
    public static List<BaseAutoBlockRecipe> autoPressRecipes = new ArrayList<>();

    public static void registerRecipes() {

    }

}
