package foxiwhitee.FoxAE2Upgrade.client.gui;

import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.client.gui.elements.LongTextField;
import foxiwhitee.FoxAE2Upgrade.container.ContainerLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.network.packets.C2SSetValuesInLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;
import foxiwhitee.FoxLib.client.gui.buttons.NoTextureButton;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.utils.helpers.UtilGui;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

public class GuiLevelMaintainer extends AppengGui {
    private final TileLevelMaintainer tile;
    private final LongTextField[] fields = new LongTextField[12];
    private final String[] lastValues = new String[12];

    public GuiLevelMaintainer(ContainerLevelMaintainer container) {
        super(container, 220, 238);
        this.tile = (TileLevelMaintainer) container.getTileEntity();
    }

    @Override
    public void drawBG(int offsetX, int offsetY, int mouseX, int mouseY) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY);
        for (var field : fields) {
            field.drawTextBox();
        }
        this.bindTexture(FoxCore.MODID, this.getBackground());
        for (int i = 0; i < 6; i++) {
            if (tile.getEnable()[i]) {
                UtilGui.drawTexture(offsetX + 176, offsetY + 22 + i * 20, 224, 10, 10, 10, 10, 10);
            }
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        for (int i = 0; i < 6; i++) {
            NoTextureButton button = new NoTextureButton(i, guiLeft + 176, guiTop + 22 + i * 20, 10, 10, new String[]{"tooltip.enable", "tooltip.disable"}, new String[0]);
            if (tile.getEnable()[i]) {
                button.setCurrentText(1);
            }
            this.buttonList.add(button);
        }

        Keyboard.enableRepeatEvents(true);
        for (int i = 0; i < fields.length; i++) {
            if (i < 6) {
                fields[i] = new LongTextField(this.fontRendererObj, guiLeft + 57, guiTop + 22 + (i * 20), 62, 10);
                fields[i].setText(String.valueOf(tile.getNeed()[i]));
            } else {
                fields[i] = new LongTextField(this.fontRendererObj, guiLeft + 124, guiTop + 22 + ((i - 6) * 20), 46, 10);
                fields[i].setText(String.valueOf(tile.getCraft()[i - 6]));
            }
            fields[i].setMaxStringLength(10);

            lastValues[i] = fields[i].getText();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button instanceof NoTextureButton) {
            NetworkManager.instance.sendToServer(new C2SSetValuesInLevelMaintainer(tile.xCoord, tile.yCoord, tile.zCoord, C2SSetValuesInLevelMaintainer.Mode.ENABLE, button.id));
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isFocused()) {
                fields[i].textboxKeyTyped(typedChar, keyCode);

                String currentText = fields[i].getText();
                if (!currentText.equals(lastValues[i])) {
                    try {
                        if (i < 6) {
                            NetworkManager.instance.sendToServer(new C2SSetValuesInLevelMaintainer(tile.xCoord, tile.yCoord, tile.zCoord, C2SSetValuesInLevelMaintainer.Mode.NEED, i, Long.parseLong(currentText)));
                        } else {
                            NetworkManager.instance.sendToServer(new C2SSetValuesInLevelMaintainer(tile.xCoord, tile.yCoord, tile.zCoord, C2SSetValuesInLevelMaintainer.Mode.CRAFT, i - 6, Long.parseLong(currentText)));
                        }
                        lastValues[i] = currentText;
                    } catch (NumberFormatException ignored) {

                    }
                }
                return;
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (var field : fields) {
            field.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected String getBackground() {
        return "gui/guiLevelMaintainer.png";
    }
}
