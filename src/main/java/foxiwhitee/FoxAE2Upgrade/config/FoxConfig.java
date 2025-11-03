package foxiwhitee.FoxAE2Upgrade.config;

import foxiwhitee.FoxLib.config.Config;
import foxiwhitee.FoxLib.config.ConfigValue;

@Config(folder = "Fox-Mods", name = "FoxAE2Upgrade")
public class FoxConfig {
    @ConfigValue(desc = "Enable tooltips?")
    public static boolean enable_tooltips = true;

    @ConfigValue(desc = "Changes the maximum size of the ME controller")
    public static int controllerMaxSize = 8;

    @ConfigValue(desc = "Changes", category = "ae2stuff")
    public static int maxWirelessChannels = 1024;


    @ConfigValue(name = "itemsGenerated", category = "cobblestoneDuper", desc = "How many items does a Cobblestone Duper generate at a time?")
    public static long cobblestoneDuperItemsGenerated = 128;

    @ConfigValue(name = "secondsNeed", category = "cobblestoneDuper", desc = "Speed")
    public static int cobblestoneDuperSecondsNeed = 2;


    @ConfigValue(category = "MolecularAssemblers.Speed", desc = "Number of items that the Advanced Molecular Assembler crafting per tick")
    public static long advanced_molecular_assembler_speed = 200;

    @ConfigValue(category = "MolecularAssemblers.Speed", desc = "Number of items that the Ultimate Molecular Assembler crafting per tick")
    public static long ultimate_molecular_assembler_speed = 25_000;

    @ConfigValue(category = "MolecularAssemblers.Speed", desc = "Number of items that the Quantum Molecular Assembler crafting per tick")
    public static long quantum_molecular_assembler_speed = 25_000_000;


    @ConfigValue(category = "MolecularAssemblers.Power", desc = "Amount of energy AE2 per tick (ae/t) consumed by the Advanced Molecular Assembler")
    public static int advanced_molecular_assembler_power = 10;

    @ConfigValue(category = "MolecularAssemblers.Power", desc = "Amount of energy AE2 per tick (ae/t) consumed by the Ultimate Molecular Assembler")
    public static int ultimate_molecular_assembler_power = 50;

    @ConfigValue(category = "MolecularAssemblers.Power", desc = "Amount of energy AE2 per tick (ae/t) consumed by the Quantum Molecular Assembler")
    public static int quantum_molecular_assembler_power = 100;


    @ConfigValue(category = "MolecularAssemblers.Productivity", desc = "Ultimate Molecular Assembler productivity")
    public static int ultimate_molecular_assembler_productivity = 10;

    @ConfigValue(category = "MolecularAssemblers.Productivity", desc = "Quantum Molecular Assembler productivity")
    public static int quantum_molecular_assembler_productivity = 50;


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
