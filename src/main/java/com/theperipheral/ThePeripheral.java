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
    private static final Random RANDOM = new Random();
    private int tickCounter = 0;

    @Override
    public void onInitialize() {
        LOGGER.info("The Peripheral: Production Horror System Activated.");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            
            // Har ~8 se 15 second me trigger hoga
            if (tickCounter >= 160 + RANDOM.nextInt(140)) {
                tickCounter = 0;

                server.getPlayerManager().getPlayerList().forEach(player -> {
                    if (player.isSpectator() || player.isCreative()) return;

                    BlockPos pos = player.getBlockPos();
                    int lightLevel = player.getWorld().getLightLevel(pos);

                    // Agar light kam hai (Underground / Night)
                    if (lightLevel < 6) {
                        // Player ke 2 blocks piche position calculate karo
                        BlockPos behindPos = pos.offset(player.getHorizontalFacing().getOpposite(), 2);
                        
                        // Random Creepy Sound Selector
                        float pitch = 0.3f + (RANDOM.nextFloat() * 0.3f); // Pitch low karke creepy aawaz
                        var soundEvent = RANDOM.nextBoolean() ? SoundEvents.BLOCK_DEEPSLATE_STEP : SoundEvents.ENTITY_GHAST_SCREAM;

                        player.getWorld().playSound(
                            null, 
                            behindPos, 
                            soundEvent, 
                            SoundCategory.PLAYERS, 
                            0.8f, 
                            pitch
                        );
                    }
                });
            }
        });
    }
}
