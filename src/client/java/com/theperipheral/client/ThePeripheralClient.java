package com.theperipheral.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Colors;

public class ThePeripheralClient implements ClientModInitializer {
    private int darknessTicks = 0;

    @Override
    public void onInitializeClient() {
        // Overlay Render: Kabhi-kabhi screen par 0.1 second ka full black glitch laane ke liye
        HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            // Light level threshold check
            int light = client.world.getLightLevel(client.player.getBlockPos());
            
            if (light < 4 && client.world.random.nextInt(500) == 1) {
                darknessTicks = 3; // 3 ticks blackout
            }

            if (darknessTicks > 0) {
                darknessTicks--;
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();
                // Screen ko pitch black kar do
                drawContext.fill(0, 0, width, height, Colors.BLACK);
            }
        });
    }
}
