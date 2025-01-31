package com.felpslipe.testmod.item;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.item.custom.ThirtyItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TestMod.MOD_ID);

    // Register new item (troll)
    public static final DeferredItem<Item> TROLL = ITEMS.register("troll",
            () -> new Item(new Item.Properties()));
    // Register new item (smiley)
    public static final DeferredItem<Item> SMILEY = ITEMS.register("smiley",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> THIRTY = ITEMS.register("thirty",
            () -> new ThirtyItem(new Item.Properties().durability(185)));

    public static final DeferredItem<Item> FRANGO = ITEMS.register("frango",
            () -> new Item(new Item.Properties().food(ModFoodProperties.FRANGO)));

    public static final DeferredItem<Item> HOT_BREATH = ITEMS.register("hot_breath",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> HOT_CHEETO = ITEMS.register("hot_cheeto",
            () -> new Item(new Item.Properties()));

    // Registering items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
