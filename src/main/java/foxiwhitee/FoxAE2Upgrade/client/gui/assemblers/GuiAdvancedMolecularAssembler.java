package foxiwhitee.FoxAE2Upgrade.client.gui.assemblers;

import foxiwhitee.FoxAE2Upgrade.client.gui.AppengGui;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerAdvancedMolecularAssembler;

public class GuiAdvancedMolecularAssembler extends AppengGui {
    public GuiAdvancedMolecularAssembler(ContainerAdvancedMolecularAssembler container) {
        super(container, 210, 199);
    }

    @Override
    protected String getBackground() {
        return "gui/guiAdvancedMolecularAssembler.png";
    }
}
