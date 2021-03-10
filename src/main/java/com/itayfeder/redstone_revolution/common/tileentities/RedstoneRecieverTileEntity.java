package com.itayfeder.redstone_revolution.common.tileentities;

import com.itayfeder.redstone_revolution.common.blocks.WirelessLeverBlock;
import com.itayfeder.redstone_revolution.core.init.TileEntityInit;
import com.itayfeder.redstone_revolution.utils.DyableBlockHelper;
import com.itayfeder.redstone_revolution.utils.worlddata.WirelessLeverWorldData;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class RedstoneRecieverTileEntity extends TileEntity implements ITickableTileEntity {
    public int tick;
    public boolean initialized = false;
    private int[] rgb = {255, 255, 255};
    private int[] prev_rgb = {999, 999, 999};


    public RedstoneRecieverTileEntity() {
        super(TileEntityInit.REDSTONE_RECIEVER_TILE.get());

    }

    @Override
    public void tick() {
        if (world instanceof ServerWorld) {
            boolean didUpdate = false;
            for (BlockPos reciever : WirelessLeverWorldData.getOrCreate((ServerWorld)world).placements) {
                Vector3i fromVector = new Vector3i(this.pos.getX(), this.pos.getY(), this.pos.getZ());
                if (world.getBlockState(reciever).hasProperty(WirelessLeverBlock.POWERED) &&
                        world.getBlockState(reciever).get(WirelessLeverBlock.POWERED) &&
                        fromVector.withinDistance(new Vector3i(reciever.getX(), reciever.getY(), reciever.getZ()), 32)) {
                    WirelessLeverTileEntity tile = (WirelessLeverTileEntity)world.getTileEntity(reciever);
                    if (tile.GetRGB() == this.GetRGB()) {
                        didUpdate = true;
                        world.setBlockState(this.pos, world.getBlockState(this.pos).with(WirelessLeverBlock.POWERED, true));
                        //world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 2);
                    }
                }
                else {
                    continue;
                }
            }
            if (!didUpdate) {
                world.setBlockState(this.pos, world.getBlockState(this.pos).with(WirelessLeverBlock.POWERED, false));
                //world.notifyBlockUpdate(this.pos, this.getBlockState(), this.getBlockState(), 2);
            }
        }
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
