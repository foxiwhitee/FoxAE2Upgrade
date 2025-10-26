package foxiwhitee.FoxAE2Upgrade.mixins;

import appeng.api.util.WorldCoord;
import appeng.me.cluster.IAECluster;
import appeng.me.cluster.implementations.CraftingCPUCalculator;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.crafting.TileCraftingTile;
import com.llamalad7.mixinextras.sugar.Local;
import foxiwhitee.FoxAE2Upgrade.api.crafting.ICraftingCPUClusterAccessor;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = CraftingCPUCalculator.class, remap = false)
public abstract class CraftingCPUCalculatorMixin {

    @Inject(method = "updateTiles",
        at = @At(value = "INVOKE",
            target = "Lappeng/tile/crafting/TileCraftingTile;updateStatus(Lappeng/me/cluster/implementations/CraftingCPUCluster;)V"),
        locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void beforeUpdateStatus(IAECluster cl, World w, WorldCoord min, WorldCoord max,
                                    CallbackInfo ci,
                                    @Local(name = "c") CraftingCPUCluster c,
                                    @Local(name = "x") int x,
                                    @Local(name = "y") int y,
                                    @Local(name = "z") int z,
                                    @Local(name = "te") TileCraftingTile te) {

        if (te instanceof TileMEServer) {
            te.updateStatus(c);
            ((ICraftingCPUClusterAccessor) (Object) c).addTile$FoxAE2Upgrade(te);
            ci.cancel();
        }
    }
}
