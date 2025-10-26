package foxiwhitee.FoxAE2Upgrade.client.gui;


import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;

public class GuiMEServer extends FoxBaseGui {
    public GuiMEServer(ContainerMEServer container) {
        super(container, 316, 274);
    }

    @Override
    protected String getBackground() {
        return "gui/gui_me_server.png";
    }
}
