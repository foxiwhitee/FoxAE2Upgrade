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

public class PartCableAlite extends PartBaseSmartCable {
    public PartCableAlite(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.CableAlite_Red.getIcon();
            case Blue -> CableBusTextures.CableAlite_Blue.getIcon();
            case Cyan -> CableBusTextures.CableAlite_Cyan.getIcon();
            case Gray -> CableBusTextures.CableAlite_Grey.getIcon();
            case Lime -> CableBusTextures.CableAlite_Lime.getIcon();
            case Pink -> CableBusTextures.CableAlite_Pink.getIcon();
            case Black -> CableBusTextures.CableAlite_Black.getIcon();
            case Brown -> CableBusTextures.CableAlite_Brown.getIcon();
            case Green -> CableBusTextures.CableAlite_Green.getIcon();
            case White -> CableBusTextures.CableAlite_White.getIcon();
            case Orange -> CableBusTextures.CableAlite_Orange.getIcon();
            case Purple -> CableBusTextures.CableAlite_Purple.getIcon();
            case Yellow -> CableBusTextures.CableAlite_Yellow.getIcon();
            case Magenta -> CableBusTextures.CableAlite_Magenta.getIcon();
            case LightBlue -> CableBusTextures.CableAlite_LightBlue.getIcon();
            case LightGray -> CableBusTextures.CableAlite_LightGrey.getIcon();
            default -> CableBusTextures.CableAlite.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.ITEM_PARTS).createPart(EnumParts.ALITE_SMART_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableAliteMaxChannelSize;
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
