package foxiwhitee.FoxAE2Upgrade;


import appeng.api.AEApi;
import foxiwhitee.FoxAE2Upgrade.config.ContentConfig;
import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemEmptyFluidCell;
import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemFluidDrop;
import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemFluidStorageCells;
import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemFluidStorageComponent;
import foxiwhitee.FoxAE2Upgrade.utils.helpers.RegisterUtils;
import net.minecraft.item.Item;

public class ModItems {

    public static final Item EMPTY_FLUID_CELL = new ItemEmptyFluidCell("emptyFluidCell");
    public static final Item FLUID_STORAGE_COMPONENT = new ItemFluidStorageComponent("fluidComponent");
    public static final Item FLUID_CELLS = new ItemFluidStorageCells();
    public static final Item FLUID_DROP = new ItemFluidDrop();

    public static void registerItems() {
        if (ContentConfig.enableMEFluid) {
            RegisterUtils.registerItems(FLUID_DROP, FLUID_STORAGE_COMPONENT, EMPTY_FLUID_CELL, FLUID_CELLS);
        }
    }

}
