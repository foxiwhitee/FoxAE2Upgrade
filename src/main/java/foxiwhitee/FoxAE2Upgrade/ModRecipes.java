package foxiwhitee.FoxAE2Upgrade;

import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.recipes.BaseAutoBlockRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModRecipes {
    public static List<BaseAutoBlockRecipe> autoCrystallizerRecipes = new ArrayList<>();
    public static List<BaseAutoBlockRecipe> autoPressRecipes = new ArrayList<>();

    public static void registerRecipes() {
        ItemStack additionalStack;
        // Auto Crystallizer
        registerAutoCrystallizerRecipe(AEApi.instance().definitions().materials().fluixCrystal().maybeStack(2).get(),
            AEApi.instance().definitions().materials().certusQuartzCrystalCharged().maybeStack(1).get(),
            new ItemStack(Items.redstone),
            new ItemStack(Items.quartz));
        registerAutoCrystallizerRecipe(AEApi.instance().definitions().materials().certusQuartzCrystalCharged().maybeStack(1).get(),
            AEApi.instance().definitions().materials().certusQuartzCrystal().maybeStack(1).get());
        additionalStack = withProgressAndMeta(AEApi.instance().definitions().items().crystalSeed().maybeStack(1).get(), 0, 0);
        registerAutoCrystallizerRecipe(AEApi.instance().definitions().materials().purifiedCertusQuartzCrystal().maybeStack(1).get(),
            additionalStack);
        additionalStack = withProgressAndMeta(AEApi.instance().definitions().items().crystalSeed().maybeStack(1).get(), 600, 600);
        registerAutoCrystallizerRecipe(AEApi.instance().definitions().materials().purifiedNetherQuartzCrystal().maybeStack(1).get(),
            additionalStack);
        additionalStack = withProgressAndMeta(AEApi.instance().definitions().items().crystalSeed().maybeStack(1).get(), 1200, 1200);
        registerAutoCrystallizerRecipe(AEApi.instance().definitions().materials().purifiedFluixCrystal().maybeStack(1).get(),
            additionalStack);
        // Auto Press
        registerAutoPressRecipe(AEApi.instance().definitions().materials().siliconPrint().maybeStack(1).get(),
            AEApi.instance().definitions().materials().silicon().maybeStack(1).get());
        registerAutoPressRecipe(AEApi.instance().definitions().materials().logicProcessor().maybeStack(1).get(),
            AEApi.instance().definitions().materials().logicProcessorPrint().maybeStack(1).get(),
            AEApi.instance().definitions().materials().siliconPrint().maybeStack(1).get(),
            new ItemStack(Items.redstone));
        registerAutoPressRecipe(AEApi.instance().definitions().materials().logicProcessor().maybeStack(1).get(),
            new ItemStack(Items.gold_ingot));
        registerAutoPressRecipe(AEApi.instance().definitions().materials().calcProcessor().maybeStack(1).get(),
            AEApi.instance().definitions().materials().calcProcessorPrint().maybeStack(1).get(),
            AEApi.instance().definitions().materials().siliconPrint().maybeStack(1).get(),
            new ItemStack(Items.redstone));
        registerAutoPressRecipe(AEApi.instance().definitions().materials().calcProcessorPrint().maybeStack(1).get(),
            AEApi.instance().definitions().materials().purifiedCertusQuartzCrystal().maybeStack(1).get());
        registerAutoPressRecipe(AEApi.instance().definitions().materials().engProcessor().maybeStack(1).get(),
            AEApi.instance().definitions().materials().engProcessorPrint().maybeStack(1).get(),
            AEApi.instance().definitions().materials().siliconPrint().maybeStack(1).get(),
            new ItemStack(Items.redstone));
        registerAutoPressRecipe(AEApi.instance().definitions().materials().engProcessorPrint().maybeStack(1).get(),
            new ItemStack(Items.diamond));
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

    public static BaseAutoBlockRecipe registerAutoCrystallizerRecipe(ItemStack output, Object... inputs) {
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(output, new ArrayList<>(Arrays.asList(inputs)));
        autoCrystallizerRecipes.add(recipe);
        return recipe;
    }

    public static BaseAutoBlockRecipe registerAutoPressRecipe(ItemStack output, Object... inputs) {
        BaseAutoBlockRecipe recipe = new BaseAutoBlockRecipe(output, new ArrayList<>(Arrays.asList(inputs)));
        autoPressRecipes.add(recipe);
        return recipe;
    }

}
