package foxiwhitee.FoxLib.recipes.crafts;

import foxiwhitee.FoxLib.recipes.IFoxRecipe;
import foxiwhitee.FoxLib.recipes.IJsonRecipe;
import foxiwhitee.FoxLib.utils.helpers.OreDictUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessCraft implements IJsonRecipe<Object, ItemStack> {
    private ItemStack[] outputs;
    private Object[] inputs;

    public ShapelessCraft(ItemStack out, Object... inputs) {
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
    public boolean matches(List<Object> inputs) {
        List<Object> ins = new ArrayList<>(List.of(this.getInputs()));
        List<Object> inCopy = new ArrayList<>(inputs);

        for (Iterator<Object> it = inCopy.iterator(); it.hasNext(); ) {
            Object obj = it.next();
            if (obj == null) continue;

            for (int i = 0; i < ins.size(); i++) {
                Object input = ins.get(i);
                boolean match = false;

                if (input instanceof String && obj instanceof String) {
                    match = input.equals(obj);
                } else if (input instanceof ItemStack && obj instanceof ItemStack) {
                    match = IFoxRecipe.simpleAreStacksEqual((ItemStack) input, (ItemStack) obj);
                } else if (input instanceof String && obj instanceof ItemStack) {
                    for (ItemStack ore : OreDictionary.getOres((String) input)) {
                        if (IFoxRecipe.simpleAreStacksEqual(ore, (ItemStack) obj)) {
                            match = true;
                            break;
                        }
                    }
                }

                if (match) {
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
        return false;
    }

    @Override
    public String getType() {
        return "workbenchShapeless";
    }
}
