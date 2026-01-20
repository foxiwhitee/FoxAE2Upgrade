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

public class PartDenseCableXaur extends PartBaseDenseCable {
    public PartDenseCableXaur(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        return switch (c) {
            case Red -> CableBusTextures.DenseCableXaur_Red.getIcon();
            case Blue -> CableBusTextures.DenseCableXaur_Blue.getIcon();
            case Cyan -> CableBusTextures.DenseCableXaur_Cyan.getIcon();
            case Gray -> CableBusTextures.DenseCableXaur_Grey.getIcon();
            case Lime -> CableBusTextures.DenseCableXaur_Lime.getIcon();
            case Pink -> CableBusTextures.DenseCableXaur_Pink.getIcon();
            case Black -> CableBusTextures.DenseCableXaur_Black.getIcon();
            case Brown -> CableBusTextures.DenseCableXaur_Brown.getIcon();
            case Green -> CableBusTextures.DenseCableXaur_Green.getIcon();
            case White -> CableBusTextures.DenseCableXaur_White.getIcon();
            case Orange -> CableBusTextures.DenseCableXaur_Orange.getIcon();
            case Purple -> CableBusTextures.DenseCableXaur_Purple.getIcon();
            case Yellow -> CableBusTextures.DenseCableXaur_Yellow.getIcon();
            case Magenta -> CableBusTextures.DenseCableXaur_Magenta.getIcon();
            case LightBlue -> CableBusTextures.DenseCableXaur_LightBlue.getIcon();
            case LightGray -> CableBusTextures.DenseCableXaur_LightGrey.getIcon();
            default -> CableBusTextures.DenseCableXaur.getIcon();
        };
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts) ModItems.itemParts).createPart(EnumParts.XAUR_DENSE_CABLE, paramAEColor).stack(1);
    }

    @Override
    public int getMaxChannelSize() {
        return FoxConfig.cableXaurMaxChannelSize;
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
