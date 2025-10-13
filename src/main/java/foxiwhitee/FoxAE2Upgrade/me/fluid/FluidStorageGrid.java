package foxiwhitee.FoxAE2Upgrade.me.fluid;

import appeng.api.config.Actionable;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.IGridStorage;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.data.IAEItemStack;
import appeng.util.item.AEItemStack;
import foxiwhitee.FoxAE2Upgrade.api.fluid.IFluidStorageGrid;
import foxiwhitee.FoxAE2Upgrade.items.fluid.ItemFluidDrop;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class FluidStorageGrid implements IFluidStorageGrid {
    private final IGrid storageGrid;

    public FluidStorageGrid(IGrid grid) {
        this.storageGrid = grid;
    }

    private IStorageGrid getStorageGrid() {
        return storageGrid.getCache(IStorageGrid.class);
    }

    public long injectFluid(Fluid fluid, long amount, Actionable actionable, BaseActionSource source) {
        AEItemStack stackToInject = AEItemStack.create(new ItemStack(ItemFluidDrop.DROP, 1));
        stackToInject.setStackSize(amount);
        IAEItemStack result = (IAEItemStack) getStorageGrid().getItemInventory().injectItems(stackToInject, actionable, source);
        return (result != null) ? result.getStackSize() : 0L;
    }

    public long extractFluid(Fluid fluid, long amount, Actionable actionable, BaseActionSource source) {
        AEItemStack stackToExtract = AEItemStack.create(new ItemStack(ItemFluidDrop.DROP, 1));
        stackToExtract.setStackSize(amount);
        IAEItemStack result = (IAEItemStack) getStorageGrid().getItemInventory().extractItems(stackToExtract, actionable, source);
        return (result != null) ? result.getStackSize() : 0L;
    }

    public void onUpdateTick() {}

    public void removeNode(IGridNode p0, IGridHost p1) {}

    public void addNode(IGridNode p0, IGridHost p1) {}

    public void onSplit(IGridStorage p0) {}

    public void onJoin(IGridStorage p0) {}

    public void populateGridStorage(IGridStorage p0) {}
}
