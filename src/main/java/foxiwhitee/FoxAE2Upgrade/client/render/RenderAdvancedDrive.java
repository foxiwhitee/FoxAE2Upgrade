package foxiwhitee.FoxAE2Upgrade.client.render;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxLib.client.render.TileEntitySpecialRendererObjWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class RenderAdvancedDrive extends TileEntitySpecialRendererObjWrapper<TileAdvancedDrive> implements IItemRenderer {
    private final static Map<String, ResourceLocation> texturesCells = new HashMap<>();
    private final IModelCustom model;

    static {
        texturesCells.put("cellItem", new ResourceLocation(FoxCore.MODID, "textures/blocks/drive/cellItem.png"));
        texturesCells.put("cellFluid", new ResourceLocation(FoxCore.MODID, "textures/blocks/drive/cellFluid.png"));
        texturesCells.put("cellEssence", new ResourceLocation(FoxCore.MODID, "textures/blocks/drive/cellEssence.png"));

        for (int i = 0; i < 5; i++) {
            texturesCells.put("cellStatus" + i, new ResourceLocation(FoxCore.MODID, "textures/blocks/drive/cellStatus" + i + ".png"));
        }
    }

    public RenderAdvancedDrive() {
        super(TileAdvancedDrive.class, new ResourceLocation(FoxCore.MODID, "models/advancedDrive.obj"), new ResourceLocation(FoxCore.MODID, "textures/blocks/drive/advancedDrive.png"));
        this.model = AdvancedModelLoader.loadModel(new ResourceLocation(FoxCore.MODID, "models/advancedDrive.obj"));
        this.createList("base");
        for (int i = 1; i <= 30; i++) {
            this.createList("cell" + i);
        }
    }

    public void renderAt(TileAdvancedDrive tileEntity, double x, double y, double z, double f) {
        ForgeDirection forward = tileEntity.getForward();
        ForgeDirection up = tileEntity.getUp();
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        switch (forward) {
            case UP: GL11.glRotatef(90, 1, 0, 0); break;
            case DOWN: GL11.glRotatef(-90, 1, 0, 0); break;
            case NORTH: GL11.glRotatef(180, 0, 1, 0); break;
            case SOUTH: GL11.glRotatef(0, 0, 1, 0); break;
            case WEST: GL11.glRotatef(270, 0, 1, 0); break;
            case EAST: GL11.glRotatef(90, 0, 1, 0); break;
            default: break;
        }
        applyRoll(forward, up);
        GL11.glTranslated(0, -0.5, 0);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.bindTexture();
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderPart("base");
        GL11.glPopMatrix();
        for (int i = 1; i <= 30; i++) {
            int type = tileEntity.getCellType(i - 1);
            if (type >= 0) {
                int state = tileEntity.getCellStatus(i - 1);
                this.bindTexture(texturesCells.get("cell" + switch (type) {
                    case 1 -> "Fluid";
                    case 2 -> "Essence";
                    default -> "Item";
                }));
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.renderPart("cell" + i);
                this.bindTexture(texturesCells.get("cellStatus" + state));
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.renderPart("cell" + i);
            }
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void applyRoll(ForgeDirection forward, ForgeDirection up) {
        switch (forward) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                switch (up) {
                    case UP: return;
                    case DOWN: GL11.glRotatef(180, 0, 0, 1); break;
                    case WEST:
                    case NORTH: GL11.glRotatef(90, 0, 0, 1); break;
                    case EAST:
                    case SOUTH: GL11.glRotatef(-90, 0, 0, 1); break;
                }
                break;
            case UP:
                switch (up) {
                    case NORTH: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(180, 0, 0, 1);
                        break;
                    }
                    case SOUTH: {
                        GL11.glRotatef(180, 0, 1, 0);
                        break;
                    }
                    case WEST: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(-90, 0, 0, 1);
                        break;
                    }
                    case EAST: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(90, 0, 0, 1);
                        break;
                    }
                }
                break;
            case DOWN:
                switch (up) {
                    case NORTH: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(180, 0, 0, 1);
                        break;
                    }
                    case SOUTH: {
                        GL11.glRotatef(180, 0, 1, 0);
                        break;
                    }
                    case WEST: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(90, 0, 0, 1);
                        break;
                    }
                    case EAST: {
                        GL11.glRotatef(180, 0, 1, 0);
                        GL11.glRotatef(-90, 0, 0, 1);
                        break;
                    }
                }
                break;
        }
    }

    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return true;
    }

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslated(0, -0.5, 0);
        GL11.glScaled(1, 1, 1);
        switch (type) {
            case ENTITY:
                GL11.glScaled(1.35, 1.35, 1.35);
                GL11.glTranslated(0, 0, 0);
                break;
            case EQUIPPED, EQUIPPED_FIRST_PERSON:
                GL11.glScaled(1, 1, 1);
                GL11.glTranslated(0.5, 0.5, 0.5);
                break;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(this.getTexture());
        this.model.renderPart("base");
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
}
