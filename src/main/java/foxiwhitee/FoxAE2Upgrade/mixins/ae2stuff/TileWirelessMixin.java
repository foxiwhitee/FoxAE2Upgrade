package foxiwhitee.FoxAE2Upgrade.mixins.ae2stuff;

import foxiwhitee.FoxAE2Upgrade.api.channels.ICustomChannelCount;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.bdew.ae2stuff.machines.wireless.TileWireless;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = TileWireless.class, remap = false)
public abstract class TileWirelessMixin implements ICustomChannelCount {

    public int getMaxChannelSize() {
        return FoxConfig.maxWirelessChannels;
    }
}
