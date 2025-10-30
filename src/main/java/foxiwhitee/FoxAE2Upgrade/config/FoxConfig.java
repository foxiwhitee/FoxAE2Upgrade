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


    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 1?")
    public static int productivityLvl1 = 1;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 2?")
    public static int productivityLvl2 = 3;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 3?")
    public static int productivityLvl3 = 5;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 4?")
    public static int productivityLvl4 = 7;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 5?")
    public static int productivityLvl5 = 10;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 6?")
    public static int productivityLvl6 = 15;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 7?")
    public static int productivityLvl7 = 25;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 8?")
    public static int productivityLvl8 = 50;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 9?")
    public static int productivityLvl9 = 75;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 10?")
    public static int productivityLvl10 = 100;

    @ConfigValue(category = "productivity", desc = "How much percent will the performance increase with the Productivity Card 11?")
    public static int productivityLvl11 = 200;


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
