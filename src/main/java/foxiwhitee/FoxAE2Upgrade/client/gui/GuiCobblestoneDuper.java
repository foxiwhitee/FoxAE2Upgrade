package foxiwhitee.FoxAE2Upgrade.client.gui;

import appeng.client.gui.AEBaseGui;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.gui.GuiButton;

import java.util.ArrayList;
import java.util.List;

public class GuiCobblestoneDuper extends AEBaseGui {
    private ContainerCobblestoneDuper container;

    public GuiCobblestoneDuper(ContainerCobblestoneDuper container) {
        super(container);
        this.container = container;
        this.ySize = 199;
        this.xSize = 210;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
    }

    public void initGui() {
        super.initGui();
    }

    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {
        int h = (this.width - this.xSize / 2) / 2;
        int k = (this.height - this.ySize / 2) / 2;
        this.bindTexture(FoxCore.MODID.toLowerCase(), this.getBackground());
        TileCobblestoneDuper tile = (TileCobblestoneDuper) container.getTileEntity();
        if (tile.getTick() > 0) {
            double l = ProductivityUtil.gauge(142, tile.getTick(), 20 * FoxConfig.cobblestoneDuperSecondsNeed);
            UtilGui.drawTexture(offsetX - 341, offsetY - 78, 0, 243, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }
        if (tile.getProductivity() > 0) {
            double l = ProductivityUtil.gaugeProductivityProgressBar(tile.getTick(), tile.getProductivity(), tile.getProgressProductivity(), 134, 20 * FoxConfig.cobblestoneDuperSecondsNeed);
            if (l > 134) {
                l = l % 134;
            }
            UtilGui.drawTexture(offsetX - 337, offsetY - 67, 0, 250, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }

    }

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        this.bindTexture(FoxCore.MODID.toLowerCase(), this.getBackground());
        drawTexturedModalRect(offsetX, offsetY, 23, 28, this.xSize, this.ySize);
    }

    protected String getBackground() {
        return "gui/gui_cobblestone_duper.png";
    }

}
