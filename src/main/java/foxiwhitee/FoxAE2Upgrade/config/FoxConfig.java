package foxiwhitee.FoxAE2Upgrade.config;

@Config(folder = "Fox-Mods", name = "FoxAE2Upgrade")
public class FoxConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enable_tooltips = true;

    @ConfigValue(desc = "Changes the maximum size of the ME controller")
    public static int controllerMaxSize = 8;

    @ConfigValue(desc = "Changes", category = "ae2stuff")
    public static int maxWirelessChannels = 1024;

    @ConfigValue(category = "MolecularAssemblers", desc = "Number of items that the molecular assembler crafting per tick")
    public static long basic_molecular_assembler_speed = 200;

    @ConfigValue(category = "MolecularAssemblers", desc = "Number of items that the hybrid molecular assembler crafting per tick")
    public static long hybrid_molecular_assembler_speed = 25_000;

    @ConfigValue(category = "MolecularAssemblers", desc = "Number of items that the ultimate molecular assembler crafting per tick")
    public static long ultimate_molecular_assembler_speed = 25_000_000;


    @ConfigValue(category = "MolecularAssemblers", desc = "Amount of energy AE2 per tick (ae/t) consumed by the molecular assembler")
    public static int basic_molecular_assembler_power = 10;

    @ConfigValue(category = "MolecularAssemblers", desc = "Amount of energy AE2 per tick (ae/t) consumed by the hybrid molecular assembler")
    public static int hybrid_molecular_assembler_power = 50;

    @ConfigValue(category = "MolecularAssemblers", desc = "Amount of energy AE2 per tick (ae/t) consumed by the ultimate molecular assembler")
    public static int ultimate_molecular_assembler_power = 100;


    @ConfigValue(category = "AutomatedBlocks", desc = "Number of items that the block crafting per tick")
    public static long speedAutoCrystallizer = 10_000;

    @ConfigValue(category = "AutomatedBlocks", desc = "Number of items that the block crafting per tick")
    public static long speedAutoPress = 1_000;


    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableAliteMaxChannelSize = 64;

    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableBimareMaxChannelSize = 128;

    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableDefitMaxChannelSize = 256;

    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableEfrimMaxChannelSize = 512;

    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableNurMaxChannelSize = 1024;

    @ConfigValue(desc = "The maximum number of channels this cable can transmit", category = "Cables")
    public static int cableXaurMaxChannelSize = 2048;

}
