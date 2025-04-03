package com.felpslipe.testmod.block.entity;

import com.felpslipe.testmod.block.entity.energy.ModBlockEntityEnergyStorage;
import com.felpslipe.testmod.block.entity.energy.ModEnergyStorage;
import com.felpslipe.testmod.block.entity.energy.ModEnergyUtil;
import com.felpslipe.testmod.screen.custom.CoalGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == INPUT_SLOT) {
                return isFuel(stack);
            }
            return super.isItemValid(slot, stack);
        }
    };

    private static final int INPUT_SLOT = 0;

    protected final ContainerData data;
    private int burnProgress = 0;
    private int maxBurnProgress = 0;
    private boolean isBurning = false;

    private static final int ENERGY_TRANSFER_AMOUNT = 480;
    private static final int ENERGY_GENERATION_AMOUNT = 320;

    private final ModEnergyStorage ENERGY_STORAGE = new ModBlockEntityEnergyStorage(this, 64000, 0, ENERGY_TRANSFER_AMOUNT);

    public CoalGeneratorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch(index) {
                    case 0 -> CoalGeneratorBlockEntity.this.burnProgress;
                    case 1 -> CoalGeneratorBlockEntity.this.maxBurnProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch(index) {
                    case 0 -> CoalGeneratorBlockEntity.this.burnProgress = value;
                    case 1 -> CoalGeneratorBlockEntity.this.maxBurnProgress = value;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return this.ENERGY_STORAGE;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Coal Generator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CoalGeneratorMenu(containerId, playerInventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        pushEnergyToNeighbors();

        if(hasFuelItemInSlot() && !isBurningFuel() && energyStorageHasSpace()) {
            startBurning();
        }

        if(isBurningFuel()) {
            increaseBurnTimer();
            if(currentFuelDoneBurning()) {
                resetBurning();
            }
            fillUpOnEnergy();
        }
    }

    private void pushEnergyToNeighbors() {
        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = this.worldPosition.relative(direction);
            if (ModEnergyUtil.doesBlockHaveEnergyStorage(neighborPos, this.level)) {
                ModEnergyUtil.move(this.worldPosition, neighborPos, ENERGY_TRANSFER_AMOUNT, this.level);
            }
        }
    }

    private boolean hasFuelItemInSlot() {
        return isFuel(this.itemHandler.getStackInSlot(INPUT_SLOT));
    }

    private boolean isFuel(ItemStack stack) {
        return getBurnTime(stack) > 0;
    }

    private boolean isBurningFuel() {
        return isBurning;
    }

    private void startBurning() {
        ItemStack itemStack = this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        isBurning = true;
        this.maxBurnProgress = getBurnTime(itemStack);
        this.burnProgress = this.maxBurnProgress;
    }

    public static int getBurnTime(ItemStack stack) {
        return stack.getBurnTime(RecipeType.SMELTING);
    }

    private void increaseBurnTimer() {
        this.burnProgress--;
    }

    private boolean currentFuelDoneBurning() {
        return this.burnProgress <= 0;
    }

    private void resetBurning() {
        isBurning = false;
        burnProgress = 0;
    }

    private boolean energyStorageHasSpace() {
        return this.ENERGY_STORAGE.receiveEnergyInternal(ENERGY_GENERATION_AMOUNT, true) == ENERGY_GENERATION_AMOUNT;
    }

    private void fillUpOnEnergy() {
        this.ENERGY_STORAGE.receiveEnergyInternal(ENERGY_GENERATION_AMOUNT, false);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("coal_generator.inventory", itemHandler.serializeNBT(registries));
        tag.putInt("coal_generator.burn_progress", burnProgress);
        tag.putInt("coal_generator.max_burn_progress", maxBurnProgress);

        tag.putInt("coal_generator.energy", ENERGY_STORAGE.getEnergyStored());

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("coal_generator.inventory"));
        ENERGY_STORAGE.setEnergy(tag.getInt("coal_generator.energy"));

        burnProgress = tag.getInt("coal_generator.burn_progress");
        maxBurnProgress = tag.getInt("coal_generator.max_burn_progress");
        isBurning = burnProgress > 0 && maxBurnProgress > 0;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }
}
