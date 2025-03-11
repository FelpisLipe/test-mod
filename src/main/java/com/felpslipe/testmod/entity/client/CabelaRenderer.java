package com.felpslipe.testmod.entity.client;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.entity.custom.CabelaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CabelaRenderer extends MobRenderer<CabelaEntity, CabelaModel<CabelaEntity>> {


    public CabelaRenderer(EntityRendererProvider.Context context) {
        super(context, new CabelaModel<>(context.bakeLayer(CabelaModel.CABELA)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CabelaEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/cabela/cabela_normal.png");
    }

    @Override
    public void render(CabelaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if(entity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
