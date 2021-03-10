package com.itayfeder.redstone_revolution.common.blocks;

import net.minecraft.block.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RotatorBlock extends Block{
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    protected static final VoxelShape SHAPE = VoxelShapes.or(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D), Block.makeCuboidShape(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D));


    public RotatorBlock(AbstractBlock.Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(TRIGGERED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
        boolean flag1 = state.get(TRIGGERED);
        if (flag && !flag1) {
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
            worldIn.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(true)), 4);
        } else if (!flag && flag1) {
            worldIn.setBlockState(pos, state.with(TRIGGERED, Boolean.valueOf(false)), 4);
        }

    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        this.rotate(worldIn, pos);
    }

    protected void rotate(ServerWorld worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.up()).hasProperty(DirectionalBlock.FACING)) {
            Direction dir = worldIn.getBlockState(pos.up()).get(DirectionalBlock.FACING);
            if (dir != Direction.DOWN && dir != Direction.UP) {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(DirectionalBlock.FACING, Direction.byHorizontalIndex(dir.getHorizontalIndex() + 1 > 3 ? 0 : dir.getHorizontalIndex()+1)));
            }
        } else if (worldIn.getBlockState(pos.up()).hasProperty(HorizontalBlock.HORIZONTAL_FACING)) {
            int dir = worldIn.getBlockState(pos.up()).get(HorizontalBlock.HORIZONTAL_FACING).getHorizontalIndex();
            worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(HorizontalBlock.HORIZONTAL_FACING, Direction.byHorizontalIndex(dir + 1 > 3 ? 0 : dir+1)));
        } else if (worldIn.getBlockState(pos.up()).hasProperty(RotatedPillarBlock.AXIS)) {
            Direction.Axis axis = worldIn.getBlockState(pos.up()).get(RotatedPillarBlock.AXIS);
            if (axis != Direction.Axis.Y) {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(RotatedPillarBlock.AXIS, axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X));
            }
        } else if (worldIn.getBlockState(pos.up()).hasProperty(SkullBlock.ROTATION)) {
            int rot = worldIn.getBlockState(pos.up()).get(SkullBlock.ROTATION);
            worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(SkullBlock.ROTATION, rot + 1 > 15 ? 0 : rot+1));
        } else if (worldIn.getBlockState(pos.up()).hasProperty(BlockStateProperties.RAIL_SHAPE)) {
            RailShape shape = worldIn.getBlockState(pos.up()).get(BlockStateProperties.RAIL_SHAPE);
            if (shape == RailShape.NORTH_SOUTH || shape == RailShape.EAST_WEST) {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(BlockStateProperties.RAIL_SHAPE, shape == RailShape.NORTH_SOUTH ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH));
            }
        } else if (worldIn.getBlockState(pos.up()).hasProperty(BlockStateProperties.RAIL_SHAPE_STRAIGHT)) {
            RailShape shape = worldIn.getBlockState(pos.up()).get(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
            if (shape == RailShape.NORTH_SOUTH || shape == RailShape.EAST_WEST) {
                worldIn.setBlockState(pos.up(), worldIn.getBlockState(pos.up()).with(BlockStateProperties.RAIL_SHAPE_STRAIGHT, shape == RailShape.NORTH_SOUTH ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH));
            }
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }
}
