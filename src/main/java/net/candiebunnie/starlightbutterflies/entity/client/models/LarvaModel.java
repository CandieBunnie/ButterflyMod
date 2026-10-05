package net.candiebunnie.starlightbutterflies.entity.client.models;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.candiebunnie.starlightbutterflies.entity.animations.LarvaAnimations;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class LarvaModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart Larva;
	private final ModelPart Head;

	public LarvaModel(ModelPart root) {
		this.Larva = root.getChild("Larva");
		this.Head = this.Larva.getChild("Head");
	}

	public static LayerDefinition createSolidBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Larva = partdefinition.addOrReplaceChild("Larva", CubeListBuilder.create(), PartPose.offset(2.0F, 24.0F, 10.0F));

		PartDefinition Head = Larva.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(20, 0).addBox(1.0F, -1.0F, -7.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(20, 0).addBox(-4.0F, -1.0F, -7.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -3.0F, -19.0F));

		PartDefinition LeftAntenna = Head.addOrReplaceChild("LeftAntenna", CubeListBuilder.create(), PartPose.offset(2.9F, -2.0F, -6.0F));

		PartDefinition left_antenna_r1 = LeftAntenna.addOrReplaceChild("left_antenna_r1", CubeListBuilder.create().texOffs(24, 19).addBox(0.0F, -9.0F, -1.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, -0.2182F, 0.0F));

		PartDefinition RightAntenna = Head.addOrReplaceChild("RightAntenna", CubeListBuilder.create(), PartPose.offset(2.9F, -2.0F, -6.0F));

		PartDefinition right_antenna_r1 = RightAntenna.addOrReplaceChild("right_antenna_r1", CubeListBuilder.create().texOffs(24, 19).addBox(0.0F, -9.0F, -1.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8F, 0.0F, 0.0F, 0.5672F, 0.2182F, 0.0F));

		PartDefinition BodySegment1 = Larva.addOrReplaceChild("BodySegment1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -18.0F));

		PartDefinition LeftFoot1 = BodySegment1.addOrReplaceChild("LeftFoot1", CubeListBuilder.create().texOffs(24, 19).addBox(-8.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition RightFoot1 = BodySegment1.addOrReplaceChild("RightFoot1", CubeListBuilder.create().texOffs(24, 19).addBox(-0.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition BodySegment2 = Larva.addOrReplaceChild("BodySegment2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition LeftFoot2 = BodySegment2.addOrReplaceChild("LeftFoot2", CubeListBuilder.create().texOffs(24, 19).addBox(-8.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition RightFoot2 = BodySegment2.addOrReplaceChild("RightFoot2", CubeListBuilder.create().texOffs(24, 19).addBox(-0.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition BodySegment3 = Larva.addOrReplaceChild("BodySegment3", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));

		PartDefinition LeftFoot3 = BodySegment3.addOrReplaceChild("LeftFoot3", CubeListBuilder.create().texOffs(24, 19).addBox(-8.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition RightFoot3 = BodySegment3.addOrReplaceChild("RightFoot3", CubeListBuilder.create().texOffs(24, 19).addBox(-0.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition BodySegment4 = Larva.addOrReplaceChild("BodySegment4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftFoot4 = BodySegment4.addOrReplaceChild("LeftFoot4", CubeListBuilder.create().texOffs(24, 19).addBox(-8.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		PartDefinition RightFoot4 = BodySegment4.addOrReplaceChild("RightFoot4", CubeListBuilder.create().texOffs(24, 19).addBox(-0.9F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createOuterBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Larva = partdefinition.addOrReplaceChild("Larva", CubeListBuilder.create(), PartPose.offset(2.0F, 24.0F, 10.0F));

		PartDefinition Head = Larva.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 20).addBox(-3.0F, -3.0F, -6.0F, 6.2F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(-2.0F, -3.0F, -19.0F));

		PartDefinition BodySegment1 = Larva.addOrReplaceChild("BodySegment1", CubeListBuilder.create().texOffs(0, 8).addBox(-5.0F, -6.0F, -1.1F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, -18.0F));

		PartDefinition BodySegment2 = Larva.addOrReplaceChild("BodySegment2", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition BodySegment3 = Larva.addOrReplaceChild("BodySegment3", CubeListBuilder.create().texOffs(0, 8).addBox(-5.0F, -6.0F, -1.1F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, -6.0F));

		PartDefinition BodySegment4 = Larva.addOrReplaceChild("BodySegment4", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -6.0F, -1.2F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(LarvaAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((LarvaEntity) entity).idleAnimationState, LarvaAnimations.idle, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Larva.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Larva;
	}

}