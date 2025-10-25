package foxiwhitee.FoxAE2Upgrade.parts.cables;

import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.util.AEColor;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.client.render.CableBusTextures;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.items.part.ItemParts;
import foxiwhitee.FoxAE2Upgrade.parts.EnumParts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class PartCableAlite extends PartBaseSmartCable {
    public PartCableAlite(ItemStack is) {
        super(is);
    }

    @Override
    public IIcon getTexture(AEColor c) {
        switch (c) {
            case Red: return CableBusTextures.CableAlite_Red.getIcon();
            case Blue: return CableBusTextures.CableAlite_Blue.getIcon();
            case Cyan: return CableBusTextures.CableAlite_Cyan.getIcon();
            case Gray: return CableBusTextures.CableAlite_Grey.getIcon();
            case Lime: return CableBusTextures.CableAlite_Lime.getIcon();
            case Pink: return CableBusTextures.CableAlite_Pink.getIcon();
            case Black: return CableBusTextures.CableAlite_Black.getIcon();
            case Brown: return CableBusTextures.CableAlite_Brown.getIcon();
            case Green: return CableBusTextures.CableAlite_Green.getIcon();
            case White: return CableBusTextures.CableAlite_White.getIcon();
            case Orange: return CableBusTextures.CableAlite_Orange.getIcon();
            case Purple: return CableBusTextures.CableAlite_Purple.getIcon();
            case Yellow: return CableBusTextures.CableAlite_Yellow.getIcon();
            case Magenta: return CableBusTextures.CableAlite_Magenta.getIcon();
            case LightBlue: return CableBusTextures.CableAlite_LightBlue.getIcon();
            case LightGray: return CableBusTextures.CableAlite_LightGrey.getIcon();
            default: return CableBusTextures.CableAlite.getIcon();
        }
    }

    @Override
    public ItemStack getPartItemStack(AEColor paramAEColor) {
        return ((ItemParts)ModItems.ITEM_PARTS).createPart(EnumParts.ALITE_SMART_CABLE, paramAEColor).stack(1);
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
