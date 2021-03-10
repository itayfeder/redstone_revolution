package com.itayfeder.redstone_revolution.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class AvailableItemsConfig {
    public static ForgeConfigSpec.BooleanValue allow_wrench;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
    {
        server.comment("Available Items Config");

        allow_wrench = server
                .comment("Decide if you allow the Wrench item to appear in your world")
                .define("items.wrench", true);
    }
}
