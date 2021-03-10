package com.itayfeder.redstone_revolution.common.tileentities;

import com.itayfeder.redstone_revolution.core.init.TileEntityInit;
import com.itayfeder.redstone_revolution.utils.DyableBlockHelper;
import com.itayfeder.redstone_revolution.utils.worlddata.WirelessLeverWorldData;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class WirelessLeverTileEntity extends TileEntity implements ITickableTileEntity {
    public int tick;
    public boolean initialized = false;
    private int[] rgb = {255, 255, 255};
    private int[] prev_rgb = {999, 999, 999};

    public WirelessLeverTileEntity() {
        super(TileEntityInit.WIRELESS_LEVER_TILE.get());

    }

    @Override
    public void tick() {
        if (!initialized && world instanceof ServerWorld) {
            if(!WirelessLeverWorldData.getOrCreate((ServerWorld)world).placements.contains(this.pos)) {
                WirelessLeverWorldData.getOrCreate((ServerWorld)world).placements.add(this.pos);
            }
            initialized = true;
        }
    }

    @Override
    public void remove() {
        if (world instanceof ServerWorld) {
            WirelessLeverWorldData.getOrCreate((ServerWorld)world).placements.remove(this.pos);
        }
        super.remove();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putIntArray("color", this.rgb.clone());
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("color")) {
            this.rgb = nbt.getIntArray("color").clone();
            this.prev_rgb = nbt.getIntArray("color").clone();
        }
    }

    public int GetRGB() {
        return DyableBlockHelper.GetColor(this.rgb);
    }

    public void SetRGB(int color) {
        this.rgb = DyableBlockHelper.ColorToRGB(color);
        markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 1);
    }

    public void UpdateRGB() {
        prev_rgb = rgb;
        markDirty();
        this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 1);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(), -1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        final CompoundNBT nbt = super.getUpdateTag();
        nbt.putIntArray("color", this.rgb.clone());
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(null,pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.rgb = tag.getIntArray("color").clone();
        this.prev_rgb = tag.getIntArray("color").clone();
    }
}
