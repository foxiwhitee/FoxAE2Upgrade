package foxiwhitee.FoxAE2Upgrade.items.fluid;

import appeng.api.AEApi;
import appeng.api.storage.*;
import appeng.api.storage.data.IAEItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foxiwhitee.FoxAE2Upgrade.FoxCore;
import foxiwhitee.FoxAE2Upgrade.ModItems;
import foxiwhitee.FoxAE2Upgrade.api.fluid.IFluidStorageCell;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxAE2Upgrade.utils.localization.LocalizationUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemFluidStorageCells extends Item implements IFluidStorageCell {
    public static final String[] suffixes = new String[]{
            "1K", "4K", "16K", "64K", "256K",
            "1M", "4M", "16M", "65M", "262M",
            "1G", "4G", "16G", "67G", "268G",
            "1T"};
    public static final long[] bytes_cell = new long[]{
            1024, 4096, 16384, 65536, 262144,
            1048576, 4194304, 16777216, 67108864, 268435456,
            1073741824, 4294967296L, 17179869184L, 68719476736L, 274877906944L,
            1099511627776L};
    public static final int[] types_cell = new int[]{
            FoxConfig.typesFluidCellK, FoxConfig.typesFluidCellK, FoxConfig.typesFluidCellK, FoxConfig.typesFluidCellK, FoxConfig.typesFluidCellK,
        FoxConfig.typesFluidCellM, FoxConfig.typesFluidCellM, FoxConfig.typesFluidCellM, FoxConfig.typesFluidCellM, FoxConfig.typesFluidCellM,
        FoxConfig.typesFluidCellG, FoxConfig.typesFluidCellG, FoxConfig.typesFluidCellG, FoxConfig.typesFluidCellG, FoxConfig.typesFluidCellG,
        FoxConfig.typesFluidCellT};
    private IIcon[] icons;

    public ItemFluidStorageCells() {
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("fluidStorageCells");
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        ICellRegistry cellRegistry = AEApi.instance().registries().cell();
        IMEInventoryHandler<IAEItemStack> invHandler = cellRegistry.getCellInventory(itemStack, (ISaveProvider)null, StorageChannel.ITEMS);
        ICellInventoryHandler inventoryHandler = (ICellInventoryHandler)invHandler;
        ICellInventory cellInv = inventoryHandler.getCellInv();
        long usedBytes = cellInv.getUsedBytes();
        list.add(LocalizationUtils.localize("tooltip.storage.fluid.bytes", usedBytes, cellInv.getTotalBytes()));
        list.add(LocalizationUtils.localize("tooltip.storage.fluid.types", cellInv.getStoredItemTypes(), cellInv.getTotalItemTypes()));
        if (usedBytes > 0L) {
            list.add(LocalizationUtils.localize("tooltip.storage.fluid.content", cellInv.getStoredItemCount()));
        }

    }

    @Override
    public boolean isFluidCell(ItemStack paramItemStack) {
        return true;
    }

    @Override
    public double getIdleDrain(ItemStack paramItemStack) {
        return 0;
    }

    public long getBytes(ItemStack cellItem) {
        return bytes_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)];
    }

    @Override
    public int getTypes(ItemStack cellItem) {
        return types_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)];
    }

    public IIcon getIconFromDamage(int dmg) {
        return this.icons[MathHelper.clamp_int(dmg, 0, suffixes.length - 1)];
    }

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        if (stack == null) {
            return super.getItemStackDisplayName(null);
        } else {
            return super.getItemStackDisplayName(stack);
        }
    }

    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.epic;
    }

    public void getSubItems(Item item, CreativeTabs creativeTab, List itemList) {
        for(int i = 0; i < suffixes.length; ++i) {
            itemList.add(new ItemStack(item, 1, i));
        }

    }

    public int getTotalTypes(ItemStack cellItem) {
        return types_cell[MathHelper.clamp_int(cellItem.getItemDamage(), 0, suffixes.length - 1)];
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return "item.fluidCell" + suffixes[itemStack.getItemDamage()];
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (itemStack == null) {
            return itemStack;
        } else if (!entityPlayer.isSneaking()) {
            return itemStack;
        } else {
            IMEInventoryHandler<IAEItemStack> invHandler = AEApi.instance().registries().cell().getCellInventory(itemStack, (ISaveProvider)null, StorageChannel.ITEMS);
            ICellInventoryHandler inventoryHandler = (ICellInventoryHandler)invHandler;
            ICellInventory cellInv = inventoryHandler.getCellInv();
            return cellInv.getUsedBytes() == 0L && entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.EMPTY_FLUID_CELL)) ? new ItemStack(ModItems.FLUID_STORAGE_COMPONENT, 1, itemStack.getItemDamage()) : itemStack;
        }
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.icons = new IIcon[suffixes.length];

        for(int i = 0; i < suffixes.length; ++i) {
            this.icons[i] = iconRegister.registerIcon(FoxCore.MODID.toLowerCase() + ":сells/fluidCell" + suffixes[i]);
        }

    }

}

