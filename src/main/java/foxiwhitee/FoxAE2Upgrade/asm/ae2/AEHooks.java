package foxiwhitee.FoxAE2Upgrade.asm.ae2;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IMachineSet;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.sync.GuiBridge;
import appeng.crafting.CraftingLink;
import appeng.me.Grid;
import appeng.me.GridNode;
import appeng.me.MachineSet;
import appeng.me.cache.CraftingGridCache;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.me.helpers.AENetworkProxy;
import appeng.tile.crafting.TileCraftingStorageTile;
import appeng.tile.crafting.TileCraftingTile;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;

import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemFluidDrop;

import foxiwhitee.FoxAE2Upgrade.proxy.CommonProxy;

import foxiwhitee.FoxAE2Upgrade.utils.craft.ICraftingGridCacheAddition;
import net.minecraft.item.ItemStack;

public class AEHooks {

    public static long skipBytes(long old, IAEItemStack what) {
        if (what.getItem() == ItemFluidDrop.DROP)
            return 0L;
        return old;
    }

    public static void onGridNotification(AENetworkProxy proxy) {
        //if (proxy.getMachine() instanceof PartBaseCable)
        //    ((PartBaseCable)proxy.getMachine()).markForUpdate();
    }

    public static int getMaxChannelCount(GridNode node, int old) {
        //if (node.getGridBlock().getMachine() instanceof ICustomChannelCount)
        //    return ((ICustomChannelCount)node.getGridBlock().getMachine()).getMaxChannelSize();
        return old;
    }

    public static long getStorageBytes(long currentBytes, TileCraftingTile tile) {
        //if (tile instanceof TileCustomCraftingStorage) {
        //    long bytes = currentBytes + ((TileCustomCraftingStorage) tile).getStorageBytesLong() - 1;
        //    if (bytes < 0) return Long.MAX_VALUE - 1;
        //    return bytes;
        //}
        return currentBytes;
    }

    public static ItemStack getItem(Object target, ItemStack old) {
//        if (target instanceof TileAdvancedInterface) {
//            return new ItemStack(ModBlocks.ADVANCED_INTERFACE);
//        }
//        if (target instanceof TileHybridInterface) {
//            return new ItemStack(ModBlocks.HYBRID_INTERFACE);
//        }
//        if (target instanceof TileUltimateInterface) {
//            return new ItemStack(ModBlocks.ULTIMATE_INTERFACE);
//        }
//        if (target instanceof PartAdvancedInterface) {
//            return EnumParts.PART_ADV_INTERFACE.getStack();
//        }
//        if (target instanceof PartHybridInterface) {
//            return EnumParts.PART_HYBRID_INTERFACE.getStack();
//        }
//        if (target instanceof PartUltimateInterface) {
//            return EnumParts.PART_ULTIMATE_INTERFACE.getStack();
//        }
        return old;
    }

    public static GuiBridge getGui(Object target, GuiBridge old) {
//        if (target instanceof TileAdvancedInterface || target instanceof PartAdvancedInterface) {
//            return CommonProxy.getGuiAdvInterface();
//        }
//        if (target instanceof TileHybridInterface || target instanceof PartHybridInterface) {
//            return CommonProxy.getGuiHybridInterface();
//        }
//        if (target instanceof TileUltimateInterface || target instanceof PartUltimateInterface) {
//            return CommonProxy.getGuiUltimateInterface();
//        }
//        if (target instanceof PartAdvancedInterfaceTerminal) {
//            return CommonProxy.getGuiBridge(0);
//        }
        return old;
    }

    public static void updateCPUClusters(IGrid grid, ICraftingGridCacheAddition cache) {
//        for(IGridNode cst : grid.getMachines(TileMEServer.class)) {
//            TileMEServer tile = (TileMEServer)cst.getMachine();
//            for (int i = 0; i < tile.getVirtualClusters().size(); i++) {
//                CraftingCPUCluster cluster = (CraftingCPUCluster)tile.getVirtualClusters().get(i);
//                if (cluster != null) {
//                    cache.getCraftingCPUClusters().add(cluster);
//                    if (cluster.getLastCraftingLink() != null) {
//                        ((CraftingGridCache)cache).addLink((CraftingLink)cluster.getLastCraftingLink());
//                    }
//                }
//            }
//        }
    }

    public static IMachineSet getMachines(MachineSet old, Grid grid) {
//        if (old.getMachineClass() == TileCraftingStorageTile.class) {
//            MachineSet n = ((IMachineSetAccessor)old).create(old.getMachineClass());
//            n.addAll(old);
//            old = n;
//            MachineSet set = (MachineSet) grid.getMachines(TileCustomCraftingStorage.class);
//            old.addAll(set);
//        }
        return old;
    }

    public static GuiBridge getContainerConfirm(Object target, GuiBridge old) {
        return old;
    }

    public static GuiBridge getGuiConfirm(Object target, GuiBridge old) {
        return old;
    }

}
