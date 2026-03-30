package foxiwhitee.FoxAE2Upgrade.client.gui;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;

@SuppressWarnings("unused")
public class GuiCobblestoneDuper extends AppengGui {
    private final TileCobblestoneDuper tile;

    public GuiCobblestoneDuper(ContainerCobblestoneDuper container) {
        super(container, 210,199);
        tile = (TileCobblestoneDuper) container.getTileEntity();
    }

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        if (tile.getTick() > 0) {
            double l = UtilGui.gauge(142, tile.getTick(), 20 * FoxConfig.cobblestoneDuperSecondsNeed);
            UtilGui.drawTexture(offsetX + 57 - 23, offsetY + 82, 0, 243, (int) (l + 1.0D), 6, (int) (l + 1.0D), 6);
        }
    }

    protected String getBackground() {
        return "gui/guiCobblestoneDuper.png";
    }
}
