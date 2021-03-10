package com.itayfeder.redstone_revolution.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VentilatorBlock extends DirectionalBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public VentilatorType type = null;

    public static final Map<VentilatorType, Integer> Lengths = new HashMap<VentilatorType, Integer>() {{
        put(VentilatorType.WOOD, 5);
        put(VentilatorType.IRON, 10);
        put(VentilatorType.GOLD, 15);
    }};

    public VentilatorBlock(VentilatorType type, AbstractBlock.Properties properties) {
        super(properties);
        this.type=type;
        this.setDefaultState(this.stateContainer.getBaseState()
                .with(FACING, Direction.SOUTH).with(POWERED, Boolean.valueOf(false)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        context.getWorld().getPendingBlockTicks().scheduleTick(context.getPos(), this, 2);
        return this.getDefaultState()
                .with(FACING, context.getNearestLookingDirection().getOpposite())
                .with(POWERED, Boolean.valueOf(context.getWorld().isBlockPowered(context.getPos())));
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (!worldIn.isRemote) {
            boolean flag = state.get(POWERED);
            if (flag != worldIn.isBlockPowered(pos)) {
                if (flag) {
                    worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlockState(pos, state.func_235896_a_(POWERED), 2);
                }
            }

        }
    }

    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.get(POWERED) && !worldIn.isBlockPowered(pos)) {
            worldIn.setBlockState(pos, state.func_235896_a_(POWERED), 2);
        }
        if (state.get(POWERED)) {
            double xmodifier = 0.1;
            double xmotion = 0;
            double ymodifier = 0.1;
            double ymotion = 0;
            double zmodifier = 0.1;
            double zmotion = 0;
            switch (state.get(FACING)) {
                case EAST:
                    xmotion = 0.15;
                    break;
                case WEST:
                    xmotion = -0.15;
                    break;
                case SOUTH:
                    zmotion = 0.15;
                    break;
                case NORTH:
                    zmotion = -0.15;
                    break;
                case UP:
                    ymotion = 0.15;
                    break;
                case DOWN:
                    ymotion = -0.15;
                    break;
            }
            for (int j = 1; j <= Lengths.get(this.type); j++) {
                switch (state.get(FACING)) {
                    case EAST:
                        xmodifier += 1;
                        break;
                    case WEST:
                        xmodifier -= 1;
                        break;
                    case SOUTH:
                        zmodifier += 1;
                        break;
                    case NORTH:
                        zmodifier -= 1;
                        break;
                    case UP:
                        ymodifier += 1;
                        break;
                    case DOWN:
                        ymodifier -= 1;
                        break;
                }
                if (!worldIn.isAirBlock(new BlockPos((double) pos.getX() + xmodifier, (double) pos.getY() + ymodifier, (double) pos.getZ() + zmodifier))) {
                    j = Lengths.get(this.type);
                    break;
                }
                AxisAlignedBB axisalignedbb = new AxisAlignedBB((double) pos.getX() + xmodifier,
                        (double) pos.getY() + ymodifier,
                        (double) pos.getZ() + zmodifier,
                        (double) pos.getX() + xmodifier + 0.8,
                        (double) pos.getY() + ymodifier + 0.8,
                        (double) pos.getZ() + zmodifier + 0.8);
                List<? extends Entity> list = worldIn.getEntitiesWithinAABB(Entity.class, axisalignedbb);
                for (Entity entity : list) {
                    entity.addVelocity(xmotion, ymotion, zmotion);
                    entity.velocityChanged = true;
                }
            }
        }
        worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(POWERED)) {
            double xmodifier = 0.5;
            double ymodifier = 0.5;
            double zmodifier = 0.5;
            for (int i = 1; i <= Lengths.get(this.type); i++) {
                switch(stateIn.get(FACING)) {
                    case EAST:
                        xmodifier += 1;
                        break;
                    case WEST:
                        xmodifier -= 1;
                        break;
                    case SOUTH:
                        zmodifier += 1;
                        break;
                    case NORTH:
                        zmodifier -= 1;
                        break;
                    case UP:
                        ymodifier += 1;
                        break;
                    case DOWN:
                        ymodifier -= 1;
                        break;
                }
                if (!worldIn.isAirBlock(new BlockPos((double)pos.getX() + xmodifier, (double)pos.getY() + ymodifier, (double)pos.getZ() + zmodifier))) {
                    i = Lengths.get(this.type);
                    break;
                }
                worldIn.addParticle(ParticleTypes.CLOUD, (double)pos.getX() + xmodifier, (double)pos.getY() + ymodifier, (double)pos.getZ() + zmodifier, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public enum VentilatorType {
        WOOD,
        IRON,
        GOLD
    }
}
