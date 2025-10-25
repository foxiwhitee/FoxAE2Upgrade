package foxiwhitee.FoxAE2Upgrade.mixins;

import appeng.api.config.RedstoneMode;
import appeng.api.implementations.IUpgradeableHost;
import appeng.container.implementations.ContainerUpgradeable;
import foxiwhitee.FoxAE2Upgrade.api.channels.IContainerUpgradeableAccessor;
import net.minecraft.entity.player.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ContainerUpgradeable.class)
public abstract class ContainerUpgradeableMixin implements IContainerUpgradeableAccessor {

    @Shadow(remap = false)
    abstract void setRedStoneMode(RedstoneMode rsMode);

    @Shadow(remap = false)
    public abstract IUpgradeableHost getUpgradeable();

    public void callSetRedStoneMode(RedstoneMode mode) {
        setRedStoneMode(mode);
    }

    public IUpgradeableHost callGetUpgradeable() {
        return getUpgradeable();
    }
}
