package com.itayfeder.redstone_revolution.client.render.entities;

import com.itayfeder.redstone_revolution.RedstoneRevolutionMod;
import com.itayfeder.redstone_revolution.client.model.entities.BombertModel;
import com.itayfeder.redstone_revolution.common.entities.BombertEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BombertRenderer extends MobRenderer<BombertEntity, BombertModel<BombertEntity>> {
    private static final ResourceLocation DEFAULT = new ResourceLocation(RedstoneRevolutionMod.MOD_ID, "textures/entity/bombert/default.png");
    private static final ResourceLocation OFFLINE = new ResourceLocation(RedstoneRevolutionMod.MOD_ID, "textures/entity/bombert/offline.png");

    public BombertRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BombertModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(BombertEntity entity) {
        return entity.isEntitySleeping() ? OFFLINE : DEFAULT;
    }
}
