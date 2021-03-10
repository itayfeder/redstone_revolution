package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.tileentities.RedstoneRecieverTileEntity;
import com.itayfeder.redstone_revolution.common.tileentities.WirelessLeverTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES,
            RedstoneRevolutionMod.MOD_ID);

    public static final RegistryObject<TileEntityType<RedstoneRecieverTileEntity>> REDSTONE_RECIEVER_TILE = TILE_ENTITIES.register("redstone_reciever",
            () -> TileEntityType.Builder.create(RedstoneRecieverTileEntity::new, BlockInit.REDSTONE_RECIEVER.get()).build(null));

    public static final RegistryObject<TileEntityType<WirelessLeverTileEntity>> WIRELESS_LEVER_TILE = TILE_ENTITIES.register("wireless_lever",
            () -> TileEntityType.Builder.create(WirelessLeverTileEntity::new, BlockInit.WIRELESS_LEVER.get()).build(null));
}
