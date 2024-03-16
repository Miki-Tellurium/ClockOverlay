package com.mikitellurium.clockoverlay.event;

import com.mikitellurium.clockoverlay.client.rendering.OverlayRenderer;
import com.mikitellurium.telluriumforge.event.EventHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class ModEvents {

    private static final EventHelper HELPER = new EventHelper();

    public static void register() {
        HELPER.addListener(HudRenderCallback.EVENT, OverlayRenderer::renderOverlay).registerAll();
    }

}
