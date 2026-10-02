package com.theperipheral;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ThePeripheral implements ModInitializer {
    public static final String MOD_ID = "the-peripheral";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private final Random random = new Random();
    private int tickCounter = 0;

    @Override
    public void onInitialize() {
        LOGGER.info("The Peripheral Mod Loading... Tayyar raho darr ke liye!");

        // Server Tick Event: Har 5-10 second me piche footsteps bajaye ga
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= 100 + random.nextInt(200)) { // 5 to 15 seconds
                tickCounter = 0;
                server.getPlayerManager().getPlayerList().forEach(player -> {
                    // Agar player dark area ya cave me hai
                    if (player.getWorld().getLightLevel(player.getBlockPos()) < 7) {
                        // Player ke 2 blocks piche sound bajao
                        BlockPos behindPos = player.getBlockPos().offset(player.getHorizontalFacing().getOpposite(), 2);
                        player.getWorld().playSound(
                            null, 
                            behindPos, 
                            SoundEvents.BLOCK_DEEPSLATE_STEP, 
                            SoundCategory.PLAYERS, 
                            1.0f, 
                            0.5f // Low pitch creepiness ke liye
                        );
                    }
                });
            }
        });
    }
}
