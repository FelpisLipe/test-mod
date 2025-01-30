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


    // Registering items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
