package com.itayfeder.redstone_revolution.common.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrenchItem extends Item {
    public WrenchItem(Item.Properties builder) {
        super(builder);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity playerentity = context.getPlayer();
        World world = context.getWorld();
        if (playerentity.isCrouching()) {
            BlockPos blockpos = context.getPos();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() instanceof RedstoneDiodeBlock) {
                switch (blockstate.get(HorizontalBlock.HORIZONTAL_FACING)) {
                    case NORTH:
                        world.setBlockState(blockpos, blockstate.with(HorizontalBlock.HORIZONTAL_FACING, Direction.EAST));
                        break;
                    case EAST:
                        world.setBlockState(blockpos, blockstate.with(HorizontalBlock.HORIZONTAL_FACING, Direction.SOUTH));
                        break;
                    case SOUTH:
                        world.setBlockState(blockpos, blockstate.with(HorizontalBlock.HORIZONTAL_FACING, Direction.WEST));
                        break;
                    case WEST:
                        world.setBlockState(blockpos, blockstate.with(HorizontalBlock.HORIZONTAL_FACING, Direction.NORTH));
                        break;
                }
            }
            else if (blockstate.getBlock() instanceof DispenserBlock || blockstate.getBlock() instanceof ObserverBlock || (blockstate.getBlock() instanceof PistonBlock && !blockstate.get(PistonBlock.EXTENDED))) {
                switch (blockstate.get(DirectionalBlock.FACING)) {
                    case NORTH:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.EAST));
                        break;
                    case EAST:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.SOUTH));
                        break;
                    case SOUTH:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.WEST));
                        break;
                    case WEST:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.UP));
                        break;
                    case UP:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.DOWN));
                        break;
                    case DOWN:
                        world.setBlockState(blockpos, blockstate.with(DirectionalBlock.FACING, Direction.NORTH));
                        break;
                }
            }
        }

        return ActionResultType.func_233537_a_(world.isRemote);
    }
}
