package com.mikitellurium.clockoverlay;

import com.mikitellurium.clockoverlay.client.rendering.OverlayRenderer;
import com.mikitellurium.clockoverlay.config.Configuration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClockOverlay.LOGGER.info("Starting client");
        HudRenderCallback.EVENT.register(OverlayRenderer::renderOverlay);
        Configuration.registerConfig();
    }

}
