package foxiwhitee.FoxAE2Upgrade.client.gui;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.utils.ProductivityUtil;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

public class GuiCobblestoneDuper extends AppengGui {
    private final TileCobblestoneDuper tile;

    public GuiCobblestoneDuper(ContainerCobblestoneDuper container) {
        super(container, 199,210);
        tile = (TileCobblestoneDuper) container.getTileEntity();
    }

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        if (tile.getTick() > 0) {
            double l = ProductivityUtil.gauge(142, tile.getTick(), 20 * FoxConfig.cobblestoneDuperSecondsNeed);
            UtilGui.drawTexture(offsetX + 57 - 23, offsetY + 103 - 28, 0, 243, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }
        if (tile.getProductivity() > 0) {
            double l = ProductivityUtil.gaugeProductivityProgressBar(tile.getTick(), tile.getProductivity(), tile.getProgressProductivity(), 134, 20 * FoxConfig.cobblestoneDuperSecondsNeed);
            if (l > 134) {
                l = l % 134;
            }
            UtilGui.drawTexture(offsetX + 64 - 26, offsetY + 114 - 28, 0, 250, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6, 256, 256);
        }
    }

    protected String getBackground() {
        return "gui/guiCobblestoneDuper.png";
    }

}
