package foxiwhitee.FoxAE2Upgrade.client.gui.assemblers;

import foxiwhitee.FoxAE2Upgrade.client.gui.AppengGui;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerQuantumMolecularAssembler;

public class GuiQuantumMolecularAssembler extends AppengGui {
    public GuiQuantumMolecularAssembler(ContainerQuantumMolecularAssembler container) {
        super(container, 210, 343);
        this.xSize = 210;
        this.ySize = 343;
    }

    @Override
    protected String getBackground() {
        return "gui/guiQuantumMolecularAssembler.png";
    }
}
