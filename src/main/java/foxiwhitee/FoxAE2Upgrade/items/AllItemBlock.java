package foxiwhitee.FoxAE2Upgrade.items;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import net.minecraft.block.Block;

public class AllItemBlock extends ModItemBlock {
    public AllItemBlock(Block b) {
        super(b);
        this.enableTooltips = FoxConfig.enableTooltips;
    }
}
