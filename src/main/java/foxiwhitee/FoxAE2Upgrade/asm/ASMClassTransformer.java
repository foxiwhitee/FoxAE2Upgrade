package foxiwhitee.FoxAE2Upgrade.asm;

import foxiwhitee.FoxAE2Upgrade.asm.ae2.AETransformer;
import net.minecraft.launchwrapper.IClassTransformer;

public class ASMClassTransformer  implements IClassTransformer {
    public ASMClassTransformer() {
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        switch (name) {
//            case "appeng.client.gui.implementations.GuiPriority":
//                return AETransformer.transformGuiPriority(basicClass);
//            case "appeng.me.Grid":
//                return AETransformer.transformGrid(basicClass);
//            case "appeng.me.cluster.implementations.CraftingCPUCluster":
//                return AETransformer.transformCraftingCPUCluster(basicClass);
//            case "appeng.helpers.DualityInterface":
//                return AETransformer.transformDualityInterface(basicClass);
//            case "appeng.tile.misc.TileInterface":
//                return AETransformer.transformTileInterface(basicClass);
//            case "appeng.parts.misc.PartInterface":
//                return AETransformer.transformPartInterface(basicClass);
//            case "appeng.me.NetworkEventBus":
//                return AETransformer.transformNetworkEventBus(basicClass);
//            case "appeng.me.MachineSet":
//                return AETransformer.transformMachineSet(basicClass);
//            case "appeng.api.networking.crafting.ICraftingMedium":
//                return AETransformer.transformICraftingMedium(basicClass);
//            case "appeng.client.gui.implementations.GuiMEMonitorable":
//                return AETransformer.transformGuiMEMonitorable(basicClass);
//            case "appeng.client.gui.implementations.GuiCraftConfirm":
//                return AETransformer.transformGuiCraftConfirm(basicClass);
//            case "appeng.container.implementations.ContainerCraftConfirm":
//                return AETransformer.transformContainerCraftConfirm(basicClass);
//            case "appeng.core.sync.packets.PacketMEInventoryUpdate":
//                return AETransformer.transformPacketMEInventoryUpdate(basicClass);
//            case "appeng.helpers.IInterfaceHost":
//                return AETransformer.transformIInterfaceHost(basicClass);
//            case "appeng.me.cache.CraftingGridCache":
//                return AETransformer.transformCraftingGridCache(basicClass);
//            case "appeng.me.cluster.implementations.CraftingCPUCalculator":
//                return AETransformer.transformCraftingCPUCalculator(basicClass);
//            case "appeng.crafting.CraftingTreeNode":
//                return AETransformer.transformCraftingTreeNode(basicClass);
//            case "appeng.client.me.ItemRepo":
//                return basicClass;//AETransformer.transformItemRepo(basicClass);
//            case "net.minecraft.client.gui.FontRenderer":
//                return MinecraftTransformer.transformFontRenderer(basicClass);
        }
        return basicClass;
    }
}

