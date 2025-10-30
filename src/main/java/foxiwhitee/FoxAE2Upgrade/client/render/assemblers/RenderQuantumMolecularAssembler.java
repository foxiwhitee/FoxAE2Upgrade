package foxiwhitee.FoxAE2Upgrade.client.render.assemblers;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderQuantumMolecularAssembler extends TileEntitySpecialRendererObjWrapper<TileQuantumMolecularAssembler> implements IItemRenderer {
    private final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxCore.MODID.toLowerCase(), "models/molecularAssembler.obj"));

    public RenderQuantumMolecularAssembler() {
        super(TileQuantumMolecularAssembler.class, "models/molecularAssembler.obj", "textures/blocks/assemblers/quantumMolecularAssembler.png");
        createList("all");
    }

    public void renderAt(TileQuantumMolecularAssembler tileEntity, double x, double y, double z, double f) {
        GL11.glPushMatrix();
        GL11.glEnable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(x, y, z);
        GL11.glPushMatrix();
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        bindTexture();
        GL11.glTranslated(0.5D, 0.0D, 0.5D);
        renderPart("all");
        GL11.glDisable(3008);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(0.0D, -0.5D, 0.0D);
        GL11.glScaled(1.0D, 1.0D, 1.0D);
        switch (type) {
            case ENTITY:
                GL11.glScaled(1.35D, 1.35D, 1.35D);
                GL11.glTranslated(0.0D, 0.0D, 0.0D);
                break;
            case EQUIPPED_FIRST_PERSON, EQUIPPED:
                GL11.glTranslated(0.5D, 0.5D, 0.5D);
                break;
        }
        (Minecraft.getMinecraft()).renderEngine.bindTexture(this.getTexture());
        this.model.renderAll();
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
