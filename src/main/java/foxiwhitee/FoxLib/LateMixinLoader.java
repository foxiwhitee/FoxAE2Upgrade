package foxiwhitee.FoxLib;

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
            "mixins.FoxLib_ae2.json",
            "mixins.FoxLib_ae2stuff.json"
        );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig) {
        if (mixinConfig.equals("mixins.FoxLib_ae2.json")) {
            return Loader.isModLoaded("appliedenergistics2");
        }
        if (mixinConfig.equals("mixins.FoxLib_ae2stuff.json")) {
            return Loader.isModLoaded("ae2stuff");
        }
        return true;
    }

}
