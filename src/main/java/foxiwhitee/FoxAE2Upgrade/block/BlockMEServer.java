package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;

public class BlockMEServer extends BlockApplied {
    public BlockMEServer(String name) {
        super(name, TileMEServer.class);
    }

    @Override
    public String getFolder() {
        return "meServer/";
    }

    @Override
    protected boolean hasUpDownRotate() {
        return true;
    }
}
