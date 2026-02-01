package foxiwhitee.FoxAE2Upgrade.client.gui.elements;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class LongTextField extends GuiTextField {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;

    public LongTextField(FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height);
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean isHovered = this.isMouseIn(mouseX, mouseY);
        this.setFocused(isHovered);
    }

    public boolean isMouseIn(int mouseX, int mouseY) {
        return mouseX >= this.xPos && mouseX < this.xPos + this.width &&
            mouseY >= this.yPos && mouseY < this.yPos + this.height;
    }

    @Override
    public void writeText(String text) {
        text = text.replaceAll(" ", "");
        String previousText = this.getText();
        super.writeText(text);
        text = this.getText();
        if (text.isEmpty()) {
            text = "0";
        }
        try {
            Long.parseLong(text);
            if (text.length() > 1) {
                String newText = text;
                while (newText.startsWith("0") && newText.length() > 1) {
                    newText = newText.substring(1);
                }
                this.setText(newText);
            }
        } catch (NumberFormatException e) {
            this.setText(previousText);
        }
    }

    @Override
    public boolean textboxKeyTyped(char p_146176_1_, int p_146176_2_) {
        boolean result = super.textboxKeyTyped(p_146176_1_, p_146176_2_);

        if (this.getText().isEmpty()) {
            this.setText("0");
        }

        return result;
    }

    @Override
    public void setText(String text) {
        if (text.trim().isEmpty()) {
            super.setText("0");
        }
        super.setText(text);
    }
}
