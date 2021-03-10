package com.itayfeder.redstone_revolution.common.blocks;

import com.itayfeder.redstone_revolution.common.tileentities.WirelessLeverTileEntity;
import com.itayfeder.redstone_revolution.utils.DyableBlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class WirelessLeverBlock extends HorizontalFaceBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    protected static final VoxelShape LEVER_NORTH_AABB = Block.makeCuboidShape(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
    protected static final VoxelShape LEVER_SOUTH_AABB = Block.makeCuboidShape(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
    protected static final VoxelShape LEVER_WEST_AABB = Block.makeCuboidShape(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
    protected static final VoxelShape LEVER_EAST_AABB = Block.makeCuboidShape(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
    protected static final VoxelShape FLOOR_Z_SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
    protected static final VoxelShape FLOOR_X_SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
    protected static final VoxelShape CEILING_Z_SHAPE = Block.makeCuboidShape(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
    protected static final VoxelShape CEILING_X_SHAPE = Block.makeCuboidShape(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);

    public WirelessLeverBlock(AbstractBlock.Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)).with(FACE, AttachFace.WALL));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch((AttachFace)state.get(FACE)) {
            case FLOOR:
                switch(state.get(HORIZONTAL_FACING).getAxis()) {
                    case X:
                        return FLOOR_X_SHAPE;
                    case Z:
                    default:
                        return FLOOR_Z_SHAPE;
                }
            case WALL:
                switch((Direction)state.get(HORIZONTAL_FACING)) {
                    case EAST:
                        return LEVER_EAST_AABB;
                    case WEST:
                        return LEVER_WEST_AABB;
                    case SOUTH:
                        return LEVER_SOUTH_AABB;
                    case NORTH:
                    default:
                        return LEVER_NORTH_AABB;
                }
            case CEILING:
            default:
                switch(state.get(HORIZONTAL_FACING).getAxis()) {
                    case X:
                        return CEILING_X_SHAPE;
                    case Z:
                    default:
                        return CEILING_Z_SHAPE;
                }
        }
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (itemstack.isEmpty()) {
            if (worldIn.isRemote) {
                BlockState blockstate1 = state.func_235896_a_(POWERED);
                if (blockstate1.get(POWERED)) {
                    addParticles(blockstate1, worldIn, pos, 1.0F);
                }

                return ActionResultType.SUCCESS;
            } else {
                BlockState blockstate = this.setPowered(state, worldIn, pos);
                float f = blockstate.get(POWERED) ? 0.6F : 0.5F;
                worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
                return ActionResultType.CONSUME;
            }
        } else {
            if (itemstack.getItem() instanceof DyeItem) {
                if (tile instanceof WirelessLeverTileEntity) {
                    WirelessLeverTileEntity wirelesslever = (WirelessLeverTileEntity)tile;
                    int[] white = {255, 255, 255};
                    if (wirelesslever.GetRGB() == DyableBlockHelper.GetColor(white)) {
                        wirelesslever.SetRGB(DyeColor.getColor(itemstack).getColorValue());
                    }
                    else {
                        wirelesslever.SetRGB(DyableBlockHelper.AddColor(wirelesslever.GetRGB(), DyeColor.getColor(itemstack).getColorValue()));
                    }
                    return ActionResultType.SUCCESS;

                }
            } else if (itemstack.getItem() == Items.PAPER) {
                if (tile instanceof WirelessLeverTileEntity) {
                    WirelessLeverTileEntity wirelesslever = (WirelessLeverTileEntity)tile;
                    int[] white = {255, 255, 255};
                    wirelesslever.SetRGB(DyableBlockHelper.GetColor(white));
                    return ActionResultType.SUCCESS;

                }
            } else {
                if (worldIn.isRemote) {
                    BlockState blockstate1 = state.func_235896_a_(POWERED);
                    if (blockstate1.get(POWERED)) {
                        addParticles(blockstate1, worldIn, pos, 1.0F);
                    }

                    return ActionResultType.SUCCESS;
                } else {
                    BlockState blockstate = this.setPowered(state, worldIn, pos);
                    float f = blockstate.get(POWERED) ? 0.6F : 0.5F;
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
                    return ActionResultType.CONSUME;
                }
            }
        }
        return ActionResultType.PASS;
    }

    public BlockState setPowered(BlockState state, World world, BlockPos pos) {
        state = state.func_235896_a_(POWERED);
        world.setBlockState(pos, state, 3);
        this.updateNeighbors(state, world, pos);
        return state;
    }

    private static void addParticles(BlockState state, IWorld worldIn, BlockPos pos, float alpha) {
        Direction direction = state.get(HORIZONTAL_FACING).getOpposite();
        Direction direction1 = getFacing(state).getOpposite();
        double d0 = (double)pos.getX() + 0.5D + 0.1D * (double)direction.getXOffset() + 0.2D * (double)direction1.getXOffset();
        double d1 = (double)pos.getY() + 0.5D + 0.1D * (double)direction.getYOffset() + 0.2D * (double)direction1.getYOffset();
        double d2 = (double)pos.getZ() + 0.5D + 0.1D * (double)direction.getZOffset() + 0.2D * (double)direction1.getZOffset();
        worldIn.addParticle(new RedstoneParticleData(1.0F, 0.0F, 0.0F, alpha), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(POWERED) && rand.nextFloat() < 0.25F) {
            addParticles(stateIn, worldIn, pos, 0.5F);
        }

    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WirelessLeverTileEntity();
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && !state.isIn(newState.getBlock())) {
            if (state.get(POWERED)) {
                this.updateNeighbors(state, worldIn, pos);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    private void updateNeighbors(BlockState state, World world, BlockPos pos) {
        world.notifyNeighborsOfStateChange(pos, this);
        world.notifyNeighborsOfStateChange(pos.offset(getFacing(state).getOpposite()), this);
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACE, HORIZONTAL_FACING, POWERED);
    }

    public static int GetRGB(IBlockDisplayReader worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof WirelessLeverTileEntity) {
            return ((WirelessLeverTileEntity)tile).GetRGB();
        }
        return -1;
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
    }
}
