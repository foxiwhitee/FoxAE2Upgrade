package foxiwhitee.FoxAE2Upgrade.api.crafting;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;

public interface ICraftingCPUClusterAccessor {
    long getWaitingFor(ICraftingPatternDetails paramICraftingPatternDetails);

    void setWaitingFor(ICraftingPatternDetails paramICraftingPatternDetails, long paramLong);

    void callPostChange(IAEItemStack paramIAEItemStack, BaseActionSource paramBaseActionSource);

    IItemList<IAEStack<?>> getWaitingFor();

    void callPostCraftingStatusChange(IAEItemStack paramIAEItemStack);
}
