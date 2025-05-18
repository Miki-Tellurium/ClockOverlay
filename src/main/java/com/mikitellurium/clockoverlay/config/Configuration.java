package com.mikitellurium.clockoverlay.config;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.clock.ClockColor;
import com.mikitellurium.clockoverlay.clock.OverlayPos;
import com.mikitellurium.telluriumforge.config.BooleanConfigEntry;
import com.mikitellurium.telluriumforge.config.EnumConfigEntry;
import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import com.mikitellurium.telluriumforge.config.TelluriumConfig;

public class Configuration {
    public static final TelluriumConfig CLIENT_CONFIG = new TelluriumConfig("clockoverlay", TelluriumConfig.Type.CLIENT);
    public static EnumConfigEntry<OverlayPos> CLOCK_POSITION;
    public static BooleanConfigEntry CLOCK_FORMAT;
    public static BooleanConfigEntry CLOCK_HOLDING_REQUIRED;
    public static BooleanConfigEntry SHOW_CURRENT_DAY;
    public static EnumConfigEntry<ClockColor> CLOCK_COLOR;
    public static EnumConfigEntry<ClockColor> DAY_COLOR;
    public static RangedConfigEntry<Integer> CLOCK_OPACITY;
    public static BooleanConfigEntry HIDE_BROKEN_CLOCK;
    public static BooleanConfigEntry SHOW_ITEM_FRAME_CLOCK;
    public static BooleanConfigEntry SHOW_ITEM_FRAME_CURRENT_DAY;
    public static EnumConfigEntry<ClockColor> ITEM_FRAME_CLOCK_COLOR;
    public static BooleanConfigEntry BIG_CLOCK;

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

        SHOW_CURRENT_DAY = CLIENT_CONFIG.entryBuilder()
                .define("showCurrentDay", true)
                .comment("Show the current day of the world");

        CLOCK_COLOR = CLIENT_CONFIG.entryBuilder()
                .define("clockColor", ClockColor.WHITE)
                .comment("The color of the clock overlay");

        DAY_COLOR = CLIENT_CONFIG.entryBuilder()
                .define("dayColor", ClockColor.WHITE)
                .comment("The color of the current day overlay");

        CLOCK_OPACITY = CLIENT_CONFIG.entryBuilder()
                .defineInRange("clockOpacity", 100, 10, 100)
                .comment("The opacity of the clock overlay");

        HIDE_BROKEN_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("hideBrokenClock", false)
                .comment("If true automatically hide the overlay when inside a")
                .comment("dimension where the clock doesn't work (like the nether)");

        SHOW_ITEM_FRAME_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("showItemFrameClock", true)
                .comment("If true a clock placed in a item frame displays the time");

        SHOW_ITEM_FRAME_CURRENT_DAY = CLIENT_CONFIG.entryBuilder()
                .define("showItemFrameCurrentDay", false)
                .comment("If true a clock placed in a item frame displays the current day of the world");

        ITEM_FRAME_CLOCK_COLOR = CLIENT_CONFIG.entryBuilder()
                .define("itemFrameClockColor", ClockColor.WHITE)
                .comment("The color of the clock overlay in item frame");

        BIG_CLOCK = CLIENT_CONFIG.entryBuilder()
                .define("enableBigClock", false)
                .comment("BIG CLOCK!");

        CLIENT_CONFIG.build();
        ClockOverlay.logger().info("Registered configuration");
    }
}
