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
        LOGGER.info("The Peripheral Mod Initialized Successfully!");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;

            // Har 8 se 15 second ke beech me sound trigger hoga
            if (tickCounter >= 160 + RANDOM.nextInt(140)) {
                tickCounter = 0;

                server.getPlayerManager().getPlayerList().forEach(player -> {
                    if (player.isSpectator() || player.isCreative()) return;

                    BlockPos pos = player.getBlockPos();
                    int lightLevel = player.getWorld().getLightLevel(pos);

                    // Agar andhera ho (Light < 6)
                    if (lightLevel < 6) {
                        BlockPos behindPos = pos.offset(player.getHorizontalFacing().getOpposite(), 2);
                        
                        player.getWorld().playSound(
                            null,
                            behindPos,
                            SoundEvents.ENTITY_GHAST_SCREAM,
                            SoundCategory.PLAYERS,
                            0.6f,
                            0.3f // Pitch low karke creepy banaya gaya hai
                        );
                    }
                });
            }
        });
    }
}
