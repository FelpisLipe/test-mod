package com.felpslipe.testmod.entity.client;

import com.felpslipe.testmod.entity.CabelaVariant;
import com.felpslipe.testmod.entity.custom.CabelaEntity;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CabelaRenderer extends MobRenderer<CabelaEntity, CabelaModel<CabelaEntity>> {
    private static final Map<CabelaVariant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(CabelaVariant.class), map -> {
        map.put(CabelaVariant.NORMAL, CabelaVariant.NORMAL.getResourceLocation());
        map.put(CabelaVariant.CRY, CabelaVariant.CRY.getResourceLocation());
    });


    public CabelaRenderer(EntityRendererProvider.Context context) {
        super(context, new CabelaModel<>(context.bakeLayer(CabelaModel.CABELA)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CabelaEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
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
