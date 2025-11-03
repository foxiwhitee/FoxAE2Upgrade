package foxiwhitee.FoxAE2Upgrade.client.gui;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;

public class GuiMEServer extends FoxBaseGui {
    public GuiMEServer(ContainerMEServer container) {
        super(container, 316, 274);
        setModID(FoxCore.MODID);
    }

    @Override
    protected String getBackground() {
        return "gui/gui_me_server.png";
    }
}
