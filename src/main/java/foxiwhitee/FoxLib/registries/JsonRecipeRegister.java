package foxiwhitee.FoxLib.registries;

import foxiwhitee.FoxLib.api.registries.IJsonRecipeRegister;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.crafts.ShapedCraft;
import foxiwhitee.FoxLib.recipes.crafts.ShapelessCraft;

import java.util.HashMap;
import java.util.Map;

public class JsonRecipeRegister implements IJsonRecipeRegister {
    private final Map<String, Class<? extends IJsonRecipe>> recipes = new HashMap<>();

    public JsonRecipeRegister() {
        register(ShapedCraft.class, "workbenchShaped");
        register(ShapelessCraft.class, "workbenchShapeless");
    }

    @Override
    public void register(Class<? extends IJsonRecipe> recipe, String name) {
        recipes.put(name, recipe);
    }
}
