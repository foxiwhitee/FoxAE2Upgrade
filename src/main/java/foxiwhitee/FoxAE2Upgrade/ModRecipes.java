package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import foxiwhitee.FoxLib.recipes.RecipesLocation;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRecipes {

    @RecipesLocation(modId = "foxae2upgrade")
    public static final String[] recipes = {"recipes"};

    public static List<BaseAutoBlockRecipe> autoCrystallizerRecipes = new ArrayList<>();
    public static List<BaseAutoBlockRecipe> autoPressRecipes = new ArrayList<>();

    public static void registerRecipes() {

    }

}
