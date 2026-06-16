package com.felpslipe.testmod.entity.client;

import com.felpslipe.testmod.entity.custom.CabelaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CabelaRenderer extends MobRenderer<CabelaEntity, CabelaModel<CabelaEntity>> {
    public CabelaRenderer(EntityRendererProvider.Context context) {
        super(context, new CabelaModel<>(context.bakeLayer(CabelaModel.CABELA)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CabelaEntity entity) {
        return entity.getVariant().getResourceLocation();
    }

    @Override
    public void render(CabelaEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float ageScale = entity.getAgeScale();
        poseStack.scale(ageScale, ageScale, ageScale);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
