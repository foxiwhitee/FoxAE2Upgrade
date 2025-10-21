package foxiwhitee.FoxAE2Upgrade;

import cpw.mods.fml.common.Loader;
import io.github.tox1cozz.mixinbooterlegacy.ILateMixinLoader;
import io.github.tox1cozz.mixinbooterlegacy.LateMixin;

import java.util.Arrays;
import java.util.List;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Arrays.asList(
            "mixins.FoxAE2Upgrade_ae2.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if (mixinConfig.equals("mixins.FoxAE2Upgrade_ae2.json")) {
            return Loader.isModLoaded("appliedenergistics2");
        }
        return true;
    }

}
