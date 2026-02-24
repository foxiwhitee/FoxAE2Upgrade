package foxiwhitee.FoxAE2Upgrade.helpers;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.data.IAEItemStack;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.util.Platform;
import appeng.util.item.AEItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrafterHelper {
    public static void trySendItems(AENetworkProxy proxy, BaseActionSource source, List<IAEItemStack> needSend) {
        try {
            if (proxy != null) {
                List<IAEItemStack> remove = new ArrayList<>();
                IMEMonitor<IAEItemStack> storage = proxy.getStorage().getItemInventory();
                for (IAEItemStack s : needSend) {
                    if (s.getStackSize() == 0) {
                        remove.add(s);
                        continue;
                    }
                    IAEItemStack rest = Platform.poweredInsert(proxy.getEnergy(), storage, s, source);
                    if (rest != null && rest.getStackSize() > 0) {
                        s.setStackSize(rest.getStackSize());
                        break;
                    }
                    s.setStackSize(0);
                    remove.add(s);
                }
                needSend.removeAll(remove);
            }
        } catch (GridAccessException ignored) {
        }
    }

    public static List<IAEItemStack> calculateOutputs(ICraftingPatternDetails activePattern, long craftCount, InventoryCrafting craftingGrid) {
        List<IAEItemStack> outputs = calculateOutputs(activePattern, craftCount);
        for (int i = 0; i < craftingGrid.getSizeInventory(); i++) {
            ItemStack item = Platform.getContainerItem(craftingGrid.getStackInSlot(i));
            if (item != null) {
                outputs.add(AEItemStack.create(item));
            }
        }
        return outputs;
    }

    public static List<IAEItemStack> calculateOutputs(ICraftingPatternDetails activePattern, long craftCount) {
        List<IAEItemStack> outputs = new ArrayList<>();
        Arrays.stream(activePattern.getCondensedAEOutputs())
            .map(stack -> {
                IAEItemStack copy = (IAEItemStack) stack.copy();
                copy.setStackSize(copy.getStackSize() * craftCount);
                return copy;
            }).forEach(outputs::add);
        return outputs;
    }

    public static void writeToNbtNeedItems(NBTTagCompound data, List<IAEItemStack> needSend) {
        data.setInteger("needSendSize", needSend.size());
        for (int i = 0; i < needSend.size(); i++) {
            IAEItemStack is = needSend.get(i);
            NBTTagCompound tag = new NBTTagCompound();
            is.writeToNBT(tag);
            data.setTag("needSend_" + i, tag);
        }
    }

    public static void readFromNbtNeedItems(NBTTagCompound data, List<IAEItemStack> needSend) {
        if (data.hasKey("needSendSize")) {
            int size = data.getInteger("needSendSize");
            needSend.clear();
            for (int i = 0; i < size; i++) {
                NBTTagCompound tag = data.getCompoundTag("needSend_" + i);
                needSend.add(AEItemStack.loadItemStackFromNBT(tag));
            }
        }
    }
}
