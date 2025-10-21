package foxiwhitee.FoxAE2Upgrade.helpers;

import appeng.me.helpers.AENetworkProxy;

public class MixinHooks {
    public static void onGridNotification(AENetworkProxy proxy) {
        //if (proxy.getMachine() instanceof PartBaseCable)
        //    ((PartBaseCable)proxy.getMachine()).markForUpdate();
    }
}
