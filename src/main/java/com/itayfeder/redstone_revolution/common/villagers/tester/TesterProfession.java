package com.itayfeder.redstone_revolution.common.villagers.tester;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;

public class TesterProfession extends VillagerProfession {
    public TesterProfession( PointOfInterestType poiType) {
        super("tester", poiType, ImmutableSet.of(Items.DIAMOND_HOE), ImmutableSet.of(Blocks.PODZOL), SoundEvents.BLOCK_METAL_PLACE);
    }
}
