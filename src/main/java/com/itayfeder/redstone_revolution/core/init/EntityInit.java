package com.itayfeder.redstone_revolution.core.init;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.common.entities.BombertEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RedstoneRevolutionMod.MOD_ID);

    public static final RegistryObject<EntityType<BombertEntity>> BOMBERT = ENTITY_TYPES.register("bombert",
            () -> EntityType.Builder.create(BombertEntity::new, EntityClassification.CREATURE)
                    .size(0.5f, 0.625f)
                    .build(new ResourceLocation(RedstoneRevolutionMod.MOD_ID, "bombert").toString()));
}
