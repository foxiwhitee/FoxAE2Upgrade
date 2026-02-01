package foxiwhitee.FoxAE2Upgrade.tile;

import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingGrid;
import appeng.api.networking.crafting.ICraftingJob;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.networking.crafting.ICraftingRequester;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.MachineSource;
import appeng.api.networking.storage.IStackWatcher;
import appeng.api.networking.storage.IStackWatcherHost;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.helpers.NonNullArrayIterator;
import appeng.me.GridAccessException;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.inventory.AppEngInternalAEInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.item.AEItemStack;
import com.google.common.collect.ImmutableSet;
import foxiwhitee.FoxLib.utils.helpers.ItemStackUtil;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TileLevelMaintainer extends TileAENetworkInvOrientable implements IStackWatcherHost, ICraftingRequester, IGridTickable {
    private static final int timeoutLong = 600;
    private static final int timeoutShort = 300;
    private final static int slots = 6;
    private final AppEngInternalAEInventory inventory = new AppEngInternalAEInventory(this, 6);
    private IStackWatcher myWatcher;
    private final boolean[] enable;
    private final long[] need;
    private final long[] craft;
    private final long[] cachedCurrentCounts;
    private final long[] cooldownUntil;
    private final ICraftingLink[] activeLinks;
    private final Future<ICraftingJob>[] pendingJobs;
    private final Reference2IntOpenHashMap<ICraftingLink> linkToSlotMap;

    public TileLevelMaintainer() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        getProxy().setIdlePowerUsage(128);
        this.enable = new boolean[slots];
        this.need = new long[slots];
        this.craft = new long[slots];
        this.cachedCurrentCounts = new long[slots];
        this.cooldownUntil = new long[slots];
        this.activeLinks = new ICraftingLink[slots];
        this.pendingJobs = new Future[slots];
        this.linkToSlotMap = new Reference2IntOpenHashMap<>(slots);
        this.linkToSlotMap.defaultReturnValue(-1);
        Arrays.fill(this.craft, 1);
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode iGridNode) {
        return new TickingRequest(20, 20, false, false);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode iGridNode, int i) {
        for (int j = 0; j < slots; j++) {
            if (enable[j]) {
                if (need[j] > cachedCurrentCounts[j]) {
                    IAEItemStack aes = inventory.getAEStackInSlot(j);
                    if (aes != null) {
                        aes = aes.copy();
                        aes.setStackSize(craft[j]);
                        try {
                            tryBeginCrafting(j, aes, getWorldObj(), getProxy().getGrid(), getProxy().getCrafting(), new MachineSource(this));
                        } catch (GridAccessException ignored) {}
                    }
                }
            }
        }
        return TickRateModulation.IDLE;
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNbt_(NBTTagCompound data) {
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < slots; ++i) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setLong("need", this.need[i]);
            tag.setLong("craft", this.craft[i]);
            tag.setBoolean("enable", this.enable[i]);
            list.appendTag(tag);
        }
        data.setTag("requestSetting", list);
        list = new NBTTagList();
        for (ICraftingLink link : this.activeLinks) {
            NBTTagCompound linkTag = new NBTTagCompound();
            if (link != null) link.writeToNBT(linkTag);
            list.appendTag(linkTag);
        }
        data.setTag("links", list);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNbt_(NBTTagCompound data) {
        NBTTagList list = data.getTagList("requestSetting", 10);
        for(int i = 0; i < slots; ++i) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            this.need[i] = tag.getLong("need");
            this.craft[i] = tag.getLong("craft");
            this.enable[i] = tag.getBoolean("enable");
        }
        markForUpdate();
        this.linkToSlotMap.clear();
        list = data.getTagList("links", 10);
        for(int i = 0; i < this.activeLinks.length; ++i) {
            NBTTagCompound linkTag = list.getCompoundTagAt(i);
            if (linkTag.hasNoTags()) {
                this.activeLinks[i] = null;
            } else {
                ICraftingLink link = AEApi.instance().storage().loadCraftingLink(linkTag, this);
                this.activeLinks[i] = link;
                this.linkToSlotMap.put(link, i);
            }
        }
    }

    @TileEvent(TileEventType.NETWORK_WRITE)
    public void writeToStream(ByteBuf data) {
        for (int i = 0; i < slots; ++i) {
            data.writeLong(this.need[i]);
            data.writeLong(this.craft[i]);
            data.writeBoolean(this.enable[i]);
        }
    }

    @TileEvent(TileEventType.NETWORK_READ)
    public boolean readFromStream(ByteBuf data) {
        boolean changed = false;
        for (int i = 0; i < slots; ++i) {
            long oldNeed = this.need[i];
            long oldCraft = this.craft[i];
            boolean oldEnable = this.enable[i];
            this.need[i] = data.readLong();
            this.craft[i] = data.readLong();
            this.enable[i] = data.readBoolean();
            changed |= oldNeed != this.need[i] || oldCraft != this.craft[i] || oldEnable != this.enable[i];
        }
        return changed;
    }

    public boolean isOnCooldown(int slot, World world) {
        return world.getTotalWorldTime() < this.cooldownUntil[slot];
    }

    private void cleanupLinks() {
        for(int i = 0; i < this.activeLinks.length; ++i) {
            ICraftingLink link = this.activeLinks[i];
            if (link != null && (link.isCanceled() || link.isDone())) {
                this.linkToSlotMap.removeInt(link);
                this.activeLinks[i] = null;
            }
        }
    }

    private void tryBeginCrafting(int slot, IAEStack<?> stack, World world, IGrid grid, ICraftingGrid craftingGrid, BaseActionSource source) {
        if (this.activeLinks[slot] != null) return;

        Future<ICraftingJob> future = this.pendingJobs[slot];

        if (future == null) {
            if (this.isOnCooldown(slot, world)) return;
            this.pendingJobs[slot] = craftingGrid.beginCraftingJob(world, grid, source, stack.copy(), null);
        } else if (future.isDone()) {
            try {
                ICraftingJob job = future.get();
                if (job != null) {

                    ICraftingLink link = craftingGrid.submitJob(job, this, null, false, source);
                    this.pendingJobs[slot] = null;

                    if (link != null) {
                        this.activeLinks[slot] = link;
                        this.linkToSlotMap.put(link, slot);
                        this.cleanupLinks();
                        return;
                    }

                    this.cooldownUntil[slot] = Math.max(this.cooldownUntil[slot], world.getTotalWorldTime() + timeoutShort);
                }
            } catch (ExecutionException e) {
                this.cooldownUntil[slot] = world.getTotalWorldTime() + timeoutLong;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(this);
    }

    @Override
    public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
        return AECableType.SMART;
    }

    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation op, ItemStack itemStack, ItemStack itemStack1) {
        if (op != InvOperation.markDirty && inventory == inv && slot >= 0) {
            IAEItemStack aeStack = inventory.getAEStackInSlot(slot);
            if (aeStack == null) {
                this.need[slot] = 0;
                this.craft[slot] = 1;
                this.enable[slot] = false;
            } else {
                for (int i = 0; i < slots; ++i) {
                    if (slot != i && ItemStackUtil.stackEquals(inventory.getStackInSlot(i), aeStack.getItemStack())) {
                        inventory.setInventorySlotContents(slot, null);
                        return;
                    }
                }
                aeStack.setStackSize(1);
            }
            this.resetSlotCache(slot);
            this.markForUpdate();
        }
    }

    private void resetSlotCache(int slot) {
        this.cachedCurrentCounts[slot] = -1;
        this.configureWatchers();
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return new int[0];
    }

    @Override
    public void updateWatcher(IStackWatcher iStackWatcher) {
        this.myWatcher = iStackWatcher;
        this.configureWatchers();
    }

    @Override
    public void onStackChange(IItemList o, IAEStack fullStack, IAEStack diffStack, BaseActionSource src, StorageChannel chan) {
        for (int j = 0; j < inventory.getSizeInventory(); j++) {
            ItemStack stack = inventory.getStackInSlot(j);
            if (fullStack instanceof IAEItemStack aes && ItemStackUtil.stackEquals(stack, aes.getItemStack())) {
                cachedCurrentCounts[j] = aes.getStackSize();
            }
        }
        this.markForUpdate();
    }

    private void configureWatchers() {
        if (this.myWatcher != null) {
            this.myWatcher.clear();
        }

        if (this.myWatcher != null) {
            for (int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack itemStack = inventory.getStackInSlot(i);
                if (itemStack != null) {
                    this.myWatcher.add(AEItemStack.create(itemStack));
                }
            }
        }
    }

    @Override
    public ImmutableSet<ICraftingLink> getRequestedJobs() {
        return ImmutableSet.copyOf(new NonNullArrayIterator<>(this.activeLinks));
    }

    private int getSlotForLink(ICraftingLink link) {
        return this.linkToSlotMap.getInt(link);
    }

    @Override
    public IAEStack<?> injectCraftedItems(ICraftingLink link, IAEStack<?> stack, Actionable mode) {
        if (!(stack instanceof IAEItemStack aes)) return null;

        int slot = getSlotForLink(link);
        if (slot == -1) return aes;

        try {
            return getProxy().getStorage().getItemInventory().injectItems(aes, mode, new MachineSource(this));
        } catch (GridAccessException e) {
            return aes;
        }
    }

    private boolean removeLink(ICraftingLink link) {
        int slot = this.linkToSlotMap.removeInt(link);
        if (slot == -1) return false;
        this.activeLinks[slot] = null;
        this.cleanupLinks();
        return true;
    }

    @Override
    public void jobStateChange(ICraftingLink link) {
        if (this.removeLink(link)) {
            this.markForUpdate();
        }
    }

    public void changeButtonMode(int id) {
        if (id >= 0 && id < slots) {
            this.enable[id] = !this.enable[id];
            markForUpdate();
        }
    }

    public void setNeed(int id, long value) {
        if (id >= 0 && id < slots) {
            this.need[id] = Math.max(value, 0);
            markForUpdate();
        }
    }

    public void setCraft(int id, long value) {
        if (id >= 0 && id < slots) {
            this.craft[id] = Math.max(value, 1);
            markForUpdate();
        }
    }

    public boolean[] getEnable() {
        return enable;
    }

    public long[] getNeed() {
        return need;
    }

    public long[] getCraft() {
        return craft;
    }

    @Override
    public void getDrops(World w, int x, int y, int z, List<ItemStack> drops) {

    }
}
