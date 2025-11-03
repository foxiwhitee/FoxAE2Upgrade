package foxiwhitee.FoxAE2Upgrade.parts.cables;

import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.util.AEColor;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.client.render.CableBusTextures;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.items.ItemParts;
import foxiwhitee.FoxAE2Upgrade.parts.EnumParts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PartCableEfrim extends PartBaseSmartCable {
    public PartCableEfrim(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.CableEfrim_Red.getIcon();
            case Blue -> CableBusTextures.CableEfrim_Blue.getIcon();
            case Cyan -> CableBusTextures.CableEfrim_Cyan.getIcon();
            case Gray -> CableBusTextures.CableEfrim_Grey.getIcon();
            case Lime -> CableBusTextures.CableEfrim_Lime.getIcon();
            case Pink -> CableBusTextures.CableEfrim_Pink.getIcon();
            case Black -> CableBusTextures.CableEfrim_Black.getIcon();
            case Brown -> CableBusTextures.CableEfrim_Brown.getIcon();
            case Green -> CableBusTextures.CableEfrim_Green.getIcon();
            case White -> CableBusTextures.CableEfrim_White.getIcon();
            case Orange -> CableBusTextures.CableEfrim_Orange.getIcon();
            case Purple -> CableBusTextures.CableEfrim_Purple.getIcon();
            case Yellow -> CableBusTextures.CableEfrim_Yellow.getIcon();
            case Magenta -> CableBusTextures.CableEfrim_Magenta.getIcon();
            case LightBlue -> CableBusTextures.CableEfrim_LightBlue.getIcon();
            case LightGray -> CableBusTextures.CableEfrim_LightGrey.getIcon();
            default -> CableBusTextures.CableEfrim.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.ITEM_PARTS).createPart(EnumParts.EFRIM_SMART_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableEfrimMaxChannelSize;
    }

    @MENetworkEventSubscribe
    public void channelUpdated(MENetworkChannelsChanged c) {
        getHost().markForUpdate();
    }

    @MENetworkEventSubscribe
    public void powerRender(MENetworkPowerStatusChange c) {
        getHost().markForUpdate();
    }

}
