package foxiwhitee.FoxLib;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxLib.config.ConfigHandler;
import foxiwhitee.FoxLib.integration.IntegrationLoader;
import foxiwhitee.FoxLib.items.ItemProductivityCard;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.recipes.RecipesHandler;
import foxiwhitee.FoxLib.recipes.RecipesLocation;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import foxiwhitee.FoxLib.utils.handler.GuiHandlerRegistry;
import foxiwhitee.FoxLib.utils.helpers.GuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.io.IOException;
import java.net.URISyntaxException;

import static foxiwhitee.FoxLib.FoxLib.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class FoxLib {
    public static final String
        MODID = "foxlib",
        MODNAME = "FoxLib",
        VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static FoxLib instance;

    public static final CreativeTabs FOX_TAB = new CreativeTabs("FOX_LIB_TAB") {
        @Override
        public Item getTabIconItem() {
            return PRODUCTIVITY_CARDS;
        }
    };

    @RecipesLocation(modId = "foxlib")
    public static final String[] recipes = {"recipes"};

    public static final Item PRODUCTIVITY_CARDS = new ItemProductivityCard("productivityCard");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ConfigHandler.loadConfigs(e);
        RecipesHandler.loadRecipesLocations(e);
        GuiHandlerRegistry.registerGuiHandlers(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        IntegrationLoader.preInit(e);

        RegisterUtils.registerItem(PRODUCTIVITY_CARDS);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        IntegrationLoader.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        NetworkManager.instance = new NetworkManager("FoxLib");
        IntegrationLoader.postInit(e);
        RecipesHandler.init();
    }
}
