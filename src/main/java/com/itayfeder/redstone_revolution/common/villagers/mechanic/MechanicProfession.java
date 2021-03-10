package com.itayfeder.redstone_revolution.common.villagers.mechanic;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;

public class MechanicProfession extends VillagerProfession {
    public MechanicProfession( PointOfInterestType poiType) {
        super("mechanic", poiType, ImmutableSet.of(Items.REDSTONE), ImmutableSet.of(Blocks.OBSERVER, Blocks.REPEATER, Blocks.COMPARATOR), SoundEvents.BLOCK_METAL_PLACE);
    }
}
