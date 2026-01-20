package foxiwhitee.FoxAE2Upgrade.client.gui.assemblers;

import foxiwhitee.FoxAE2Upgrade.client.gui.AppengGui;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerUltimateMolecularAssembler;

public class GuiUltimateMolecularAssembler extends AppengGui {
    public GuiUltimateMolecularAssembler(ContainerUltimateMolecularAssembler container) {
        super(container, 210, 271);
    }

    @Override
    protected String getBackground() {
        return "gui/guiUltimateMolecularAssembler.png";
    }
}
