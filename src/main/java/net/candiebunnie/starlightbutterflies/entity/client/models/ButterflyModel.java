package net.candiebunnie.starlightbutterflies.entity.client.models;// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.candiebunnie.starlightbutterflies.entity.animations.ButterflyAnimations;
import net.candiebunnie.starlightbutterflies.entity.animations.LarvaAnimations;
import net.candiebunnie.starlightbutterflies.entity.custom.ButterflyEntity;
import net.candiebunnie.starlightbutterflies.entity.custom.LarvaEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class ButterflyModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart Butterfly;
	private final ModelPart Head;
	private final ModelPart LeftAntenna;
	private final ModelPart RightAntenna;
	private final ModelPart Thorax;
	private final ModelPart Fluff;
	private final ModelPart Legs;
	private final ModelPart RightLeg1;
	private final ModelPart RightLeg2;
	private final ModelPart RightLeg3;
	private final ModelPart LeftLeg3;
	private final ModelPart LeftLeg2;
	private final ModelPart LeftLeg1;
	private final ModelPart Wings;
	private final ModelPart RightWing;
	private final ModelPart LeftWing;
	private final ModelPart Abdomen;

	public ButterflyModel(ModelPart root) {
		this.Butterfly = root.getChild("Butterfly");
		this.Head = this.Butterfly.getChild("Head");
		this.LeftAntenna = this.Head.getChild("LeftAntenna");
		this.RightAntenna = this.Head.getChild("RightAntenna");
		this.Thorax = this.Butterfly.getChild("Thorax");
		this.Fluff = this.Thorax.getChild("Fluff");
		this.Legs = this.Thorax.getChild("Legs");
		this.RightLeg1 = this.Legs.getChild("RightLeg1");
		this.RightLeg2 = this.Legs.getChild("RightLeg2");
		this.RightLeg3 = this.Legs.getChild("RightLeg3");
		this.LeftLeg3 = this.Legs.getChild("LeftLeg3");
		this.LeftLeg2 = this.Legs.getChild("LeftLeg2");
		this.LeftLeg1 = this.Legs.getChild("LeftLeg1");
		this.Wings = this.Thorax.getChild("Wings");
		this.RightWing = this.Wings.getChild("RightWing");
		this.LeftWing = this.Wings.getChild("LeftWing");
		this.Abdomen = this.Butterfly.getChild("Abdomen");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Butterfly = partdefinition.addOrReplaceChild("Butterfly", CubeListBuilder.create(), PartPose.offset(5.0F, 9.0F, -2.0F));

		PartDefinition Head = Butterfly.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -6.0F, -7.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(2, 19).addBox(1.0F, -4.0F, -8.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(2, 19).addBox(-5.0F, -4.0F, -8.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 3.0F, -5.0F));

		PartDefinition LeftAntenna = Head.addOrReplaceChild("LeftAntenna", CubeListBuilder.create(), PartPose.offset(3.9F, -5.0F, -7.0F));

		PartDefinition left_antenna_r1 = LeftAntenna.addOrReplaceChild("left_antenna_r1", CubeListBuilder.create().texOffs(36, -3).addBox(0.0F, -9.0F, -1.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, -0.2182F, 0.0F));

		PartDefinition RightAntenna = Head.addOrReplaceChild("RightAntenna", CubeListBuilder.create(), PartPose.offset(-3.9F, -5.0F, -7.0F));

		PartDefinition right_antenna_r1 = RightAntenna.addOrReplaceChild("right_antenna_r1", CubeListBuilder.create().texOffs(36, -3).addBox(0.0F, -9.0F, -1.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.2182F, 0.0F));

		PartDefinition Thorax = Butterfly.addOrReplaceChild("Thorax", CubeListBuilder.create(), PartPose.offset(-5.0F, 5.0F, 7.0F));

		PartDefinition thorax_r1 = Thorax.addOrReplaceChild("thorax_r1", CubeListBuilder.create().texOffs(35, 0).addBox(-9.0F, -8.0F, -1.0F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.0F, -11.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition Fluff = Thorax.addOrReplaceChild("Fluff", CubeListBuilder.create(), PartPose.offset(2.0F, 4.0F, -13.0F));

		PartDefinition fluff_2_r1 = Fluff.addOrReplaceChild("fluff_2_r1", CubeListBuilder.create().texOffs(46, 23).addBox(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition fluff_1_r1 = Fluff.addOrReplaceChild("fluff_1_r1", CubeListBuilder.create().texOffs(68, 23).addBox(-11.0F, -10.0F, -1.0F, 12.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, 1.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition Legs = Thorax.addOrReplaceChild("Legs", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, -8.0F));

		PartDefinition RightLeg1 = Legs.addOrReplaceChild("RightLeg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_leg_1_r1 = RightLeg1.addOrReplaceChild("right_leg_1_r1", CubeListBuilder.create().texOffs(70, 0).addBox(0.1F, -1.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, 0.7418F, -0.2618F));

		PartDefinition RightLeg2 = Legs.addOrReplaceChild("RightLeg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition right_leg_2_r1 = RightLeg2.addOrReplaceChild("right_leg_2_r1", CubeListBuilder.create().texOffs(70, 0).addBox(0.1F, -1.0F, 1.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.8727F, 0.3491F));

		PartDefinition RightLeg3 = Legs.addOrReplaceChild("RightLeg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition right_leg_3_r1 = RightLeg3.addOrReplaceChild("right_leg_3_r1", CubeListBuilder.create().texOffs(70, 0).addBox(1.1F, -1.0F, -1.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.2618F));

		PartDefinition LeftLeg3 = Legs.addOrReplaceChild("LeftLeg3", CubeListBuilder.create(), PartPose.offset(10.0F, 0.0F, 6.0F));

		PartDefinition left_leg_3_r1 = LeftLeg3.addOrReplaceChild("left_leg_3_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-2.1F, -1.0F, -1.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, -0.2618F));

		PartDefinition LeftLeg2 = Legs.addOrReplaceChild("LeftLeg2", CubeListBuilder.create(), PartPose.offset(10.0F, 0.0F, 3.0F));

		PartDefinition left_leg_2_r1 = LeftLeg2.addOrReplaceChild("left_leg_2_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-1.1F, -1.0F, 1.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.8727F, -0.3491F));

		PartDefinition LeftLeg1 = Legs.addOrReplaceChild("LeftLeg1", CubeListBuilder.create(), PartPose.offset(10.0F, 0.0F, 0.0F));

		PartDefinition left_leg_1_r1 = LeftLeg1.addOrReplaceChild("left_leg_1_r1", CubeListBuilder.create().texOffs(70, 0).addBox(-1.1F, -1.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5672F, -0.7418F, 0.2618F));

		PartDefinition Wings = Thorax.addOrReplaceChild("Wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition RightWing = Wings.addOrReplaceChild("RightWing", CubeListBuilder.create(), PartPose.offset(-5.0F, -5.0F, -7.0F));

		PartDefinition right_hindwing_r1 = RightWing.addOrReplaceChild("right_hindwing_r1", CubeListBuilder.create().texOffs(64, 64).addBox(-63.0F, 1.0F, -21.0F, 64.0F, 0.0F, 64.0F, new CubeDeformation(0.0F))
				.texOffs(64, 0).addBox(-63.0F, 0.0F, -21.0F, 64.0F, 0.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3072F, 0.2377F, -0.1016F));

		PartDefinition LeftWing = Wings.addOrReplaceChild("LeftWing", CubeListBuilder.create(), PartPose.offset(5.0F, -5.0F, -7.0F));

		PartDefinition left_hindwing_r1 = LeftWing.addOrReplaceChild("left_hindwing_r1", CubeListBuilder.create().texOffs(64, 64).mirror().addBox(-1.0F, 1.0F, -21.0F, 64.0F, 0.0F, 64.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(64, 0).mirror().addBox(-1.0F, 0.0F, -21.0F, 64.0F, 0.0F, 64.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3072F, -0.2377F, 0.1016F));

		PartDefinition Abdomen = Butterfly.addOrReplaceChild("Abdomen", CubeListBuilder.create(), PartPose.offset(-5.0F, 6.0F, 8.0F));

		PartDefinition abdomen2_r1 = Abdomen.addOrReplaceChild("abdomen2_r1", CubeListBuilder.create().texOffs(103, 22).addBox(-5.0F, -5.0F, 7.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 4.0F, 3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition abdomen1_r1 = Abdomen.addOrReplaceChild("abdomen1_r1", CubeListBuilder.create().texOffs(85, 0).addBox(-7.0F, -7.0F, -1.0F, 8.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 4.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if(entity instanceof ButterflyEntity butterfly) {
            this.Fluff.visible = butterfly.hasFluff();
			applyWingRotation(butterfly.getWingRotation());
		}

		applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		if(entity.onGround()){
			this.animateWalk(ButterflyAnimations.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
		} else {
			this.animateWalk(ButterflyAnimations.fly, limbSwing, limbSwingAmount, 2f, 2.5f);
		}
		this.animate(((ButterflyEntity) entity).idleAnimationState, ButterflyAnimations.idle, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.Head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.Head.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	private void applyWingRotation(float[] pWingRotation) {
		pWingRotation[0] = Mth.clamp(pWingRotation[0], -5.0F, 20.0F);
		pWingRotation[1] = Mth.clamp(pWingRotation[1], -80.0F, 0.0F);

		this.LeftWing.zRot = pWingRotation[1] * ((float)Math.PI / 180F);
		this.RightWing.zRot = 0-pWingRotation[1] * ((float)Math.PI / 180F);
		this.LeftWing.yRot = pWingRotation[0] * ((float)Math.PI / 180F);
		this.RightWing.yRot = 0-pWingRotation[0] * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Butterfly.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return Butterfly;
	}
}