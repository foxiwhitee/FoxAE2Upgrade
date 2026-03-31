package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.api.networking.GridFlags;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import foxiwhitee.FoxLib.integration.applied.tile.TilePatternMachine;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

@SuppressWarnings("unused")
public abstract class TileCustomMolecularAssembler extends TilePatternMachine {
    private static final int[] NO_SLOTS = new int[0];

    public TileCustomMolecularAssembler() {
        getProxy().setFlags(GridFlags.REQUIRE_CHANNEL);
        getProxy().setIdlePowerUsage(getPower());
    }

    @Override
    protected boolean isValidCraft(ICraftingPatternDetails iCraftingPatternDetails) {
        return iCraftingPatternDetails.isCraftable();
    }

    @Override
    public FoxInternalInventory getInternalInventory() {
        return (FoxInternalInventory) getPatterns();
    }

    @Override
    public int[] getAccessibleSlotsBySide(ForgeDirection forgeDirection) {
        return NO_SLOTS;
    }

    protected abstract double getPower();
}
