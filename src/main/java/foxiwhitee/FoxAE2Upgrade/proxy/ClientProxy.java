package foxiwhitee.FoxAE2Upgrade.proxy;

import appeng.client.texture.CableBusTextures;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.ClientEventHandler;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;

public class ClientProxy extends CommonProxy {
    private static final HashMap<String, Integer> hash = new HashMap<>();

    public static int[] displayList = new int[1];

    public static int getRenderBlocks(String model) {
        if (hash.containsKey(model))
            return ((Integer)hash.get(model)).intValue();
        int displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(displayList, 4864);
        AdvancedModelLoader.loadModel(new ResourceLocation(FoxCore.MODID.toLowerCase(), "models/blocks/" + model + ".obj")).renderAll();
        GL11.glEndList();
        hash.put(model, Integer.valueOf(displayList));
        return displayList;
    }

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
