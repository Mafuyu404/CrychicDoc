package com.mna.entities.models.constructs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mna.entities.constructs.ConstructAssemblyStand;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class ConstructAssemblyStandModel extends ConstructAssemblyStandModelBase {

    private final ModelPart rightBodyStick;

    private final ModelPart leftBodyStick;

    private final ModelPart shoulderStick;

    private final ModelPart basePlate;

    public ConstructAssemblyStandModel(ModelPart modelPart0) {
        super(modelPart0);
        this.rightBodyStick = modelPart0.getChild("right_body_stick");
        this.leftBodyStick = modelPart0.getChild("left_body_stick");
        this.shoulderStick = modelPart0.getChild("shoulder_stick");
        this.basePlate = modelPart0.getChild("base_plate");
        this.f_102809_.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.offset(0.0F, 1.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 26).addBox(-6.0F, 0.0F, -1.5F, 12.0F, 3.0F, 3.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 16).mirror().addBox(0.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 11.0F, 2.0F), PartPose.offset(1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_body_stick", CubeListBuilder.create().texOffs(16, 0).addBox(-3.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_body_stick", CubeListBuilder.create().texOffs(48, 16).addBox(1.0F, 3.0F, -1.0F, 2.0F, 7.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("shoulder_stick", CubeListBuilder.create().texOffs(0, 48).addBox(-4.0F, 10.0F, -1.0F, 8.0F, 2.0F, 2.0F), PartPose.ZERO);
        partdefinition.addOrReplaceChild("base_plate", CubeListBuilder.create().texOffs(0, 32).addBox(-6.0F, 11.0F, -6.0F, 12.0F, 1.0F, 12.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void prepareMobModel(ConstructAssemblyStand constructAssemblyStand0, float float1, float float2, float float3) {
        this.basePlate.xRot = 0.0F;
        this.basePlate.yRot = (float) (Math.PI / 180.0) * -Mth.rotLerp(float3, constructAssemblyStand0.f_19859_, constructAssemblyStand0.m_146908_());
        this.basePlate.zRot = 0.0F;
    }

    @Override
    public void setupAnim(ConstructAssemblyStand constructAssemblyStand0, float float1, float float2, float float3, float float4, float float5) {
        super.setupAnim(constructAssemblyStand0, float1, float2, float3, float4, float5);
        this.f_102812_.visible = false;
        this.f_102811_.visible = false;
        this.basePlate.visible = true;
        this.rightBodyStick.xRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getX();
        this.rightBodyStick.yRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getY();
        this.rightBodyStick.zRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getZ();
        this.leftBodyStick.xRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getX();
        this.leftBodyStick.yRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getY();
        this.leftBodyStick.zRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getZ();
        this.shoulderStick.xRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getX();
        this.shoulderStick.yRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getY();
        this.shoulderStick.zRot = (float) (Math.PI / 180.0) * constructAssemblyStand0.getBodyPose().getZ();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.m_5608_(), ImmutableList.of(this.rightBodyStick, this.leftBodyStick, this.shoulderStick, this.basePlate));
    }

    @Override
    public void translateToHand(HumanoidArm humanoidArm0, PoseStack poseStack1) {
        ModelPart modelpart = this.m_102851_(humanoidArm0);
        boolean flag = modelpart.visible;
        modelpart.visible = true;
        super.m_6002_(humanoidArm0, poseStack1);
        modelpart.visible = flag;
    }
}