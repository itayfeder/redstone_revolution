package com.itayfeder.redstone_revolution.common.blocks;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class RandomizerBlock extends RedstoneDiodeBlock {

    public static final EnumProperty<RandomizerPowerOutput> SIDE_POWERED = EnumProperty.create("side", RandomizerPowerOutput.class);

    public RandomizerBlock(AbstractBlock.Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(SIDE_POWERED, RandomizerPowerOutput.NONE).with(POWERED, Boolean.valueOf(false)));
    }

    protected int getDelay(BlockState state) {
        return 0;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = super.getStateForPlacement(context);
        return blockstate;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    protected boolean isAlternateInput(BlockState state) {
        return isDiode(state);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, SIDE_POWERED, POWERED);
    }

    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.getWeakPower(blockAccess, pos, side);
    }

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        if (!blockState.get(POWERED)) {
            return 0;
        } else {
            if (blockState.get(SIDE_POWERED) == RandomizerPowerOutput.LEFT) {
                return blockState.get(HORIZONTAL_FACING).rotateYCCW() == side ? this.getActiveSignal(blockAccess, pos, blockState) : 0;
            }
            else {
                return blockState.get(HORIZONTAL_FACING).rotateY() == side ? this.getActiveSignal(blockAccess, pos, blockState) : 0;
            }
        }
    }


    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = state.get(POWERED);
            if (flag != this.shouldBePowered(worldIn, pos, state)) {
                if (flag) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 1);
                } else {
                    Random rnd = new Random();
                    int side = rnd.nextInt(2);
                    if (side == 0) {
                        worldIn.setBlockState(pos, state.func_235896_a_(POWERED).with(SIDE_POWERED, RandomizerPowerOutput.LEFT), 3);
                    }
                    else {
                        worldIn.setBlockState(pos, state.func_235896_a_(POWERED).with(SIDE_POWERED, RandomizerPowerOutput.RIGHT), 3);
                    }
                }
            }

        }
    }

    protected boolean shouldBePowered(World worldIn, BlockPos pos, BlockState state) {
        return this.calculateInputStrength(worldIn, pos, state) > 0;
    }

    protected int calculateInputStrength(World worldIn, BlockPos pos, BlockState state) {
        Direction direction = state.get(HORIZONTAL_FACING);
        BlockPos blockpos = pos.offset(direction);
        int i = worldIn.getRedstonePower(blockpos, direction);
        if (i >= 15) {
            return i;
        } else {
            BlockState blockstate = worldIn.getBlockState(blockpos);
            return Math.max(i, blockstate.isIn(Blocks.REDSTONE_WIRE) ? blockstate.get(RedstoneWireBlock.POWER) : 0);
        }
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(POWERED) && !this.shouldBePowered(worldIn, pos, state)) {
            worldIn.setBlockState(pos, state.func_235896_a_(POWERED), 3);
        }
    }

    public enum RandomizerPowerOutput implements IStringSerializable {
        NONE("none"),
        LEFT("left"),
        RIGHT("right");

        private final String name;

        private RandomizerPowerOutput(String name) {
            this.name = name;
        }

        public String getString() {
            return this.name;
        }
    }

}
