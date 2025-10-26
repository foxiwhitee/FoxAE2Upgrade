package foxiwhitee.FoxLib.network.packets;

import appeng.core.sync.network.INetworkInfo;
import foxiwhitee.FoxLib.network.BasePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;

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

    }

    @Override
    public void handleClientSide(INetworkInfo network, BasePacket packet, EntityPlayer player) {

    }
}
