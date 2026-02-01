package foxiwhitee.FoxAE2Upgrade.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.client.ClientEventHandler;
import foxiwhitee.FoxAE2Upgrade.client.render.RenderAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        if (ContentConfig.enableAdvancedDrive) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.advancedDrive), new RenderAdvancedDrive());
            ClientRegistry.bindTileEntitySpecialRenderer(TileAdvancedDrive.class, new RenderAdvancedDrive());
        }
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
