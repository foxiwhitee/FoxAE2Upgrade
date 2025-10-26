package foxiwhitee.FoxLib;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import foxiwhitee.FoxLib.config.ConfigHandler;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.handler.GuiHandlerRegistry;
import foxiwhitee.FoxLib.utils.helpers.GuiHandler;

import static foxiwhitee.FoxLib.FoxLib.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION)
public class FoxLib {
    public static final String
        MODID = "foxlib",
        MODNAME = "FoxLib",
        VERSION = "1.0.0";

    @Mod.Instance(MODID)
    public static FoxLib instance;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        ConfigHandler.loadConfigs(e);
        GuiHandlerRegistry.registerGuiHandlers(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        foxiwhitee.FoxAE2Upgrade.integration.IntegrationLoader.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        foxiwhitee.FoxAE2Upgrade.integration.IntegrationLoader.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        NetworkManager.instance = new NetworkManager("FoxLib");
        foxiwhitee.FoxAE2Upgrade.integration.IntegrationLoader.postInit(e);
    }
}
