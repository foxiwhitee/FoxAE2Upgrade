package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;

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
