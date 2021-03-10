package com.itayfeder.redstone_revolution.client.model.entities;// Made with Blockbench 3.8.0
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.itayfeder.redstone_revolution.common.entities.BombertEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BombertModel<T extends BombertEntity> extends EntityModel<T> {
	private final ModelRenderer body;
	private final ModelRenderer fuse_r1;
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftLeg2;

	public BombertModel() {
		textureWidth = 32;
		textureHeight = 32;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 17.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		body.setTextureOffset(16, 16).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 1.0F, 4.0F, 0.0F, false);

		fuse_r1 = new ModelRenderer(this);
		fuse_r1.setRotationPoint(0.0F, -4.0F, 0.0F);
		body.addChild(fuse_r1);
		setRotationAngle(fuse_r1, -0.4363F, 0.0F, 0.1309F);
		fuse_r1.setTextureOffset(12, 16).addBox(-0.5F, -2.7F, -0.5F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(2.5F, 22.0F, 0.5F);
		leftLeg.setTextureOffset(0, 16).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

		leftLeg2 = new ModelRenderer(this);
		leftLeg2.setRotationPoint(-2.5F, 22.0F, 0.25F);
		leftLeg2.setTextureOffset(0, 16).addBox(-1.0F, 0.0F, -1.75F, 2.0F, 2.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
		this.leftLeg2.rotateAngleX = MathHelper.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg.render(matrixStack, buffer, packedLight, packedOverlay);
		leftLeg2.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		if (entityIn.isEntitySleeping()) {
			this.leftLeg.rotateAngleZ = MathHelper.sin(30);
			this.leftLeg2.rotateAngleZ = MathHelper.sin(-30);
			this.body.rotationPointY = 18.0F;

		} else {
			this.leftLeg.rotateAngleZ = 0;
			this.leftLeg2.rotateAngleZ = 0;
			this.body.rotationPointY = 17.0F;

		}
	}
}