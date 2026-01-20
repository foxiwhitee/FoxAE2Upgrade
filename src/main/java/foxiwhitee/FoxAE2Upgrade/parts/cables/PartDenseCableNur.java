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

public class PartDenseCableNur extends PartBaseDenseCable {
    public PartDenseCableNur(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.DenseCableNur_Red.getIcon();
            case Blue -> CableBusTextures.DenseCableNur_Blue.getIcon();
            case Cyan -> CableBusTextures.DenseCableNur_Cyan.getIcon();
            case Gray -> CableBusTextures.DenseCableNur_Grey.getIcon();
            case Lime -> CableBusTextures.DenseCableNur_Lime.getIcon();
            case Pink -> CableBusTextures.DenseCableNur_Pink.getIcon();
            case Black -> CableBusTextures.DenseCableNur_Black.getIcon();
            case Brown -> CableBusTextures.DenseCableNur_Brown.getIcon();
            case Green -> CableBusTextures.DenseCableNur_Green.getIcon();
            case White -> CableBusTextures.DenseCableNur_White.getIcon();
            case Orange -> CableBusTextures.DenseCableNur_Orange.getIcon();
            case Purple -> CableBusTextures.DenseCableNur_Purple.getIcon();
            case Yellow -> CableBusTextures.DenseCableNur_Yellow.getIcon();
            case Magenta -> CableBusTextures.DenseCableNur_Magenta.getIcon();
            case LightBlue -> CableBusTextures.DenseCableNur_LightBlue.getIcon();
            case LightGray -> CableBusTextures.DenseCableNur_LightGrey.getIcon();
            default -> CableBusTextures.DenseCableNur.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.itemParts).createPart(EnumParts.NUR_DENSE_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableNurMaxChannelSize;
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
