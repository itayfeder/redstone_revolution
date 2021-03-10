package com.itayfeder.redstone_revolution.utils;

import com.itayfeder.redstone_revolution.core.init.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class RegistryHandler {
    public static void Init() {
        EntityInit.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        ItemInit.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockInit.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ProfessionInit.POINTS_OF_INTEREST.register(FMLJavaModLoadingContext.get().getModEventBus());
        ProfessionInit.PROFESSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntityInit.TILE_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
