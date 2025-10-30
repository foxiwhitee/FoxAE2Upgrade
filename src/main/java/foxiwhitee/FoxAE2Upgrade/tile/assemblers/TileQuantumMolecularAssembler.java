package foxiwhitee.FoxAE2Upgrade.tile.assemblers;

import appeng.tile.inventory.AppEngInternalInventory;
import foxiwhitee.FoxAE2Upgrade.ModBlocks;
import foxiwhitee.FoxAE2Upgrade.config.FoxConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileQuantumMolecularAssembler extends TileUltimateMolecularAssembler {
    protected AppEngInternalInventory patternInventory = new AppEngInternalInventory(this, 108);

    public TileQuantumMolecularAssembler() {
        getProxy().setIdlePowerUsage(FoxConfig.quantum_molecular_assembler_power);
        getPatterns().setMaxStackSize(1);
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(ModBlocks.QUANTUM_MOLECULAR_ASSEMBLER);
    }

    @Override
    public long getMaxCount() {
        return FoxConfig.quantum_molecular_assembler_speed - 1L;
    }

    @Override
    protected double getPower() {
        return FoxConfig.quantum_molecular_assembler_power;
    }

    public String getName() {
        return ModBlocks.QUANTUM_MOLECULAR_ASSEMBLER.getUnlocalizedName();
    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @Override
    public boolean acceptsPlans() {
        return false;
    }

    @Override
    public AppEngInternalInventory getPatterns() {
        return patternInventory;
    }

    @Override
    public int rows() {
        return 12;
    }

    @Override
    protected int getProductivity() {
        return FoxConfig.quantum_molecular_assembler_productivity;
    }
}
