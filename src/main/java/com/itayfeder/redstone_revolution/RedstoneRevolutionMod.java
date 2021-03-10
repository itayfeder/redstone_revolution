package com.itayfeder.redstone_revolution;

import com.itayfeder.redstone_revolution.client.render.entities.BombertRenderer;
import com.itayfeder.redstone_revolution.common.entities.BombertEntity;
import com.itayfeder.redstone_revolution.config.RedstoneRevolution;
import com.itayfeder.redstone_revolution.core.init.BlockInit;
import com.itayfeder.redstone_revolution.core.init.EntityInit;
import com.itayfeder.redstone_revolution.utils.RegistryHandler;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("redstone_revolution")
public class RedstoneRevolutionMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "redstone_revolution";

    public RedstoneRevolutionMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::doClientStuff);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, RedstoneRevolution.SERVER, "redstone_revolution-server.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, RedstoneRevolution.CLIENT, "redstone_revolution-client.toml");

        RedstoneRevolution.loadConfig(RedstoneRevolution.CLIENT, FMLPaths.CONFIGDIR.get().resolve("redstone_revolution-client.toml").toString());
        RedstoneRevolution.loadConfig(RedstoneRevolution.SERVER, FMLPaths.CONFIGDIR.get().resolve("redstone_revolution-server.toml").toString());

        RegistryHandler.Init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(EntityInit.BOMBERT.get(), BombertEntity.setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockInit.BLUESTONE_WIRE.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.REDSTONE_RECIEVER.get(), RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(BlockInit.ROTATOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.RANDOMIZER.get(), RenderType.getCutoutMipped());

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BOMBERT.get(), BombertRenderer::new);

    }
}
