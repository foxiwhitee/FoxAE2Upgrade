package foxiwhitee.FoxAE2Upgrade.recipes;

import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.json.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.JsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipeOutput;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipeValue;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonRecipe(value = "autoCrystallizer", hasMineTweaker = true)
public class AutoCrystallizerCraft implements IJsonRecipe {
    @RecipeOutput
    @RecipeValue("output")
    private ItemStack output;

    @RecipeValue("inputs")
    private List<ItemStack> inputs;

    public AutoCrystallizerCraft() {
    }

    @Override
    public void register() {
        if (this.output == null || this.inputs == null || this.inputs.isEmpty()) {
            return;
        }
        List<Object> objectList = inputs.stream()
            .map(item -> (Object) item)
            .collect(Collectors.toList());
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(output, objectList);
        ModRecipes.autoCrystallizerRecipes.add(recipe);
    }

    @Override
    public void addCraftByMineTweaker(IItemStack stack, IIngredient... inputs) {
        Object[] objects = new Object[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            IIngredient ingredient = inputs[i];
            objects[i] = ingredient.getInternal();
        }
        ItemStack real = (ItemStack) stack.getInternal();
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(real, Arrays.asList(objects));
        ModRecipes.autoCrystallizerRecipes.add(recipe);
    }

    @Override
    public void removeCraftByMineTweaker(IItemStack stack) {
        ItemStack real = (ItemStack) stack.getInternal();
        for (BaseAutoBlockRecipe recipe : ModRecipes.autoCrystallizerRecipes) {
            if (IFoxRecipe.simpleAreStacksEqual(real, recipe.getOut())) {
                ModRecipes.autoCrystallizerRecipes.remove(recipe);
                break;
            }
        }
    }
}
