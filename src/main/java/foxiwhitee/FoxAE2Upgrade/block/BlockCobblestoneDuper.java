package foxiwhitee.FoxAE2Upgrade.block;

import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;

public class BlockCobblestoneDuper extends BlockApplied {
    public BlockCobblestoneDuper(String name) {
        super(name, TileCobblestoneDuper.class);
        setHardness(0.5F);
    }

    @Override
    public String getFolder() {
        return "cobblestoneDuper/";
    }

}
