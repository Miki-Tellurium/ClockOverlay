package com.mikitellurium.clockoverlay.config;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.clock.ClockColor;
import com.mikitellurium.clockoverlay.clock.OverlayPos;
import com.mikitellurium.telluriumforge.config.ConfigEntry;
import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mikitellurium.telluriumforge.config.TelluriumConfig;

public class Configuration {

    public static final TelluriumConfig CLIENT_CONFIG = new TelluriumConfig("clockoverlay", TelluriumConfig.Type.CLIENT);

    public static EnumConfigEntry<OverlayPos> CLOCK_POSITION;
    public static ConfigEntry<Boolean> CLOCK_FORMAT;
    public static ConfigEntry<Boolean> CLOCK_HOLDING_REQUIRED;
    public static ConfigEntry<Boolean> SHOW_ITEM_FRAME_CLOCK;
    public static EnumConfigEntry<ClockColor> CLOCK_COLOR;
    public static EnumConfigEntry<ClockColor> ITEM_FRAME_CLOCK_COLOR;
    public static ConfigEntry<Boolean> HIDE_BROKEN_CLOCK;
    public static ConfigEntry<Boolean> BIG_CLOCK;

    public static void registerConfig() {
        CLIENT_CONFIG.comment("Configuration file for clock overlay");

        CLOCK_POSITION = CLIENT_CONFIG.entryBuilder()
                .define("clockPosition", OverlayPos.OVER_INVENTORY)
                .comment("The position of the clock overlay on screen");

        CLOCK_FORMAT = CLIENT_CONFIG.entryBuilder()
                .define("clockFormat", true)
                .comment("The format of the clock overlay, set to true for 24-hour clock")
                .comment("and false for 12-hour clock");

        CLOCK_HOLDING_REQUIRED = CLIENT_CONFIG.entryBuilder()
                .define("clockHoldingRequired", true)
                .comment("If true holding a clock item is required to show the overlay,")
                .comment("if false just having a clock in the player inventory is required");

        SHOW_ITEM_FRAME_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("showItemFrameClock", true)
                .comment("If true a clock placed in a item frame displays the time");

        CLOCK_COLOR = CLIENT_CONFIG.entryBuilder()
                .define("clockColor", ClockColor.WHITE)
                .comment("The color of the clock overlay");

        ITEM_FRAME_CLOCK_COLOR = CLIENT_CONFIG.entryBuilder()
                .define("itemFrameClockColor", ClockColor.WHITE)
                .comment("The color of the clock overlay in item frame");

        HIDE_BROKEN_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("hideBrokenClock", false)
                .comment("If true automatically hide the overlay when inside a")
                .comment("dimension where the clock doesn't work (like the nether)");

        BIG_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("enableBigClock", false)
                .comment("BIG CLOCK!");

        CLIENT_CONFIG.build();
        ClockOverlay.logger().info("Registered configuration");
    }

}
