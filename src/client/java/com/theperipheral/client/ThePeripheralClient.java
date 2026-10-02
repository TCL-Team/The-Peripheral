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

            if (client.player.isCreative() || client.player.isSpectator()) return;

            int light = client.world.getLightLevel(client.player.getBlockPos());

            // Dark area me Random Blackout flicker
            if (light < 4 && RANDOM.nextInt(200) == 1) {
                blackoutTicks = 3;
            }

            if (blackoutTicks > 0) {
                blackoutTicks--;
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                // 1.21.11 Compatible ARGB Pitch Black Screen Fill
                drawContext.fill(0, 0, width, height, 0xFF000000);
            }
        });
    }
}
