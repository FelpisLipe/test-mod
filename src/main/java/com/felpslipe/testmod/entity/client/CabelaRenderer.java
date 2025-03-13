package com.felpslipe.testmod.entity.client;

import com.felpslipe.testmod.TestMod;
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
        map.put(CabelaVariant.NORMAL, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/cabela/cabela_normal.png"));
        map.put(CabelaVariant.CRY, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "textures/entity/cabela/cabela_cry.png"));
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
        if(entity.isBaby()) {
            poseStack.pushPose();
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if(entity.isBaby()) {
            poseStack.popPose();
        }
    }
}
