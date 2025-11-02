package foxiwhitee.FoxAE2Upgrade.recipes;

import com.google.gson.JsonObject;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.recipes.RecipeUtils;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AutoCrystallizerCraft implements IJsonRecipe<ItemStack, ItemStack> {
    private ItemStack[] outputs;
    private ItemStack[] inputs;

    public AutoCrystallizerCraft() {}

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
        return false;
    }

    @Override
    public String getType() {
        return "autoCrystallizer";
    }

    @Override
    public IJsonRecipe create(JsonObject data) {
        try {
            this.outputs = new ItemStack[] { RecipeUtils.getOutput(data) };
            this.inputs = (ItemStack[]) RecipeUtils.getInputs(data, hasOreDict());
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
            throw new IllegalArgumentException("Inputs cannot be empty for autoCrystallizer recipe");
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(outputs[0], Arrays.asList(inputs));
        ModRecipes.autoCrystallizerRecipes.add(recipe);
    }

    @Override
    public String addCraftByMineTweaker() {
        return IJsonRecipe.super.addCraftByMineTweaker();
    }

    @Override
    public String removeCraftByMineTweaker() {
        return IJsonRecipe.super.removeCraftByMineTweaker();
    }
}
