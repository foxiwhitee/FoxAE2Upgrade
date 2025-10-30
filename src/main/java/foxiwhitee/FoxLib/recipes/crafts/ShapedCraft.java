package foxiwhitee.FoxLib.recipes.crafts;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapedCraft implements IJsonRecipe<Object, ItemStack> {
    private ItemStack[] outputs;
    private Object[] inputs;

    public ShapedCraft(ItemStack out, Object... inputs) {
        this.inputs = inputs;
        this.outputs = new ItemStack[]{out};

    }

    @Override
    public ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public Object[] getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(List<Object> objects) {
        if (inputs.length != objects.size()) {
            return false;
        }
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] instanceof String && objects.get(i) instanceof String) {
                if (!inputs[i].equals(objects.get(i))) {
                    return false;
                }
            } else if (inputs[i] instanceof String && objects.get(i) instanceof ItemStack) {
                if(!OreDictUtil.areStacksEqual(inputs[i], (ItemStack) objects.get(i))) {
                    return false;
                }
            } else if (inputs[i] instanceof ItemStack && objects.get(i) instanceof String) {
                if(!OreDictUtil.areStacksEqual(objects.get(i), (ItemStack) inputs[i])) {
                    return false;
                }
            } else if (inputs[i] instanceof ItemStack && objects.get(i) instanceof ItemStack) {
                if(!IFoxRecipe.simpleAreStacksEqual((ItemStack) inputs[i], (ItemStack) objects.get(i))) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasOreDict() {
        return true;
    }

    @Override
    public boolean hasMineTweakerIntegration() {
        return false;
    }

    @Override
    public String getType() {
        return "workbenchShaped";
    }
}
