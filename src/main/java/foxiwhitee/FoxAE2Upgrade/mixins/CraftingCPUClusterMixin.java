package foxiwhitee.FoxAE2Upgrade.mixins;

import appeng.api.networking.crafting.ICraftingMedium;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import foxiwhitee.FoxAE2Upgrade.api.crafting.ICraftingCPUClusterAccessor;
import foxiwhitee.FoxAE2Upgrade.api.crafting.IPreCraftingMedium;
import net.minecraft.inventory.InventoryCrafting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Field;
import java.util.Map;

@Mixin(value = CraftingCPUCluster.class, remap = false)
public abstract class CraftingCPUClusterMixin implements ICraftingCPUClusterAccessor {

    @Shadow(remap = false)
    private IItemList<IAEStack<?>> waitingFor;

    @Shadow(remap = false)
    protected abstract void postChange(IAEStack<?> diff, BaseActionSource src);

    @Shadow(remap = false)
    protected abstract void postCraftingStatusChange(IAEStack<?> aeDiff);

    @Final
    @Shadow(remap = false)
    private Map<ICraftingPatternDetails, ?> tasks;

    @Redirect(method = "executeCrafting",
        at = @At(value = "INVOKE",
            target = "Lappeng/api/networking/crafting/ICraftingMedium;pushPattern(Lappeng/api/networking/crafting/ICraftingPatternDetails;Lnet/minecraft/inventory/InventoryCrafting;)Z"))
    private boolean redirectPushPattern(ICraftingMedium medium,
                                        ICraftingPatternDetails details,
                                        InventoryCrafting craftingInventory) {
        if (medium instanceof IPreCraftingMedium) {
            return ((IPreCraftingMedium) medium).pushPattern(details, craftingInventory, (CraftingCPUCluster) (Object) this);
        }
        return medium.pushPattern(details, craftingInventory);
    }

    @Override
    public long getWaitingFor(ICraftingPatternDetails pattern) {
        Object progress = tasks.get(pattern);
        if (progress == null) return 0L;
        try {
            Field valueField = progress.getClass().getDeclaredField("value");
            valueField.setAccessible(true);
            return valueField.getLong(progress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setWaitingFor(ICraftingPatternDetails pattern, long value) {
        Object progress = tasks.get(pattern);
        if (progress == null) return;
        try {
            Field valueField = progress.getClass().getDeclaredField("value");
            valueField.setAccessible(true);
            valueField.setLong(progress, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void callPostChange(IAEItemStack paramIAEItemStack, BaseActionSource paramBaseActionSource) {
        postChange(paramIAEItemStack, paramBaseActionSource);
    }

    @Override
    public IItemList<IAEStack<?>> getWaitingFor() {
        return waitingFor;
    }

    @Override
    public void callPostCraftingStatusChange(IAEItemStack paramIAEItemStack) {
        postCraftingStatusChange(paramIAEItemStack);
    }

}
