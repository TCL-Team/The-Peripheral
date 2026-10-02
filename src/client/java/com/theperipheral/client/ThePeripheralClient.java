package com.theperipheral.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;

import java.util.Random;

public class ThePeripheralClient implements ClientModInitializer {
    private static final Random RANDOM = new Random();
    private int blackoutTicks = 0;

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            // Player agar spectating nahi kar raha
            if (client.player.isCreative() || client.player.isSpectator()) return;

            int light = client.world.getLightLevel(client.player.getBlockPos());

            // Jab andhera ho (Light < 4), toh 0.5% chance hai short glitch blackout hone ka
            if (light < 4 && RANDOM.nextInt(200) == 1) {
                blackoutTicks = 4; // 4 ticks black screen
            }

            if (blackoutTicks > 0) {
                blackoutTicks--;
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                // 1.21.11 Compatible ARGB Pitch Black Color Fill (0xFF000000 = Fully Opaque Black)
                drawContext.fill(0, 0, width, height, 0xFF000000);
            }
        });
    }
}
