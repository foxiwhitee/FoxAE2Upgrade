package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRecipes {

    public static void registerRecipes() {

    }


    public static ItemStack withProgressAndMeta(ItemStack stack, int progress, int meta) {
        stack.setItemDamage(meta);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("progress", progress);
        stack.setTagCompound(tag);
        return stack;
    }

    public static void removeCraftingRecipe(ItemStack output) {
        if (output == null) return;

        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        List<IRecipe> toRemove = new ArrayList<>();

        for (IRecipe recipe : recipes) {
            if (recipe != null && recipe.getRecipeOutput() != null &&
                    ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), output)) {
                toRemove.add(recipe);
            }
        }

        recipes.removeAll(toRemove);
    }

}
