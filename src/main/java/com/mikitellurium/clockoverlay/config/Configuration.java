package com.mikitellurium.clockoverlay.config;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.util.ScreenPos;

public class Configuration {

    public static final TelluriumConfig.Builder CLIENT_CONFIG = new TelluriumConfig.Builder("clockoverlay");

    public static TelluriumConfig.ConfigEntry<String> CLOCK_POSITION;
    public static TelluriumConfig.ConfigEntry<Boolean> CLOCK_HOLDING_REQUIRED;
    public static TelluriumConfig.ConfigEntry<Boolean> BIG_CLOCK;

    public static void registerConfig() {
        CLIENT_CONFIG.comment("Configuration file for clock overlay");

        CLOCK_POSITION = CLIENT_CONFIG
                .define("clockPosition", ScreenPos.OVER_INVENTORY.toString())
                .comment("The position of the clock overlay on screen");

        CLOCK_HOLDING_REQUIRED = CLIENT_CONFIG
                .define("clockHoldingRequired", true)
                .comment("If true holding a clock item is required to show the overlay,\n" +
                        "# if false having a clock in the player inventory is required");

        BIG_CLOCK = CLIENT_CONFIG
                .define("enableBigClock", false)
                .comment("BIG CLOCK!");

        CLIENT_CONFIG.build();
        ClockOverlay.LOGGER.info("Registered configuration");
    }

}
