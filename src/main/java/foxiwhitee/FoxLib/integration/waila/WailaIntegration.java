package foxiwhitee.FoxLib.integration.waila;

import appeng.tile.AEBaseTile;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

@Integration(modid = "Waila")
public class WailaIntegration implements IIntegration {
  public static void register(IWailaRegistrar registrar) {
    if (ContentConfig.enableCables) {
      IWailaDataProvider partHost = new PartWailaDataProvider();
      registrar.registerStackProvider(partHost, AEBaseTile.class);
      registrar.registerBodyProvider(partHost, AEBaseTile.class);
      registrar.registerNBTProvider(partHost, AEBaseTile.class);
    }
  }

  public void preInit(FMLPreInitializationEvent e) {}

  public void init(FMLInitializationEvent e) {
    FMLInterModComms.sendMessage("Waila", "register", WailaIntegration.class.getName() + ".register");
  }

  public void postInit(FMLPostInitializationEvent e) {}
}
