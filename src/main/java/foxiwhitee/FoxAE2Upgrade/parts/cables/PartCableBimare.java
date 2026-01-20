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

public class PartCableBimare extends PartBaseSmartCable {
    public PartCableBimare(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.CableBimare_Red.getIcon();
            case Blue -> CableBusTextures.CableBimare_Blue.getIcon();
            case Cyan -> CableBusTextures.CableBimare_Cyan.getIcon();
            case Gray -> CableBusTextures.CableBimare_Grey.getIcon();
            case Lime -> CableBusTextures.CableBimare_Lime.getIcon();
            case Pink -> CableBusTextures.CableBimare_Pink.getIcon();
            case Black -> CableBusTextures.CableBimare_Black.getIcon();
            case Brown -> CableBusTextures.CableBimare_Brown.getIcon();
            case Green -> CableBusTextures.CableBimare_Green.getIcon();
            case White -> CableBusTextures.CableBimare_White.getIcon();
            case Orange -> CableBusTextures.CableBimare_Orange.getIcon();
            case Purple -> CableBusTextures.CableBimare_Purple.getIcon();
            case Yellow -> CableBusTextures.CableBimare_Yellow.getIcon();
            case Magenta -> CableBusTextures.CableBimare_Magenta.getIcon();
            case LightBlue -> CableBusTextures.CableBimare_LightBlue.getIcon();
            case LightGray -> CableBusTextures.CableBimare_LightGrey.getIcon();
            default -> CableBusTextures.CableBimare.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.itemParts).createPart(EnumParts.BIMARE_SMART_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableBimareMaxChannelSize;
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
