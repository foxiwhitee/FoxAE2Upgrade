package foxiwhitee.FoxAE2Upgrade.client.gui;

import appeng.client.gui.AEBaseGui;
import appeng.core.localization.GuiText;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.PacketSwitchGuis;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.container.ContainerAdvancedDrive;
import foxiwhitee.FoxLib.api.button.ITooltipButton;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.gui.GuiButton;

public class GuiAdvancedDrive extends AEBaseGui {
    private GuiButton priority;

    public GuiAdvancedDrive(ContainerAdvancedDrive container) {
        super(container);
        this.xSize = 220;
        this.ySize = 238;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for(Object c : this.buttonList) {
            if (c instanceof ITooltipButton tooltip) {
                int x = tooltip.xPos();
                int y = tooltip.yPos();
                if (x < mouseX && x + tooltip.getWidth() > mouseX && tooltip.isVisible() && y < mouseY && y + tooltip.getHeight() > mouseY) {
                    if (y < 15) {
                        y = 15;
                    }

                    String msg = tooltip.getMessage();
                    if (msg != null) {
                        this.drawTooltip(x + 11, y + 4, msg);
                    }
                }
            }
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton == this.priority) {
            NetworkHandler.instance.sendToServer(new PacketSwitchGuis(GuiBridge.GUI_PRIORITY));
        }
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(this.priority = new NoTextureButton(0, this.guiLeft + 206, this.guiTop + 3, 8, 8, GuiText.Priority.getUnlocalized()));
    }

    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
    }

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        bindTexture(FoxCore.MODID, "gui/guiAdvancedDriver.png");
        UtilGui.drawTexture(offsetX, offsetY, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
    }
}
