package com.itayfeder.redstone_revolution.events;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.blocks.RedstoneWatcherBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = RedstoneRevolutionMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldTickEvents {

    @SubscribeEvent
    public static void WorldTickSubbedEvent(final TickEvent.WorldTickEvent event) {
        List<? extends PlayerEntity> players = event.world.getPlayers();
        for(PlayerEntity player : players) {
            Vector3d start = player.getEyePosition(0);
            Vector3d look = player.getLook(0);
            Vector3d end = start.add(look.scale(32));
            BlockRayTraceResult resultBlock = event.world.rayTraceBlocks(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));
            if (resultBlock.getType() != RayTraceResult.Type.MISS && event.world.getBlockState(resultBlock.getPos()).getBlock() instanceof RedstoneWatcherBlock) {
                event.world.getPendingBlockTicks().scheduleTick(resultBlock.getPos(), event.world.getBlockState(resultBlock.getPos()).getBlock(), 1);
            }
        }
    }

    /*@SubscribeEvent
    public static void BlockBreakEvents(BlockEvent.BreakEvent event) {
        ScreenShotHelper.saveScreenshot(Minecraft.getInstance().gameDir, Minecraft.getInstance().getMainWindow().getFramebufferWidth(), Minecraft.getInstance().getMainWindow().getFramebufferHeight(), Minecraft.getInstance().getFramebuffer(), (chatMessage) -> {
            Minecraft.getInstance().execute(() -> {
                Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(chatMessage);
            });
        });
    }*/

}
