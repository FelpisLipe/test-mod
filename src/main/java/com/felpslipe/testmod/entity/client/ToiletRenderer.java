package com.felpslipe.testmod.entity.client;

import com.felpslipe.testmod.entity.custom.ToiletEntity;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ToiletRenderer extends EntityRenderer<ToiletEntity> {
    public ToiletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(ToiletEntity entity) {
        return null;
    }

    @Override
    public boolean shouldRender(ToiletEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}
