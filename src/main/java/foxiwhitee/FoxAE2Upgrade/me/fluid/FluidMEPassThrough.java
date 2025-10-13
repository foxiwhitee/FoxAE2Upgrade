package foxiwhitee.FoxAE2Upgrade.me.fluid;

import appeng.api.storage.IMEInventory;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.me.storage.MEPassThrough;

public class FluidMEPassThrough<T extends IAEStack<T>> extends MEPassThrough<T> {
    public FluidMEPassThrough(IMEInventory<T> i, StorageChannel channel) {
        super(i, channel);
    }

    public IMEInventory<T> getInternal() {
        return super.getInternal();
    }
}

