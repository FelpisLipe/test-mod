package com.felpslipe.testmod.item;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.block.ModBlocks;
import com.felpslipe.testmod.item.custom.HammerItem;
import com.felpslipe.testmod.item.custom.ThirtyItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
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
    public static final DeferredItem<SwordItem> SMILEY_SWORD = ITEMS.register("smiley_sword",
            () -> new SwordItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SMILEY, 3, -2.4f))));
    public static final DeferredItem<PickaxeItem> SMILEY_PICKAXE = ITEMS.register("smiley_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SMILEY, 1.5f, -2.8f))));
    public static final DeferredItem<AxeItem> SMILEY_AXE = ITEMS.register("smiley_axe",
            () -> new AxeItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SMILEY, 5, -3f))));
    public static final DeferredItem<ShovelItem> SMILEY_SHOVEL = ITEMS.register("smiley_shovel",
            () -> new ShovelItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SMILEY, 2, -3f))));
    public static final DeferredItem<HoeItem> SMILEY_HOE = ITEMS.register("smiley_hoe",
            () -> new HoeItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SMILEY, -3, 0f))));
    public static final DeferredItem<HammerItem> SMILEY_HAMMER = ITEMS.register("smiley_hammer",
            () -> new HammerItem(ModToolTiers.SMILEY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SMILEY, 7f, -3.5f))));
    public static final DeferredItem<ArmorItem> SMILEY_HELMET = ITEMS.register("smiley_helmet",
            () -> new ArmorItem(ModArmorMaterials.SMILEY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
        public static final DeferredItem<ArmorItem> SMILEY_CHESTPLATE = ITEMS.register("smiley_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SMILEY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(19))));
        public static final DeferredItem<ArmorItem> SMILEY_LEGGINGS = ITEMS.register("smiley_leggings",
            () -> new ArmorItem(ModArmorMaterials.SMILEY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(19))));
        public static final DeferredItem<ArmorItem> SMILEY_BOOTS = ITEMS.register("smiley_boots",
            () -> new ArmorItem(ModArmorMaterials.SMILEY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(19))));
        public static final DeferredItem<Item> FRANGO_SEEDS = ITEMS.register("frango_seeds",
                () -> new ItemNameBlockItem(ModBlocks.FRANGO_CROP.get(), new Item.Properties()));


    // Registering items
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
