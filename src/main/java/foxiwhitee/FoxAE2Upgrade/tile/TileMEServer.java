package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.definitions.IBlocks;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkCraftingCpuChange;
import appeng.api.util.WorldCoord;
import appeng.core.AELog;
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
import foxiwhitee.FoxLib.api.orientable.FastOrientableManager;
import foxiwhitee.FoxLib.api.orientable.IOrientable;
import foxiwhitee.FoxLib.integration.applied.api.ITileMEServer;
import foxiwhitee.FoxLib.integration.applied.api.crafting.ICraftingCPUClusterAccessor;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TileMEServer extends TileCraftingTile implements IAEAppEngInventory, ITileMEServer, IOrientable {
    private static final int CLUSTER_COUNT = 12;
    private static Method addTileMethod;

    static {
        try {
            addTileMethod = CraftingCPUCluster.class.getDeclaredMethod("addTile", TileCraftingTile.class);
            addTileMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            AELog.error("Critical Error: Could not find addTile method in CraftingCPUCluster!");
        }
    }

    private final List<CraftingCPUCluster> virtualClusters = new ArrayList<>(CLUSTER_COUNT);
    private final AppEngInternalInventory storage = new AppEngInternalInventory(this, CLUSTER_COUNT);
    private final AppEngInternalInventory accelerators = new AppEngInternalInventory(this, CLUSTER_COUNT);

    private final long[] storage_bytes = new long[CLUSTER_COUNT];
    private final int[] accelerators_count = new int[CLUSTER_COUNT];
    private final NBTTagCompound[] previousStates = new NBTTagCompound[CLUSTER_COUNT];
    private final int orientableId = FastOrientableManager.nextId();

    public TileMEServer() {
        this.getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        WorldCoord loc = new WorldCoord(this);
        for (int i = 0; i < CLUSTER_COUNT; i++) {
            virtualClusters.add(new CraftingCPUCluster(loc, loc));
        }
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation ops, ItemStack removed, ItemStack added) {
        if (Platform.isClient() || ops == InvOperation.markDirty) {
            return;
        }

        if (inv == storage) {
            calculateCraftingStorage(slot, added);
        } else if (inv == accelerators) {
            calculateCraftingAccelerators(slot, added);
        }

        if (this.getProxy().isReady()) {
            this.initializeClusters();
        }
    }

    private void calculateCraftingStorage(int id, ItemStack stack) {
        if (stack == null) {
            storage_bytes[id] = 0;
            return;
        }

        IBlocks blocks = AEApi.instance().definitions().blocks();
        long unit = 0;

        if (blocks.craftingStorage1k().isSameAs(stack)) {
            unit = 1024;
        } else if (blocks.craftingStorage4k().isSameAs(stack)) {
            unit = 1024 * 4;
        } else if (blocks.craftingStorage16k().isSameAs(stack)) {
            unit = 1024 * 16;
        } else if (blocks.craftingStorage64k().isSameAs(stack)) {
            unit = 1024 * 64;
        } else if (blocks.craftingStorage256k().isSameAs(stack)) {
            unit = 1024 * 256;
        } else if (blocks.craftingStorage1024k().isSameAs(stack)) {
            unit = 1024 * 1024;
        } else if (blocks.craftingStorage4096k().isSameAs(stack)) {
            unit = 1024 * 4096;
        } else if (blocks.craftingStorage16384k().isSameAs(stack)) {
            unit = 1024 * 16384;
        } else if (blocks.craftingStorageSingularity().isSameAs(stack)) {
            unit = Long.MAX_VALUE;
        }
        storage_bytes[id] = (long) Math.min(Long.MAX_VALUE - 1000000, ((double) unit * stack.stackSize));
    }

    private void calculateCraftingAccelerators(int id, ItemStack stack) {
        if (stack == null) {
            accelerators_count[id] = 0;
            return;
        }

        IBlocks blocks = AEApi.instance().definitions().blocks();
        int unit = 0;

        if (blocks.craftingAccelerator().isSameAs(stack)) {
            unit = 1;
        } else if (blocks.craftingAccelerator4x().isSameAs(stack)) {
            unit = 4;
        } else if (blocks.craftingAccelerator16x().isSameAs(stack)) {
            unit = 16;
        } else if (blocks.craftingAccelerator64x().isSameAs(stack)) {
            unit = 64;
        } else if (blocks.craftingAccelerator256x().isSameAs(stack)) {
            unit = 256;
        } else if (blocks.craftingAccelerator1024x().isSameAs(stack)) {
            unit = 1024;
        } else if (blocks.craftingAccelerator4096x().isSameAs(stack)) {
            unit = 4096;
        }

        accelerators_count[id] = unit * stack.stackSize;
    }

    public void initializeClusters() {
        if (Platform.isClient() || worldObj == null) {
            return;
        }

        WorldCoord loc = new WorldCoord(this);
        for (int i = 0; i < CLUSTER_COUNT; i++) {
            CraftingCPUCluster oldCluster = virtualClusters.get(i);

            FMLCommonHandler.instance().bus().unregister(oldCluster);
            oldCluster.cancel();

            CraftingCPUCluster newCluster = new CraftingCPUCluster(loc, loc);
            virtualClusters.set(i, newCluster);

            FMLCommonHandler.instance().bus().register(newCluster);
            this.bindTileToCluster(newCluster);

            if (previousStates[i] != null) {
                newCluster.readFromNBT(previousStates[i]);
            }

            newCluster.updateStatus(true);
            ((ICraftingCPUClusterAccessor) (Object) newCluster).doneMEServer();
        }

        this.updateMeta(true);
        this.notifyNetwork();
    }

    private void bindTileToCluster(CraftingCPUCluster cluster) {
        try {
            if (addTileMethod != null) {
                addTileMethod.invoke(cluster, this);
            }
        } catch (Exception e) {
            AELog.error("Reflection Error in ME Server: " + e.getMessage());
        }
    }

    @Override
    public void updateMultiBlock() {
    }

    @Override
    public void onReady() {
        super.onReady();
        this.initializeClusters();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.disconnect(false);
    }

    @Override
    public void disconnect(boolean update) {
        for (CraftingCPUCluster cluster : virtualClusters) {
            if (cluster != null) {
                FMLCommonHandler.instance().bus().unregister(cluster);
                cluster.destroy();
            }
        }
        if (update) {
            this.updateMeta(true);
        }
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_(NBTTagCompound data) {
        storage.writeToNBT(data, "storage");
        accelerators.writeToNBT(data, "accelerators");

        NBTTagCompound clustersTag = new NBTTagCompound();
        for (int i = 0; i < virtualClusters.size(); i++) {
            NBTTagCompound tag = new NBTTagCompound();
            virtualClusters.get(i).writeToNBT(tag);
            clustersTag.setTag("c" + i, tag);
        }
        data.setTag("virtualClusters", clustersTag);

        data.setByte("f_fwd", (byte) getForward().ordinal());
        data.setByte("f_up", (byte) getUp().ordinal());
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_(NBTTagCompound data) {
        storage.readFromNBT(data, "storage");
        accelerators.readFromNBT(data, "accelerators");

        NBTTagCompound clustersTag = data.getCompoundTag("virtualClusters");
        for (int i = 0; i < CLUSTER_COUNT; i++) {
            calculateCraftingStorage(i, storage.getStackInSlot(i));
            calculateCraftingAccelerators(i, accelerators.getStackInSlot(i));
            if (clustersTag.hasKey("c" + i)) {
                previousStates[i] = clustersTag.getCompoundTag("c" + i);
            }
        }

        if (data.hasKey("f_fwd")) {
            this.setOrientation(
                ForgeDirection.getOrientation(data.getByte("f_fwd")),
                ForgeDirection.getOrientation(data.getByte("f_up"))
            );
        }
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream(ByteBuf data) {
        if (this.canBeRotated()) {
            data.writeByte((byte) getForward().ordinal());
            data.writeByte((byte) getUp().ordinal());
        }
    }

    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean output = false;
        if (this.canBeRotated()) {
            ForgeDirection oldForward = this.getForward(), oldUp = this.getUp();
            byte orientationForward = data.readByte(), orientationUp = data.readByte();
            ForgeDirection newForward = ForgeDirection.getOrientation(orientationForward & 7);
            ForgeDirection newUp = ForgeDirection.getOrientation(orientationUp & 7);
            this.setOrientation(newForward, newUp);
            output = newForward != oldForward || newUp != oldUp;
        }
        return output;
    }

    private void notifyNetwork() {
        IGridNode node = getGridNode(ForgeDirection.UNKNOWN);
        if (node != null && node.getGrid() != null) {
            node.getGrid().postEvent(new MENetworkCraftingCpuChange(node));
        }
    }

    @Override
    public long getClusterStorageBytes(int idx) {
        return (idx >= 0 && idx < CLUSTER_COUNT) ? storage_bytes[idx] : 0;
    }

    @Override
    public int getClusterAccelerator(int idx) {
        return (idx >= 0 && idx < CLUSTER_COUNT) ? accelerators_count[idx] : 0;
    }

    @Override
    public int getClusterIndex(CraftingCPUCluster c) {
        return virtualClusters.indexOf(c);
    }

    @Override
    public List<CraftingCPUCluster> getVirtualClusters() {
        return virtualClusters;
    }

    @Override
    public boolean isFormed() {
        return true;
    }

    @Override
    protected AENetworkProxy createProxy() {
        return new AENetworkProxyMultiblock(this, "proxy", getItemFromTile(this), true);
    }

    @Override
    public ForgeDirection getForward() {
        return FastOrientableManager.getForward(orientableId);
    }

    @Override
    public ForgeDirection getUp() {
        return FastOrientableManager.getUp(orientableId);
    }

    @Override
    public void setOrientation(ForgeDirection f, ForgeDirection u) {
        FastOrientableManager.set(orientableId, f, u);
        this.markForUpdate();
    }

    public AppEngInternalInventory getStorage() {
        return this.storage;
    }

    public AppEngInternalInventory getAccelerators() {
        return this.accelerators;
    }

    public NBTTagCompound getPreviousState(int index) {
        if (index >= 0 && index < CLUSTER_COUNT) {
            return this.previousStates[index];
        }
        return null;
    }

    public void setPreviousState(int index, NBTTagCompound state) {
        if (index >= 0 && index < CLUSTER_COUNT) {
            this.previousStates[index] = state;
        }
    }
}
