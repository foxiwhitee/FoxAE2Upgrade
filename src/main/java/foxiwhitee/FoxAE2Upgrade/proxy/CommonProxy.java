package foxiwhitee.FoxAE2Upgrade.proxy;

import appeng.api.config.SecurityPermissions;
import appeng.core.Api;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.GuiHostType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.container.ContainerAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.recipes.AutoCrystallizerCraft;
import foxiwhitee.FoxAE2Upgrade.recipes.AutoPressCraft;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.api.FoxLibApi;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {
    private static GuiBridge GUI_ADV_ME_DRIVE = null;
    private static GuiBridge GUI_ADVANCED_MOLECULAR_ASSEMBLER = null;
    private static GuiBridge GUI_ULTIMATE_MOLECULAR_ASSEMBLER = null;
    private static GuiBridge GUI_QUANTUM_MOLECULAR_ASSEMBLER = null;

    public static GuiBridge getGuiAdvMeDrive() {
        return GUI_ADV_ME_DRIVE;
    }

    public static GuiBridge getGuiAdvancedMolecularAssembler() {
        return GUI_ADVANCED_MOLECULAR_ASSEMBLER;
    }

    public static GuiBridge getGuiUltimateMolecularAssembler() {
        return GUI_ULTIMATE_MOLECULAR_ASSEMBLER;
    }

    public static GuiBridge getGuiQuantumMolecularAssembler() {
        return GUI_QUANTUM_MOLECULAR_ASSEMBLER;
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModRecipes.registerRecipes();
    }

    public void init(FMLInitializationEvent event) {
        if (ContentConfig.enableMolecularAssemblers) {
            Api.INSTANCE.registries().interfaceTerminal().register(TileAdvancedMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileUltimateMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileQuantumMolecularAssembler.class);
        }
        if (ContentConfig.enableCables) {
            for (int i = 0; i < 17; i++) {
                OreDictionary.registerOre("cableAlite", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
            for (int i = 17; i < 34; i++) {
                OreDictionary.registerOre("cableBimare", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
            for (int i = 34; i < 51; i++) {
                OreDictionary.registerOre("cableDefit", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
            for (int i = 51; i < 68; i++) {
                OreDictionary.registerOre("cableEfrim", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
            for (int i = 68; i < 85; i++) {
                OreDictionary.registerOre("cableNur", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
            for (int i = 85; i < 102; i++) {
                OreDictionary.registerOre("cableXaur", new ItemStack(ModItems.ITEM_PARTS, 1, i));
            }
        }
        if (ContentConfig.enableAutoPress) {
            FoxLibApi.instance.registries().registerJsonRecipe().register(AutoPressCraft.class, "autoPress");
        }
        if (ContentConfig.enableAutoCrystallizer) {
            FoxLibApi.instance.registries().registerJsonRecipe().register(AutoCrystallizerCraft.class, "autoCrystallizer");
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (ContentConfig.enableAdvancedDriver) {
            GUI_ADV_ME_DRIVE = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "AdvMEDrive", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerAdvancedDrive.class, TileAdvancedDrive.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        }
        if (ContentConfig.enableMolecularAssemblers) {
            GUI_ADVANCED_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "AdvancedModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerAdvancedMolecularAssembler.class, TileAdvancedMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
            GUI_ULTIMATE_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "UltimateModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerUltimateMolecularAssembler.class, TileUltimateMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
            GUI_QUANTUM_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "QuantumModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerQuantumMolecularAssembler.class, TileQuantumMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        }
    }
}
