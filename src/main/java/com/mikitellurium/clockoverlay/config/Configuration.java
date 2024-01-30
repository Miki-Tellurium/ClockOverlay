package com.mikitellurium.clockoverlay.config;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.util.OverlayPos;
import com.mikitellurium.telluriumforge.config.ConfigEntry;
import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mikitellurium.telluriumforge.config.TelluriumConfig;

public class Configuration {

    public static final TelluriumConfig CLIENT_CONFIG = new TelluriumConfig("clockoverlay");

    public static EnumConfigEntry<OverlayPos> CLOCK_POSITION;
    public static ConfigEntry<Boolean> CLOCK_HOLDING_REQUIRED;
    public static ConfigEntry<Boolean> SHOW_ITEM_FRAME_CLOCK;
    public static ConfigEntry<Boolean> BIG_CLOCK;

    public static void registerConfig() {
        CLIENT_CONFIG.comment("Configuration file for clock overlay");

        CLOCK_POSITION = CLIENT_CONFIG.entryBuilder()
                .define("clockPosition", OverlayPos.OVER_INVENTORY)
                .comment("The position of the clock overlay on screen");

        CLOCK_HOLDING_REQUIRED = CLIENT_CONFIG.entryBuilder()
                .define("clockHoldingRequired", true)
                .comment("If true holding a clock item is required to show the overlay,")
                .comment("if false just having a clock in the player inventory is required");

        SHOW_ITEM_FRAME_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("showItemFrameClock", true)
                .comment("If true a clock placed in a item frame displays the time");

        BIG_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("enableBigClock", false)
                .comment("BIG CLOCK!");

        CLIENT_CONFIG.build();
        ClockOverlay.logger().info("Registered configuration");
    }

}
