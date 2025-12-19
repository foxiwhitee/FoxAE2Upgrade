package foxiwhitee.FoxAE2Upgrade.recipes;

import appeng.recipes.Ingredient;
import com.google.gson.JsonObject;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.value.IAny;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AutoPressCraft implements IJsonRecipe<ItemStack, ItemStack> {
    private ItemStack[] outputs;
    private ItemStack[] inputs;

    public AutoPressCraft() {
    }

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public ItemStack[] getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(List<ItemStack> objects) {
        List<ItemStack> ins = new ArrayList<>(Arrays.asList(getInputs()));
        List<ItemStack> inCopy = new ArrayList<>(objects);

        for (Iterator<ItemStack> it = inCopy.iterator(); it.hasNext(); ) {
            ItemStack obj = it.next();
            if (obj == null) continue;

            for (int i = 0; i < ins.size(); i++) {
                ItemStack input = ins.get(i);
                if (input == null) continue;

                if (IFoxRecipe.simpleAreStacksEqual(input, obj)) {
                    ins.remove(i);
                    it.remove();
                    break;
                }
            }
        }

        return ins.isEmpty() && inCopy.isEmpty();

    }

    @Override
    public boolean hasOreDict() {
        return true;
    }

    @Override
    public boolean hasMineTweakerIntegration() {
        return true;
    }

    @Override
    public String getType() {
        return "autoPress";
    }

    @Override
    public IJsonRecipe create(JsonObject data) {
        try {
            this.outputs = new ItemStack[]{RecipeUtils.getOutput(data)};
            Object[] objects = RecipeUtils.getInputs(data, hasOreDict());
            this.inputs = new ItemStack[objects.length];
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] instanceof ItemStack stack) {
                    inputs[i] = stack.copy();
                } else if (objects[i] instanceof String string) {
                    List<ItemStack> list = OreDictionary.getOres(string);
                    inputs[i] = list.get(0);
                }
            }
        } catch (RuntimeException e) {
            if (e.getMessage().startsWith("Item not found:")) {
                this.inputs = null;
                this.outputs = null;
            } else {
                throw e;
            }
        }
        return this;
    }

    @Override
    public void register() {
        if (this.outputs == null && this.inputs == null) {
            return;
        }
        if (inputs == null || inputs.length == 0)
            throw new IllegalArgumentException("Inputs cannot be empty for autoPress recipe");
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(outputs[0], Arrays.asList((Object[]) inputs));
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
