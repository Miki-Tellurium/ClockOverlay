package com.mikitellurium.clockoverlay.event;

import com.mikitellurium.clockoverlay.client.ClockOverlayRenderer;
import com.mikitellurium.clockoverlay.clock.TickedClock;
import com.mikitellurium.telluriumforge.event.EventHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ModEvents {

    private static final EventHelper HELPER = new EventHelper();

    public static void register() {
        HELPER
                .addListener(HudRenderCallback.EVENT, ClockOverlayRenderer::renderOverlay)
                .addListener(ClientTickEvents.START_CLIENT_TICK, (event) -> TickedClock.INSTANCE.tick())
                .registerAll();
    }

}
