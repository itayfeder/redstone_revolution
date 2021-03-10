package com.itayfeder.redstone_revolution.common.blocks;

import com.itayfeder.redstone_revolution.common.tileentities.RedstoneRecieverTileEntity;
import com.itayfeder.redstone_revolution.utils.DyableBlockHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.ToIntFunction;

public class RedstoneRecieverBlock extends Block {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneRecieverBlock(AbstractBlock.Properties builder) {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(POWERED, Boolean.valueOf(false)));
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasSolidSideOnTop(worldIn, pos.down());
    }

    public static boolean isDiode(BlockState state) { return true; }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    public boolean canProvidePower(BlockState state) {
        return true;
    }

    protected int getRedstoneStrength(BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    protected BlockState setRedstoneStrength(BlockState state, int strength) {
        return state.with(POWERED, Boolean.valueOf(strength > 0));
    }

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return this.getRedstoneStrength(blockState);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new RedstoneRecieverTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getHeldItem(handIn);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (itemstack.isEmpty()) {
            return ActionResultType.PASS;
        } else {
            if (itemstack.getItem() instanceof DyeItem) {
                if (tile instanceof RedstoneRecieverTileEntity) {
                    RedstoneRecieverTileEntity reciever = (RedstoneRecieverTileEntity)tile;
                    int[] white = {255, 255, 255};
                    if (reciever.GetRGB() == DyableBlockHelper.GetColor(white)) {
                        reciever.SetRGB(DyeColor.getColor(itemstack).getColorValue());
                    }
                    else {
                        reciever.SetRGB(DyableBlockHelper.AddColor(reciever.GetRGB(), DyeColor.getColor(itemstack).getColorValue()));
                    }
                    return ActionResultType.SUCCESS;

                }
            } else if (itemstack.getItem() == Items.PAPER) {
                if (tile instanceof RedstoneRecieverTileEntity) {
                    RedstoneRecieverTileEntity reciever = (RedstoneRecieverTileEntity)tile;
                    int[] white = {255, 255, 255};
                    reciever.SetRGB(DyableBlockHelper.GetColor(white));
                    return ActionResultType.SUCCESS;

                }
            }
        }
        return ActionResultType.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(POWERED)) {
            double d0 = (double)pos.getX() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d1 = (double)pos.getY() + 0.65D + (rand.nextDouble() - 0.5D) * 0.2D;
            double d2 = (double)pos.getZ() + 0.5D + (rand.nextDouble() - 0.5D) * 0.2D;
            worldIn.addParticle(RedstoneParticleData.REDSTONE_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }


    public static ToIntFunction<BlockState> getLitValue(int lightValue) {
        return (state) -> {
            return state.get(POWERED) ? lightValue : 0;
        };
    }



    public static int GetRGB(IBlockDisplayReader worldIn, BlockPos pos) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof RedstoneRecieverTileEntity) {
            return ((RedstoneRecieverTileEntity)tile).GetRGB();
        }
        return -1;
    }
}
