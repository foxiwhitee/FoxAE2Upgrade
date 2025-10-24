package foxiwhitee.FoxAE2Upgrade.mixins;

import appeng.me.pathfinding.ControllerValidator;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ControllerValidator.class, remap = false)
public abstract class ControllerValidatorMixin {

    @ModifyConstant(method = "visitNode", constant = @Constant(intValue = 7))
    private int modifyMaxSize(int original) {
        return FoxConfig.controllerMaxSize;
    }
}
