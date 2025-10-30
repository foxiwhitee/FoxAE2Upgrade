package foxiwhitee.FoxAE2Upgrade.proxy;

import appeng.client.texture.CableBusTextures;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.client.ClientEventHandler;
import foxiwhitee.FoxAE2Upgrade.client.render.assemblers.RenderAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.client.render.assemblers.RenderQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.client.render.assemblers.RenderUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxLib.utils.helpers.RegisterUtils;
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

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

        if (ContentConfig.enableMolecularAssemblers) {
            RegisterUtils.registerItemRenderer(Item.getItemFromBlock(ModBlocks.ADVANCED_MOLECULAR_ASSEMBLER), new RenderAdvancedMolecularAssembler());
            RegisterUtils.registerItemRenderer(Item.getItemFromBlock(ModBlocks.ULTIMATE_MOLECULAR_ASSEMBLER), new RenderUltimateMolecularAssembler());
            RegisterUtils.registerItemRenderer(Item.getItemFromBlock(ModBlocks.QUANTUM_MOLECULAR_ASSEMBLER), new RenderQuantumMolecularAssembler());
            RegisterUtils.registerTileRenderer(TileAdvancedMolecularAssembler.class, new RenderAdvancedMolecularAssembler());
            RegisterUtils.registerTileRenderer(TileUltimateMolecularAssembler.class, new RenderUltimateMolecularAssembler());
            RegisterUtils.registerTileRenderer(TileQuantumMolecularAssembler.class, new RenderQuantumMolecularAssembler());
        }

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

}
