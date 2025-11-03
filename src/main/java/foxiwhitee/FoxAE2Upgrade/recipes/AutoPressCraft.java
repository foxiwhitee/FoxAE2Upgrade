package foxiwhitee.FoxAE2Upgrade.recipes;

import com.google.gson.JsonObject;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
        return false;
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
            this.inputs = Arrays.copyOf(objects, objects.length, ItemStack[].class);
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
            objects[i] = ingredient.getInternal();
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
