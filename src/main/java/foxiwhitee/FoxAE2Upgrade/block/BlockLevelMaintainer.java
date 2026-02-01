package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.client.gui.GuiLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.container.ContainerLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileLevelMaintainer.class, container = ContainerLevelMaintainer.class, gui = GuiLevelMaintainer.class)
public class BlockLevelMaintainer extends BlockApplied {
    public BlockLevelMaintainer(String name) {
        super(name, TileLevelMaintainer.class);
    }

    @Override
    public String getFolder() {
        return "levelMaintainer/";
    }

    @Override
    protected boolean hasUpDownRotate() {
        return super.hasUpDownRotate();
    }
}
