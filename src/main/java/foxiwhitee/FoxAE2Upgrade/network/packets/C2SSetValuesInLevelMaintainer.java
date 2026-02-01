package foxiwhitee.FoxAE2Upgrade.network.packets;

import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;
import foxiwhitee.FoxLib.network.BasePacket;
import foxiwhitee.FoxLib.network.IInfoPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class C2SSetValuesInLevelMaintainer extends BasePacket {
    private final int xCoord, yCoord, zCoord, id;
    private final long value;
    private final Mode mode;

    public C2SSetValuesInLevelMaintainer(ByteBuf data) {
        super(data);
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
        id = data.readInt();
        value = data.readLong();
        mode = Mode.values()[data.readInt()];
    }

    public C2SSetValuesInLevelMaintainer(int xCoord, int yCoord, int zCoord, Mode mode, int id, long value) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.mode = mode;
        this.id = id;
        this.value = value;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        data.writeInt(id);
        data.writeLong(value);
        data.writeInt(mode.ordinal());
        setPacketData(data);
    }

    public C2SSetValuesInLevelMaintainer(int xCoord, int yCoord, int zCoord, Mode mode, int id) {
        this(xCoord, yCoord, zCoord, mode, id, 0);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            TileEntity te = player.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
            if (te instanceof TileLevelMaintainer tile) {
                switch (mode) {
                    case ENABLE -> tile.changeButtonMode(id);
                    case NEED -> tile.setNeed(id, value);
                    case CRAFT -> tile.setCraft(id, value);
                }
            }
        }
    }

    public enum Mode {
        ENABLE, NEED, CRAFT
    }
}
