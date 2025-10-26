package foxiwhitee.FoxLib.mixins.ae2;

import appeng.api.networking.GridNotification;
import appeng.me.helpers.AENetworkProxy;
import foxiwhitee.FoxAE2Upgrade.parts.cables.PartBaseCable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AENetworkProxy.class, remap = false)
public abstract class AENetworkProxyMixin {

    @Inject(method = "onGridNotification", at = @At("HEAD"))
    private void injected(GridNotification notification, CallbackInfo ci) {
        if (((AENetworkProxy) (Object) this).getMachine() instanceof PartBaseCable) {
            ((PartBaseCable)((AENetworkProxy) (Object) this).getMachine()).markForUpdate();
        }
    }
}
