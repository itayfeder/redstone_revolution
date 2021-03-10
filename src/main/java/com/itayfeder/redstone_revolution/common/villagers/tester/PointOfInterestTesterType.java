package com.itayfeder.redstone_revolution.common.villagers.tester;

import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;

public class PointOfInterestTesterType extends PointOfInterestType {

    public PointOfInterestTesterType() {
        super("tester", getAllStates(Blocks.PODZOL), 1, 1);
    }
}