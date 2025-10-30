package foxiwhitee.FoxLib.recipes;

import java.util.Arrays;
import java.util.List;

public interface IJsonRecipe<I, O> {
    O[] getOutputs();
    I[] getInputs();
    boolean matches(List<I> objects);
    boolean hasOreDict();
    boolean hasMineTweakerIntegration();
    String getType();

    default String addCraftByMineTweaker() {
        return "";
    }

    default String removeCraftByMineTweaker() {
        return "";
    }

    default boolean matches(I[] objects) {
        return matches(Arrays.asList(objects));
    }
}
