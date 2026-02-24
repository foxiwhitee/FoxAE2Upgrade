package foxiwhitee.FoxAE2Upgrade.proxy;

import appeng.core.Api;
import appeng.items.misc.ItemEncodedPattern;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.ModRecipes;
import foxiwhitee.FoxAE2Upgrade.block.BlockAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.block.BlockCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.block.BlockLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.block.BlockMEServer;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.block.assemblers.BlockUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.container.ContainerAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.container.ContainerCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.container.ContainerLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.container.ContainerMEServer;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerQuantumMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.container.assemblers.ContainerUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.network.packets.C2SSetValuesInLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.tile.TileAdvancedDrive;
import foxiwhitee.FoxAE2Upgrade.tile.TileCobblestoneDuper;
import foxiwhitee.FoxAE2Upgrade.tile.TileLevelMaintainer;
import foxiwhitee.FoxAE2Upgrade.tile.TileMEServer;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileAdvancedMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileUltimateMolecularAssembler;
import foxiwhitee.FoxAE2Upgrade.tile.assemblers.TileQuantumMolecularAssembler;
import foxiwhitee.FoxLib.api.FoxLibApi;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModRecipes.registerRecipes();

    }

    public void init(FMLInitializationEvent event) {
        SlotFiltered.filters.put("encodedPattern", stack -> stack != null && stack.getItem() != null && stack.getItem() instanceof ItemEncodedPattern);
        FoxLibApi.instance.registries().registerGui()
            .register(BlockAdvancedMolecularAssembler.class, TileAdvancedMolecularAssembler.class, ContainerAdvancedMolecularAssembler.class)
            .register(BlockUltimateMolecularAssembler.class, TileUltimateMolecularAssembler.class, ContainerUltimateMolecularAssembler.class)
            .register(BlockQuantumMolecularAssembler.class, TileQuantumMolecularAssembler.class, ContainerQuantumMolecularAssembler.class)
            .register(BlockAdvancedDrive.class, TileAdvancedDrive.class, ContainerAdvancedDrive.class)
            .register(BlockCobblestoneDuper.class, TileCobblestoneDuper.class, ContainerCobblestoneDuper.class)
            .register(BlockLevelMaintainer.class, TileLevelMaintainer.class, ContainerLevelMaintainer.class)
            .register(BlockMEServer.class, TileMEServer.class, ContainerMEServer.class);

        if (ContentConfig.enableLevelMaintainer) {
            FoxLibApi.instance.registries().registerPacket().register(C2SSetValuesInLevelMaintainer.class);
        }
        if (ContentConfig.enableMolecularAssemblers) {
            Api.INSTANCE.registries().interfaceTerminal().register(TileAdvancedMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileUltimateMolecularAssembler.class);
            Api.INSTANCE.registries().interfaceTerminal().register(TileQuantumMolecularAssembler.class);
        }
        if (ContentConfig.enableCables) {
            for (int i = 0; i < 17; i++) {
                OreDictionary.registerOre("cableAlite", new ItemStack(ModItems.itemParts, 1, i));
            }
            for (int i = 17; i < 34; i++) {
                OreDictionary.registerOre("cableBimare", new ItemStack(ModItems.itemParts, 1, i));
            }
            for (int i = 34; i < 51; i++) {
                OreDictionary.registerOre("cableDefit", new ItemStack(ModItems.itemParts, 1, i));
            }
            for (int i = 51; i < 68; i++) {
                OreDictionary.registerOre("cableEfrim", new ItemStack(ModItems.itemParts, 1, i));
            }
            for (int i = 68; i < 85; i++) {
                OreDictionary.registerOre("cableNur", new ItemStack(ModItems.itemParts, 1, i));
            }
            for (int i = 85; i < 102; i++) {
                OreDictionary.registerOre("cableXaur", new ItemStack(ModItems.itemParts, 1, i));
            }
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
