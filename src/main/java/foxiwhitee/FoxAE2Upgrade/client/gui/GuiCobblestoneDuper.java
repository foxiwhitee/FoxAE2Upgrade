package foxiwhitee.FoxAE2Upgrade.client.gui;

import appeng.client.gui.AEBaseGui;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import net.minecraft.client.gui.GuiButton;

public class GuiCobblestoneDuper extends AEBaseGui {
    public GuiCobblestoneDuper(ContainerCobblestoneDuper container) {
        super(container);
        this.ySize = 199;
        this.xSize = 210;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
    }

    public void initGui() {
        super.initGui();
    }

    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {}

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FoxCore.MODID.toLowerCase(), this.getBackground());
        drawTexturedModalRect(offsetX, offsetY, 23, 28, this.xSize, this.ySize);
    }

    protected String getBackground() {
        return "gui/gui_cobblestone_duper.png";
    }

}
