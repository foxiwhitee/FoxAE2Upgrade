package foxiwhitee.FoxAE2Upgrade.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxAE2Upgrade-Content")
public class ContentConfig {
    @ConfigValue(category = "Content", desc = "Enable AutoCrystallizer")
    public static boolean enableAutoCrystallizer = true;

    @ConfigValue(category = "Content", desc = "Enable AutoPress")
    public static boolean enableAutoPress = true;

    @ConfigValue(category = "Content", desc = "Enable ME Server")
    public static boolean enableMEServer = true;

    @ConfigValue(category = "Content", desc = "Enable Advanced Driver")
    public static boolean enableAdvancedDriver = true;

    @ConfigValue(category = "Content", desc = "Enable Cobblestone Duper")
    public static boolean enableCobblestoneDuper = true;

    @ConfigValue(category = "Content", desc = "Enable New Molecular Assemblers")
    public static boolean enableMolecularAssemblers = true;

    @ConfigValue(category = "Content", desc = "Enable New Cables")
    public static boolean enableCables = true;

}
