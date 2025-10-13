package foxiwhitee.FoxAE2Upgrade.proxy;

import appeng.api.AEApi;
import appeng.api.config.SecurityPermissions;
import appeng.api.storage.ICellHandler;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.GuiHostType;
import appeng.helpers.IInterfaceHost;
import appeng.parts.misc.PartInterface;
import appeng.tile.misc.TileInterface;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.api.fluid.IFluidStorageGrid;
import foxiwhitee.FoxAE2Upgrade.config.ConfigHandler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.me.fluid.FluidCellHandler;
import foxiwhitee.FoxAE2Upgrade.me.fluid.FluidStorageGrid;
import foxiwhitee.FoxAE2Upgrade.utils.handler.GuiHandlerRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.loadConfigs(event);
        GuiHandlerRegistry.registerGuiHandlers(event);

        ModBlocks.registerBlocks();
        ModItems.registerItems();

    }

    public void init(FMLInitializationEvent event) {
        if (ContentConfig.enableMEFluid) {
            AEApi.instance().registries().cell().addCellHandler(new FluidCellHandler());
            AEApi.instance().registries().gridCache().registerGridCache(IFluidStorageGrid.class, FluidStorageGrid.class);
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(ServerEventHandler.INSTANCE);
    }
}
