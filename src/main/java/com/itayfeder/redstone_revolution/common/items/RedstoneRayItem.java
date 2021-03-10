package com.itayfeder.redstone_revolution.common.items;

import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RedstoneRayItem extends Item {
    public RedstoneRayItem(Item.Properties builder) {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        Vector3d start = playerIn.getEyePosition(0);
        Vector3d look = playerIn.getLook(0);
        Vector3d end = start.add(look.scale(32));
        BlockRayTraceResult resultBlock = worldIn.rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, playerIn));
        if (resultBlock.getType() != RayTraceResult.Type.MISS) {
            playerIn.sendMessage(worldIn.getBlockState(resultBlock.getPos()).getBlock().getTranslatedName(), playerIn.getUniqueID());
            if (worldIn.getBlockState(resultBlock.getPos()).getBlock() instanceof RedstoneLampBlock) {
                worldIn.setBlockState(resultBlock.getPos(), worldIn.getBlockState(resultBlock.getPos()).with(RedstoneLampBlock.LIT, true));
                worldIn.getPendingBlockTicks().scheduleTick(resultBlock.getPos(), worldIn.getBlockState(resultBlock.getPos()).getBlock(), 10);
            }
            else if (worldIn.getBlockState(resultBlock.getPos()).getBlock() instanceof DispenserBlock) {
                worldIn.setBlockState(resultBlock.getPos(), worldIn.getBlockState(resultBlock.getPos()).with(DispenserBlock.TRIGGERED, true));
                worldIn.getPendingBlockTicks().scheduleTick(resultBlock.getPos(), worldIn.getBlockState(resultBlock.getPos()).getBlock(), 1);
            }
            else if (worldIn.getBlockState(resultBlock.getPos()).getBlock() instanceof TNTBlock) {
                worldIn.getBlockState(resultBlock.getPos()).getBlock().catchFire(worldIn.getBlockState(resultBlock.getPos()), worldIn, resultBlock.getPos(), null, null);
                worldIn.setBlockState(resultBlock.getPos(), Blocks.AIR.getDefaultState());
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
