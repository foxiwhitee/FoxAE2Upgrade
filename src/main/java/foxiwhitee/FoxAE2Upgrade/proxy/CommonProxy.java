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
import foxiwhitee.FoxAE2Upgrade.container.ContainerCustomMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileBaseMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileHybridMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import net.minecraftforge.common.util.EnumHelper;

public class CommonProxy {
    private static GuiBridge GUI_ADV_ME_DRIVE = null;
    private static GuiBridge GUI_BASE_MOLECULAR_ASSEMBLER = null;
    private static GuiBridge GUI_HYBRID_MOLECULAR_ASSEMBLER = null;
    private static GuiBridge GUI_ULTIMATE_MOLECULAR_ASSEMBLER = null;

    public static GuiBridge getGuiAdvMeDrive() {
        return GUI_ADV_ME_DRIVE;
    }

    public static GuiBridge getGuiBaseMolecularAssembler() {
        return GUI_BASE_MOLECULAR_ASSEMBLER;
    }

    public static GuiBridge getGuiHybridMolecularAssembler() {
        return GUI_HYBRID_MOLECULAR_ASSEMBLER;
    }

    public static GuiBridge getGuiUltimateMolecularAssembler() {
        return GUI_ULTIMATE_MOLECULAR_ASSEMBLER;
    }

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModRecipes.registerRecipes();

    }

    public void init(FMLInitializationEvent event) {
        if (ContentConfig.enableMolecularAssemblers) {
            Api.INSTANCE.registries().interfaceTerminal().register(TileBaseMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileHybridMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileUltimateMolecularAssembler.class);
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
        if (ContentConfig.enableAdvancedDriver) {
            GUI_ADV_ME_DRIVE = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "AdvMEDrive", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerAdvancedDrive.class, TileAdvancedDrive.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        }
        if (ContentConfig.enableMolecularAssemblers) {
            GUI_BASE_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "BaseModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerCustomMolecularAssembler.class, TileBaseMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
            GUI_HYBRID_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "HybridModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerCustomMolecularAssembler.class, TileHybridMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
            GUI_ULTIMATE_MOLECULAR_ASSEMBLER = (GuiBridge) EnumHelper.addEnum(GuiBridge.class, "UltimateModularAssembler", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerCustomMolecularAssembler.class, TileUltimateMolecularAssembler.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        }
    }
}
