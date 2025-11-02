package foxiwhitee.FoxLib.items;

import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemProductivityCard extends Item {
    private final static String[] prefixes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private final IIcon[] icons = new IIcon[prefixes.length];
    private final String name;

    public ItemProductivityCard(String name) {
        this.name = name;
        setCreativeTab(FoxLib.FOX_TAB);
        setUnlocalizedName(name);
        hasSubtypes = true;
    }

    @Override
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List<String> p_77624_3_, boolean p_77624_4_) {
        if (FoxConfig.enable_tooltips) {
            int productivity = switch (p_77624_1_.getItemDamage()) {
                case 0 -> FoxConfig.productivityLvl1;
                case 1 -> FoxConfig.productivityLvl2;
                case 2 -> FoxConfig.productivityLvl3;
                case 3 -> FoxConfig.productivityLvl4;
                case 4 -> FoxConfig.productivityLvl5;
                case 5 -> FoxConfig.productivityLvl6;
                case 6 -> FoxConfig.productivityLvl7;
                case 7 -> FoxConfig.productivityLvl8;
                case 8 -> FoxConfig.productivityLvl9;
                case 9 -> FoxConfig.productivityLvl10;
                case 10 -> FoxConfig.productivityLvl11;
                default -> 0;
            };
            p_77624_3_.add(StatCollector.translateToLocalFormatted("tooltip.productivity.card", String.valueOf(productivity)));
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        meta = Math.max(0, Math.min(meta, prefixes.length - 1));
        return icons[meta];
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < prefixes.length; i++) {
            icons[i] = register.registerIcon(FoxLib.MODID.toLowerCase() + ":productivityCards/" + name + prefixes[i]);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        int meta = (stack != null) ? stack.getItemDamage() : 0;
        return LocalizationUtils.localize(getUnlocalizedName() + ".name", prefixes[meta]);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < prefixes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
