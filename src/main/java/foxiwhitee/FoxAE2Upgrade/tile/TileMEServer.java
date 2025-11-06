package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCraftingCpuChange;
import appeng.api.util.WorldCoord;
import appeng.core.AELog;
import appeng.me.cluster.implementations.CraftingCPUCalculator;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.AENetworkProxyMultiblock;
import appeng.tile.TileEvent;
import appeng.tile.crafting.TileCraftingTile;
import appeng.tile.events.TileEventType;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.IAEAppEngInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import cpw.mods.fml.common.FMLCommonHandler;
import foxiwhitee.FoxLib.integration.applied.api.ITileMEServer;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingCPUClusterAccessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TileMEServer extends TileCraftingTile implements IAEAppEngInventory, ITileMEServer {
    private final List<CraftingCPUCluster> virtualClusters = new ArrayList<>(12);
    private boolean isFormed = true;
    private AppEngInternalInventory storage = new AppEngInternalInventory(this, 12);
    private AppEngInternalInventory accelerators = new AppEngInternalInventory(this, 12);
    private final CraftingCPUCalculator calc = new CraftingCPUCalculator(this);

    private long[] storage_bytes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] accelerators_count = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private NBTTagCompound[] previousStates = new NBTTagCompound[12];

    public TileMEServer() {
        this.getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        WorldCoord loc = new WorldCoord(this);
        for (int i = 0; i < 12; i++) {
            virtualClusters.add(new CraftingCPUCluster(loc, loc));
        }
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        if (this.getProxy().isReady() && (itemStack != null || itemStack1 != null)) {
            if (iInventory == storage) {
                calculateCraftingStorage(i, storage.getStackInSlot(i));
            }
            if (iInventory == accelerators) {
                calculateCraftingAccelerators(i, accelerators.getStackInSlot(i));
            }
            initializeClusters();
        } else if (invOperation == InvOperation.markDirty) {
            for (int j = 0; j < 12; j++) {
                calculateCraftingStorage(j, storage.getStackInSlot(j));
                calculateCraftingAccelerators(j, accelerators.getStackInSlot(j));
            }
            initializeClusters();
        }
    }

    private void calculateCraftingStorage(int id, ItemStack stack) {
        if (id >= 0 && id <= storage_bytes.length) {
            if (stack == null) {
                storage_bytes[id] = 0;
                return;
            }
            long storageAmt = AEApi.instance().definitions().blocks().craftingStorage1k().isSameAs(stack) ? 1024
                : AEApi.instance().definitions().blocks().craftingStorage4k().isSameAs(stack) ? 1024 * 4
                : AEApi.instance().definitions().blocks().craftingStorage16k().isSameAs(stack) ? 1024 * 16
                : AEApi.instance().definitions().blocks().craftingStorage64k().isSameAs(stack) ? 1024 * 64
                : AEApi.instance().definitions().blocks().craftingStorage256k().isSameAs(stack) ? 1024 * 256
                : AEApi.instance().definitions().blocks().craftingStorage1024k().isSameAs(stack) ? 1024 * 1024
                : AEApi.instance().definitions().blocks().craftingStorage4096k().isSameAs(stack) ? 1024 * 4096
                : AEApi.instance().definitions().blocks().craftingStorage16384k().isSameAs(stack) ? 1024 * 16384
                : AEApi.instance().definitions().blocks().craftingStorageSingularity().isSameAs(stack) ? Long.MAX_VALUE
                : 0;
            storageAmt *= stack.stackSize;
            storage_bytes[id] = storageAmt;
        }
    }

    private void calculateCraftingAccelerators(int id, ItemStack stack) {
        if (id >= 0 && id <= accelerators_count.length) {
            if (stack == null) {
                accelerators_count[id] = 0;
                return;
            }
            int acceleratorAmt = AEApi.instance().definitions().blocks().craftingAccelerator().isSameAs(stack) ? 1
                : AEApi.instance().definitions().blocks().craftingAccelerator4x().isSameAs(stack) ? 4
                : AEApi.instance().definitions().blocks().craftingAccelerator16x().isSameAs(stack) ? 16
                : AEApi.instance().definitions().blocks().craftingAccelerator64x().isSameAs(stack) ? 64
                : AEApi.instance().definitions().blocks().craftingAccelerator256x().isSameAs(stack) ? 256
                : AEApi.instance().definitions().blocks().craftingAccelerator1024x().isSameAs(stack) ? 1024
                : AEApi.instance().definitions().blocks().craftingAccelerator4096x().isSameAs(stack) ? 4096
                : 0;
            acceleratorAmt *= stack.stackSize;
            accelerators_count[id] = acceleratorAmt;
        }
    }

    public long getClusterStorageBytes(int clusterIndex) {
        if (clusterIndex >= 0 && clusterIndex < storage_bytes.length) {
            return storage_bytes[clusterIndex];
        }
        return 0;
    }

    public int getClusterAccelerator(int clusterIndex) {
        if (clusterIndex >= 0 && clusterIndex < accelerators_count.length) {
            return accelerators_count[clusterIndex];
        }
        return 0;
    }

    public int getClusterIndex(CraftingCPUCluster cluster) {
        return virtualClusters.indexOf(cluster);
    }

    @Override
    public final void updateMultiBlock() {
    }

    @Override
    protected AENetworkProxy createProxy() {
        return new AENetworkProxyMultiblock(this, "proxy", this.getItemFromTile(this), true);
    }

    public void initializeClusters() {
        if (Platform.isClient()) return;

        try {
            virtualClusters.forEach(CraftingCPUCluster::cancel);
            virtualClusters.clear();

            WorldCoord loc = new WorldCoord(this);
            for (int i = 0; i < 12; i++) {
                CraftingCPUCluster cluster = new CraftingCPUCluster(loc, loc);
                virtualClusters.add(cluster);
                FMLCommonHandler.instance().bus().register(cluster);
                invokeAddTile(cluster, this);
                cluster.updateStatus(true);
            }

            updateMeta(true);
            notifyNetwork();
        } catch (Throwable err) {
            AELog.debug("Error initializing ME Server clusters: %s", err.getMessage());
            disconnect(true);
        }
    }

    private void invokeAddTile(CraftingCPUCluster cluster, TileMEServer tile) {
        try {
            Method addTileMethod = CraftingCPUCluster.class.getDeclaredMethod("addTile", TileCraftingTile.class);
            addTileMethod.setAccessible(true);
            addTileMethod.invoke(cluster, tile);
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            AELog.debug("Failed to invoke addTile: %s", e.getMessage());
        }
    }

    @Override
    public void updateMeta(boolean updateFormed) {
        if (worldObj == null || notLoaded()) return;

        boolean power = getProxy().isActive();
        int newMeta = (isFormed ? 8 : 0) | (power ? 4 : 0);

        if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != newMeta) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, newMeta, 2);
        }

        if (updateFormed) {
            getProxy().setValidSides(isFormed ? EnumSet.allOf(ForgeDirection.class) : EnumSet.noneOf(ForgeDirection.class));
        }
    }

    @Override
    public boolean isFormed() {
        return isFormed;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_TileCraftingTile(NBTTagCompound data) {
        storage.writeToNBT(data, "storage");
        accelerators.writeToNBT(data, "accelerators");
        data.setBoolean("core", true);
        NBTTagCompound clusters = new NBTTagCompound();
        for (int i = 0; i < virtualClusters.size(); i++) {
            NBTTagCompound clusterData = new NBTTagCompound();
            virtualClusters.get(i).writeToNBT(clusterData);
            clusters.setTag("cluster_" + i, clusterData);
        }
        data.setTag("virtualClusters", clusters);
    }

    @Override
    public World getWorldObj() {
        return super.getWorldObj();
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_TileCraftingTile(NBTTagCompound data) {
        storage.readFromNBT(data, "storage");
        accelerators.readFromNBT(data, "accelerators");
        setCoreBlock(data.getBoolean("core"));
        NBTTagCompound clusters = data.getCompoundTag("virtualClusters");
        for (int i = 0; i < storage.getSizeInventory(); i++) {
            if (storage.getStackInSlot(i) != null) {
                calculateCraftingStorage(i, storage.getStackInSlot(i));
            }
        }
        for (int i = 0; i < accelerators.getSizeInventory(); i++) {
            if (accelerators.getStackInSlot(i) != null) {
                calculateCraftingAccelerators(i, accelerators.getStackInSlot(i));
            }
        }
        initializeClusters();
        try {
            for (int i = 0; i < virtualClusters.size(); i++) {
                setPreviousState(i, clusters.getCompoundTag("cluster_" + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect(boolean update) {
        for (CraftingCPUCluster cluster : virtualClusters) {
            if (cluster != null) cluster.destroy();
        }
        virtualClusters.clear();
        if (update) updateMeta(true);
    }

    private void notifyNetwork() {
        IGridNode node = getGridNode(ForgeDirection.UNKNOWN);
        if (node != null && node.getGrid() != null) {
            node.getGrid().postEvent(new MENetworkCraftingCpuChange(node));
        }
    }

    @Override
    public void onReady() {
        super.onReady();
        initializeClusters();
        virtualClusters.forEach(craftingCPUCluster -> {
            ((ICraftingCPUClusterAccessor) (Object) craftingCPUCluster).doneMEServer();
        });
    }

    public List<CraftingCPUCluster> getVirtualClusters() {
        return virtualClusters;
    }

    public AppEngInternalInventory getStorage() {
        return storage;
    }

    public AppEngInternalInventory getAccelerators() {
        return accelerators;
    }

    public NBTTagCompound getPreviousState(int index) {
        return this.previousStates[index];
    }

    public void setPreviousState(int index, NBTTagCompound previousState) {
        this.previousStates[index] = previousState;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {
        super.getDrops(w, x, y, z, drops);
        storage.forEach(stack -> {
            if (stack != null) {
                drops.add(stack);
            }
        });
        accelerators.forEach(stack -> {
            if (stack != null) {
                drops.add(stack);
            }
        });
    }
}
