package com.felpslipe.testmod.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

public class Hud {
    public static final int WHITE = 0xffffff;
    public static final int OUTLINE_COLOR = 0x000000;
    public static final int TRANSPARENT = -1; //cccccccccccccccccccccccccccccccccccccccccccccccccc

    static int xPos = 2;
    static int yPos = 2;
    static int xMovement = 1;
    static int yMovement = 1;

    public static boolean enabled = false;

    private static void eventRenderGameOverlayEvent(RenderGuiEvent.Post event) {
        onRenderHUD(event.getGuiGraphics(), event.getPartialTick());
    }

    public static void onRenderHUD(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();



        if (mc.getDebugOverlay().showDebugScreen()) {
            return;
        }
        if (mc.options.hideGui) {
            return;
        }
        if (!enabled) {
            return;
        }
        Font textRenderer = mc.font;
        LocalPlayer player = mc.player;

        int xPadding = 2;
        int yPadding = 2;
        int lineSpacing = 10;

        int xGlobalOffset = 1 + xPadding;
        int yOffset = 1 + yPadding;
        if (player == null) {
            return;
        }

        Component myComponent = Component.literal("My mod Hud component!");


        int screenWidth = guiGraphics.guiWidth(); // IDK how to get
        int screenHeight = guiGraphics.guiHeight(); // IDK how to get

        int coordinatesWidth = textRenderer.width(myComponent.getString()) + xPadding;

        if ((xMovement > 0 && xPos + xMovement + coordinatesWidth >= screenWidth) || (xMovement < 0 && xPos + xMovement <= xPadding)) {
            xMovement *= -1;
        }
        if ((yMovement > 0 && yPos + yMovement + lineSpacing >= screenHeight) || (yMovement < 0 && yPos + yMovement <= yPadding)) {
            yMovement *= -1;
        }

        xPos += xMovement;
        yPos += yMovement;

        // Show text here with xPos and yPos

        text(guiGraphics, textRenderer, myComponent , xPos, yPos, WHITE, OUTLINE_COLOR);
    }

    private static void text(GuiGraphics context, Font font, Component message, int x, int y, int color, int outlineColor) {
        if (outlineColor == TRANSPARENT) {
            context.drawString(font, message, x, y, color, false);
            return;
        }

        font.drawInBatch8xOutline(message.getVisualOrderText(), x, y, color, outlineColor, context.pose().last().pose(), context.bufferSource(), 15728880);
        context.flush();
    }

    public static void toggle() {
        enabled = !enabled;
    }
    public static final KeyMapping TOGGLE_HUD =
            new KeyMapping("key.testmod.toggle_hud", GLFW.GLFW_KEY_G, "key.categories.testmod.hud");

    public static void eventClientTickEventPost(ClientTickEvent.Post event) {
        onClientTick();
    }
    public static void onClientTick() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        while (TOGGLE_HUD.consumeClick()) {
            if (!player.isShiftKeyDown() ) {
                toggle();
            }
        }
    }

    public static void register(IEventBus eventBus) {
        NeoForge.EVENT_BUS.addListener(Hud::eventRenderGameOverlayEvent);
        NeoForge.EVENT_BUS.addListener(Hud::eventClientTickEventPost);
        eventBus.addListener(Hud::registerBindings);
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_HUD);
    }


}
