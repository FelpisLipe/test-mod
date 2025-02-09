package com.felpslipe.testmod.item.custom;

import com.felpslipe.testmod.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class ThirtyItem extends Item {
    private static final Map<Block, Block> THIRTY_MAP =
            Map.of(
                    Blocks.STONE, Blocks.END_STONE,
                    Blocks.END_STONE_BRICKS, Blocks.BEDROCK,
                    Blocks.DEEPSLATE, Blocks.SCULK_SHRIEKER,
                    Blocks.OXIDIZED_COPPER, Blocks.COPPER_BLOCK,
                    Blocks.ACACIA_DOOR, ModBlocks.TROLL_BLOCK.get()
            );


    public ThirtyItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(THIRTY_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), THIRTY_MAP.get(clickedBlock).defaultBlockState());

                context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null, context.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);
            }

        }

        return InteractionResult.SUCCESS;
    }

}
