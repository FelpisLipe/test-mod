package com.felpslipe.testmod.event;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.entity.ModEntities;
import com.felpslipe.testmod.entity.client.CabelaModel;
import com.felpslipe.testmod.entity.custom.CabelaEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = TestMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CabelaModel.CABELA, CabelaModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CABELA.get(), CabelaEntity.createAttributes().build());
    }
}
