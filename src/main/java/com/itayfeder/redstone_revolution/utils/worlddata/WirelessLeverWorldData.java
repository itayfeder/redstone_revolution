package com.itayfeder.redstone_revolution.utils.worlddata;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.List;

public class WirelessLeverWorldData extends WorldSavedData {
    public static final String DATA_KEY = RedstoneRevolutionMod.MOD_ID + ":wireless_lever";
    public List<BlockPos> placements = new ArrayList<BlockPos>();

    public WirelessLeverWorldData()
    {
        super(DATA_KEY);
    }

    @Override
    public void read(CompoundNBT nbt)
    {
        long[] longs = nbt.getLongArray("locations");
        for (long pos : longs) {
            BlockPos realpos = BlockPos.fromLong(pos);
            placements.add(realpos);
        }
    }

    public static WirelessLeverWorldData getOrCreate(ServerWorld world)
    {
        return world.getSavedData().getOrCreate(WirelessLeverWorldData::new, DATA_KEY);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        List<Long> longs = new ArrayList<Long>();
        for (BlockPos pos : placements) {
            longs.add(pos.toLong());
        }
        compound.putLongArray("locations",longs);
        return compound;
    }
}
