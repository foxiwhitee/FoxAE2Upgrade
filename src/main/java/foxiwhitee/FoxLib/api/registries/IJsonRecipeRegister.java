package foxiwhitee.FoxLib.api.registries;

import foxiwhitee.FoxLib.recipes.IJsonRecipe;

public interface IJsonRecipeRegister {
    void register(Class<? extends IJsonRecipe> recipe, String name);
}
