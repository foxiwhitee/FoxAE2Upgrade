package foxiwhitee.FoxAE2Upgrade.client.gui;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxLib.client.gui.FoxBaseGui;
import net.minecraft.inventory.Container;

public abstract class AppengGui extends FoxBaseGui {
    public AppengGui(Container container, int xSize, int ySize) {
        super(container, xSize, ySize);
        setModID(FoxCore.MODID);
    }
}
