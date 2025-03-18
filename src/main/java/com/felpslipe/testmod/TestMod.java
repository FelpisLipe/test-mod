package com.felpslipe.testmod;

import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.block.client.CabelaSkullModel;
import com.felpslipe.testmod.block.custom.CabelaSkullBlock;
import com.felpslipe.testmod.block.entity.ModBlockEntities;
import com.felpslipe.testmod.block.entity.renderer.PedestalBlockEntityRenderer;
import com.felpslipe.testmod.entity.CabelaVariant;
import com.felpslipe.testmod.entity.ModEntities;
import com.felpslipe.testmod.entity.client.CabelaRenderer;
import com.felpslipe.testmod.entity.client.ToiletRenderer;
import com.felpslipe.testmod.hud.Hud;
import com.felpslipe.testmod.item.ModCreativeModeTabs;
import com.felpslipe.testmod.item.ModItems;
import com.felpslipe.testmod.screen.ModMenuTypes;
import com.felpslipe.testmod.screen.custom.PedestalScreen;
import com.felpslipe.testmod.sound.ModSounds;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.SkullBlock;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TestMod.MOD_ID)
public class TestMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "testmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public TestMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Calling creative mode tabs register
        ModCreativeModeTabs.register(modEventBus);

        // Calling item register
        ModItems.register(modEventBus);

        // Calling block register
        ModBlocks.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


        // Test hud shit
        Hud.register(modEventBus);

        // Sound register
        ModSounds.register(modEventBus);

        // Entities register
        ModEntities.register(modEventBus);

        // Block Entities
        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Adding items to creative mode tab Ingredients
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.TROLL);
            event.accept(ModItems.SMILEY);
        }

        // Adding blocks to creative mode tab Building Blocks
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.TROLL_BLOCK);
        }

        // Adding blocks to creative mode tab Natural Blocks
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.SMILEY_ORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.CABELA.get(), CabelaRenderer::new);            EntityRenderers.register(ModEntities.TOILET_ENTITY.get(), ToiletRenderer::new);


            event.enqueueWork(() -> {
                ImmutableMap.Builder<SkullBlock.Type, ResourceLocation> builder = ImmutableMap.builder();
                builder.put(CabelaVariant.NORMAL, CabelaVariant.NORMAL.getResourceLocation());
                builder.put(CabelaVariant.CRY, CabelaVariant.CRY.getResourceLocation());
                SkullBlockRenderer.SKIN_BY_TYPE.putAll(builder.build());
            });
            EntityRenderers.register(ModEntities.TOILET_ENTITY.get(), ToiletRenderer::new);

        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(CabelaSkullModel.CABELA_HEAD, CabelaSkullModel::createCabelaHeadLayer);
        }

        @SubscribeEvent
        public static void onCreateSkullModel(EntityRenderersEvent.CreateSkullModels event) {
            event.registerSkullModel(CabelaVariant.NORMAL, new CabelaSkullModel(event.getEntityModelSet().bakeLayer(CabelaSkullModel.CABELA_HEAD)));
            event.registerSkullModel(CabelaVariant.CRY, new CabelaSkullModel(event.getEntityModelSet().bakeLayer(CabelaSkullModel.CABELA_HEAD)));
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.PEDESTAL_MENU.get(), PedestalScreen::new);
        }
    }
}
