package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.items.BombertItem;
import com.itayfeder.redstone_revolution.common.items.WrenchItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            RedstoneRevolutionMod.MOD_ID);

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new WrenchItem(new Item.Properties().group(ItemGroup.REDSTONE).maxStackSize(1)));

    public static final RegistryObject<Item> BLUESTONE = ITEMS.register("bluestone",
            () -> new BlockNamedItem(BlockInit.BLUESTONE_WIRE.get(), (new Item.Properties()).group(ItemGroup.REDSTONE)));

    public static final RegistryObject<Item> BOMBER_SPAWNER = ITEMS.register("bombert_spawner",
            () -> new BombertItem(new Item.Properties().group(ItemGroup.REDSTONE).maxStackSize(1)));

    //BLOCK ITEMS
    public static final RegistryObject<BlockItem> QUARTZ_PRESSURE_PLATE = ITEMS.register("quartz_pressure_plate",
            () -> new BlockItem(BlockInit.QUARTZ_PRESSURE_PLATE.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> REDSTONE_WATCHER = ITEMS.register("redstone_watcher",
            () -> new BlockItem(BlockInit.REDSTONE_WATCHER.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> WOODEN_VENTILATOR = ITEMS.register("wooden_ventilator",
            () -> new BlockItem(BlockInit.WOODEN_VENTILATOR.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> IRON_VENTILATOR = ITEMS.register("iron_ventilator",
            () -> new BlockItem(BlockInit.IRON_VENTILATOR.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> REDSTONE_RECIEVER = ITEMS.register("redstone_reciever",
            () -> new BlockItem(BlockInit.REDSTONE_RECIEVER.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> WIRELESS_LEVER = ITEMS.register("wireless_lever",
            () -> new BlockItem(BlockInit.WIRELESS_LEVER.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> GOLDEN_DOOR = ITEMS.register("golden_door",
            () -> new BlockItem(BlockInit.GOLDEN_DOOR.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> ROTATOR = ITEMS.register("rotator",
            () -> new BlockItem(BlockInit.ROTATOR.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

    public static final RegistryObject<BlockItem> RANDOMIZER = ITEMS.register("randomizer",
            () -> new BlockItem(BlockInit.RANDOMIZER.get(),
                    new Item.Properties().group(ItemGroup.REDSTONE)));

}
