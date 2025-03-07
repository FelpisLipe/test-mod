package com.felpslipe.testmod.event;

import com.felpslipe.testmod.TestMod;
import com.felpslipe.testmod.item.custom.HammerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@EventBusSubscriber(modid = TestMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ModEvents.class);

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos originPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(originPos)) {
                LOGGER.info("Exiting hammer block break event");
                return;
            }

            LOGGER.info("Starting breaking blocks from {}", originPos);
            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, originPos, serverPlayer)) {
                if(pos.equals(originPos) || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    LOGGER.info("Skipping destroying block {}", pos);
                    continue;
                }

                LOGGER.info("Destroying block {}", pos);
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
            LOGGER.info("Ended breaking blocks from {}", originPos);
        }
    }

}
