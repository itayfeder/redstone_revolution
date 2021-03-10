package com.itayfeder.redstone_revolution.common.villagers.mechanic;

import net.minecraft.block.Blocks;
import net.minecraft.village.PointOfInterestType;

public class PointOfInterestMechanicType extends PointOfInterestType {

    public PointOfInterestMechanicType() {
        super("mechanic", getAllStates(Blocks.OBSERVER), 1, 1);
    }
}