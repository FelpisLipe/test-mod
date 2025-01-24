package com.felpslipe.testmod.item;

import com.felpslipe.testmod.TestMod;
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


    // Registering items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
