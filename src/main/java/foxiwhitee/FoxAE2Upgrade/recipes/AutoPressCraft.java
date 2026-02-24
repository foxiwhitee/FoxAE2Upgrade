package foxiwhitee.FoxAE2Upgrade.recipes;

import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.json.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.JsonRecipe;
import foxiwhitee.FoxLib.recipes.json.annotations.OreValue;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipeOutput;
import foxiwhitee.FoxLib.recipes.json.annotations.RecipeValue;
import foxiwhitee.FoxLib.utils.helpers.StackOreDict;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;
import java.util.stream.Collectors;

@JsonRecipe(value = "autoPress", hasMineTweaker = true, hasOreDict = true)
public class AutoPressCraft implements IJsonRecipe {
    @RecipeOutput
    @RecipeValue("output")
    private ItemStack output;

    @OreValue("inputs")
    private List<Object> inputs;

    public AutoPressCraft() {
    }

    @Override
    public void register() {
        if (this.output == null || this.inputs == null || inputs.isEmpty()) {
            return;
        }
        List<Object> objectList = inputs.stream()
            .map(item -> {
                if (item instanceof StackOreDict ore) {
                    List<ItemStack> stacks = OreDictionary.getOres(ore.getOre());
                    return stacks.get(0);
                }
                return item;
            })
            .collect(Collectors.toList());
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(output, objectList);
        ModRecipes.autoPressRecipes.add(recipe);
    }

    @Override
    public void addCraftByMineTweaker(IItemStack stack, IIngredient... inputs) {
        Object[] objects = new Object[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            IIngredient ingredient = inputs[i];
            if (ingredient instanceof IItemStack) {
                objects[i] = ingredient.getInternal();
            } else {
                objects[i] = ingredient.getItems().get(0);
            }
        }
        ItemStack real = (ItemStack) stack.getInternal();
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(real, Arrays.asList(objects));
        ModRecipes.autoPressRecipes.add(recipe);
    }

    @Override
    public void removeCraftByMineTweaker(IItemStack stack) {
        ItemStack real = (ItemStack) stack.getInternal();
        for (BaseAutoBlockRecipe recipe : ModRecipes.autoPressRecipes) {
            if (IFoxRecipe.simpleAreStacksEqual(real, recipe.getOut())) {
                ModRecipes.autoPressRecipes.remove(recipe);
                break;
            }
        }
    }
}
