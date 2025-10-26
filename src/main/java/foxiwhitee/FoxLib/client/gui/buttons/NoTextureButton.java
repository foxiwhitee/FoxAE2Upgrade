package foxiwhitee.FoxLib.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public final class NoTextureButton extends GuiButton {
    public NoTextureButton(int id, int x, int y, int w, int h) {
        super(id, x, y, "");
        this.width = w;
        this.height = h;
    }

    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_) {}
}
