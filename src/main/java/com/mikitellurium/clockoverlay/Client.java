package com.mikitellurium.clockoverlay;

import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.event.ModEvents;
import net.fabricmc.api.ClientModInitializer;

public class Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClockOverlay.logger().info("Starting client");
        ModEvents.register();
        Configuration.registerConfig();
    }

}
