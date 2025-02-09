package com.felpslipe.testmod.item;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.item.custom.ThirtyItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MOD_ID);

    // Register new item (troll)
    public static final DeferredItem<Item> TROLL = ITEMS.register("troll",
            () -> new Item(new Item.Properties()));
    // Register new item (smiley)
    public static final DeferredItem<Item> SMILEY = ITEMS.register("smiley",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> THIRTY = ITEMS.register("thirty",
            () -> new ThirtyItem(new Item.Properties().durability(185)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.testmod.thirty"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FRANGO = ITEMS.register("frango",
            () -> new Item(new Item.Properties().food(ModFoodProperties.FRANGO)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.testmod.frango.shift_down"));
                    }
                    else {
                        tooltipComponents.add(Component.translatable("tooltip.testmod.frango"));
                    }
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> HOT_BREATH = ITEMS.register("hot_breath",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOT_CHEETO = ITEMS.register("hot_cheeto",
            () -> new Item(new Item.Properties()));

    // Registering items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
