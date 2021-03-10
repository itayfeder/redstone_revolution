package com.itayfeder.redstone_revolution.utils;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.blocks.BluestoneWireBlock;
import com.itayfeder.redstone_revolution.common.blocks.RedstoneRecieverBlock;
import com.itayfeder.redstone_revolution.common.blocks.WirelessLeverBlock;
import com.itayfeder.redstone_revolution.core.init.BlockInit;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RedstoneRevolutionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModColorHandler {

    @SubscribeEvent
    public static void registerColorHandlers(ColorHandlerEvent.Block event) {

        registerBlockColorHandlers(event.getBlockColors());
    }

    private static void registerBlockColorHandlers(final BlockColors blockColors) {
        final IBlockColor ColorWireHandler = (state, blockAccess, pos, tintIndex) -> {
            if (blockAccess != null && pos != null && blockAccess.getBlockState(pos).getBlock() instanceof BluestoneWireBlock) {
                BluestoneWireBlock Wire = (BluestoneWireBlock) (blockAccess.getBlockState(pos).getBlock());
                return BluestoneWireBlock.getRGBByPower(blockAccess.getBlockState(pos).get(BluestoneWireBlock.POWER));
            }

            return 16777215;
        };

        final IBlockColor ColorClothHandler = (state, blockAccess, pos, tintIndex) -> {
            int result = 16777215;
            if (blockAccess != null && pos != null) {
                if (blockAccess.getBlockState(pos).getBlock() instanceof RedstoneRecieverBlock) {
                    result = RedstoneRecieverBlock.GetRGB(blockAccess, pos);
                }
                else if(blockAccess.getBlockState(pos).getBlock() instanceof WirelessLeverBlock) {
                    result = WirelessLeverBlock.GetRGB(blockAccess, pos);
                }
            }

            return result;
        };

        blockColors.register(ColorWireHandler, BlockInit.BLUESTONE_WIRE.get());
        blockColors.register(ColorClothHandler, BlockInit.REDSTONE_RECIEVER.get());
        blockColors.register(ColorClothHandler, BlockInit.WIRELESS_LEVER.get());
    }
}
