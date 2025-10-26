package foxiwhitee.FoxAE2Upgrade.network.packets;

import appeng.api.config.Settings;
import appeng.api.util.IConfigManager;
import appeng.api.util.IConfigurableObject;
import appeng.client.gui.implementations.GuiCraftingCPU;
import appeng.container.AEBaseContainer;
import appeng.container.implementations.ContainerNetworkTool;
import appeng.container.implementations.ContainerSecurity;
import appeng.core.sync.network.INetworkInfo;
import appeng.helpers.IMouseWheelItem;
import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;
import foxiwhitee.FoxAE2Upgrade.network.BasePacket;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.io.*;

public class DefaultPacket extends BasePacket {
    private final String packetName;
    private final String packetValue;

    public DefaultPacket(ByteBuf buffer) throws IOException {
        super(buffer);
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(
                buffer.array(), buffer.readerIndex(), buffer.readableBytes()));
        this.packetName = input.readUTF();
        this.packetValue = input.readUTF();
    }

    public DefaultPacket(String name, String value) throws IOException {
        super();
        this.packetName = name;
        this.packetValue = value;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(output);
        outputStream.writeUTF(name);
        outputStream.writeUTF(value);
        data.writeBytes(output.toByteArray());
        setPacketData(data);
    }

    @Override
    public void handleServerSide(INetworkInfo network, BasePacket packet, EntityPlayer player) {
        Container container = player.openContainer;
        if (container instanceof ContainerMEServer && packetName.equals("initializeClusters")) {
            ((TileMEServer)((ContainerMEServer) container).getTileEntity()).initializeClusters();
        }

    }

    @Override
    public void handleClientSide(INetworkInfo network, BasePacket packet, EntityPlayer player) {

    }
}
