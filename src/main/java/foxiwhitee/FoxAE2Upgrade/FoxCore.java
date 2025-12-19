package foxiwhitee.FoxAE2Upgrade;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static foxiwhitee.FoxAE2Upgrade.FoxCore.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = DEPEND)
public class FoxCore {
    public static final String
        MODID = "foxae2upgrade",
        MODNAME = "FoxAE2Upgrade",
        VERSION = "1.0.4",
        DEPEND = "required-after:appliedenergistics2;required-after:foxlib;";

    public static final CreativeTabs FOX_TAB = new CreativeTabs("FOX_AE2_UPGRADE_TAB") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.QUANTUM_MOLECULAR_ASSEMBLER);
        }
    };

    @Mod.Instance(MODID)
    public static FoxCore instance;

    @SidedProxy(clientSide = "foxiwhitee.FoxAE2Upgrade.proxy.ClientProxy", serverSide = "foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
