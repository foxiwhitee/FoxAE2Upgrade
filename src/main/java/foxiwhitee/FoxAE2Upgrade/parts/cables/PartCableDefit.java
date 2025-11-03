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

public class PartCableDefit extends PartBaseSmartCable {
    public PartCableDefit(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.CableDefit_Red.getIcon();
            case Blue -> CableBusTextures.CableDefit_Blue.getIcon();
            case Cyan -> CableBusTextures.CableDefit_Cyan.getIcon();
            case Gray -> CableBusTextures.CableDefit_Grey.getIcon();
            case Lime -> CableBusTextures.CableDefit_Lime.getIcon();
            case Pink -> CableBusTextures.CableDefit_Pink.getIcon();
            case Black -> CableBusTextures.CableDefit_Black.getIcon();
            case Brown -> CableBusTextures.CableDefit_Brown.getIcon();
            case Green -> CableBusTextures.CableDefit_Green.getIcon();
            case White -> CableBusTextures.CableDefit_White.getIcon();
            case Orange -> CableBusTextures.CableDefit_Orange.getIcon();
            case Purple -> CableBusTextures.CableDefit_Purple.getIcon();
            case Yellow -> CableBusTextures.CableDefit_Yellow.getIcon();
            case Magenta -> CableBusTextures.CableDefit_Magenta.getIcon();
            case LightBlue -> CableBusTextures.CableDefit_LightBlue.getIcon();
            case LightGray -> CableBusTextures.CableDefit_LightGrey.getIcon();
            default -> CableBusTextures.CableDefit.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.ITEM_PARTS).createPart(EnumParts.DEFIT_SMART_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableDefitMaxChannelSize;
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
