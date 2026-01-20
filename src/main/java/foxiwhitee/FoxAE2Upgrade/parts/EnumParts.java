package foxiwhitee.FoxAE2Upgrade.parts;

import appeng.api.parts.IPart;
import appeng.core.features.AEFeature;
import appeng.integration.IntegrationType;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.parts.cables.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum EnumParts {
    InvalidType(-1, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), null, false, ""),
    ALITE_SMART_CABLE(0, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartCableAlite.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableAliteMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    },
    BIMARE_SMART_CABLE(17, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartCableBimare.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableBimareMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    },
    DEFIT_SMART_CABLE(34, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartCableDefit.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableDefitMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    },
    EFRIM_SMART_CABLE(51, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartCableEfrim.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableEfrimMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    },
    NUR_DENSE_CABLE(68, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartDenseCableNur.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableNurMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    },
    XAUR_DENSE_CABLE(85, EnumSet.of(AEFeature.Core), EnumSet.noneOf(IntegrationType.class), PartDenseCableXaur.class, ContentConfig.enableCables, StatCollector.translateToLocalFormatted("tooltip.cables.channels", FoxConfig.cableXaurMaxChannelSize)) {
        public boolean isCable() {
            return true;
        }
    };

    private final int baseDamage;

    private final Set<AEFeature> features;

    private final Set<IntegrationType> integrations;

    private final Class<? extends IPart> myPart;

    private Constructor<? extends IPart> constructor;

    private final boolean register;

    private final String description;

    EnumParts(int baseMetaValue, Set<AEFeature> features, Set<IntegrationType> integrations, Class<? extends IPart> c, boolean register, String description) {
        this.features = Collections.unmodifiableSet(features);
        this.integrations = Collections.unmodifiableSet(integrations);
        this.myPart = c;
        this.baseDamage = baseMetaValue;
        this.register = register;
        this.description = description;
    }

    public boolean isCable() {
        return false;
    }

    public Set<AEFeature> getFeature() {
        return this.features;
    }

    public Set<IntegrationType> getIntegrations() {
        return this.integrations;
    }

    public Class<? extends IPart> getPart() {
        return this.myPart;
    }

    public Constructor<? extends IPart> getConstructor() {
        return this.constructor;
    }

    public void setConstructor(Constructor<? extends IPart> constructor) {
        this.constructor = constructor;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public ItemStack getStack() {
        return new ItemStack(ModItems.itemParts, 1, getBaseDamage());
    }

    public boolean isRegister() {
        return register;
    }

    public String getDescription() {
        return description;
    }
}
