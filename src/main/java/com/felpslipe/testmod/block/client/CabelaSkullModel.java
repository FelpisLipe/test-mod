package com.felpslipe.testmod.block.client;

import com.felpslipe.testmod.TestMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CabelaSkullModel extends SkullModelBase implements IWallShiftSkullModel {
    public static final ModelLayerLocation CABELA_HEAD = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "cabela_head"), "main");

    private final ModelPart root;

    public CabelaSkullModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createCabelaHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("cubeOut", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5f, -7.5f, -3.5f, 8.5f, 8.5f, 8.5f), PartPose.ZERO);
        partdefinition.addOrReplaceChild("cubeIn", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 32);
    }




    @Override
    public void setupAnim(float mouthAnimation, float yRot, float xRot) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {

    }

    @Override
    public float getWallSkullZShift() {
        return 0;
    }

}
