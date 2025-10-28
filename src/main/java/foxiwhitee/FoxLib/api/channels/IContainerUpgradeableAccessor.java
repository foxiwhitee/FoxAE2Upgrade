package foxiwhitee.FoxLib.api.channels;

import appeng.api.config.RedstoneMode;
import appeng.api.implementations.IUpgradeableHost;

public interface IContainerUpgradeableAccessor {
    void callSetRedStoneMode(RedstoneMode paramRedstoneMode);

    IUpgradeableHost callGetUpgradeable();
}
