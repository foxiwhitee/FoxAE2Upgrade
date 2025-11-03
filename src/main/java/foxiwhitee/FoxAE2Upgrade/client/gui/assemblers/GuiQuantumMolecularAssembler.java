package foxiwhitee.FoxAE2Upgrade.client.gui.assemblers;

import appeng.client.gui.AEBaseGui;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiQuantumMolecularAssembler extends AEBaseGui {
    public GuiQuantumMolecularAssembler(InventoryPlayer ip, TileQuantumMolecularAssembler te) {
        super(new ContainerQuantumMolecularAssembler(ip, te));
        this.xSize = 210;
        this.ySize = 343;
    }

    public void drawFG(int offsetX, int offsetY, int mouseX, int mouseY) {}

    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        bindTexture(FoxCore.MODID.toLowerCase(), "gui/gui_quantumMolecularAssembler.png");
        UtilGui.drawTexture(offsetX - 17, offsetY + 7, 0, 0, this.xSize, this.ySize, this.xSize, this.ySize);
    }
}
