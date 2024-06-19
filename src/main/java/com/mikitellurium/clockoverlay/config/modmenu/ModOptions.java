package com.mikitellurium.clockoverlay.config.modmenu;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.clock.ClockColor;
import com.mikitellurium.clockoverlay.clock.OverlayPos;
import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.telluriumforge.option.BooleanConfigOption;
import com.mikitellurium.telluriumforge.option.ConfigOption;
import com.mikitellurium.telluriumforge.option.EnumConfigOption;
import net.minecraft.client.option.SimpleOption;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ModOptions {

    public static final EnumConfigOption<OverlayPos> CLOCK_POSITION = new EnumConfigOption<>(
            ClockOverlay.getModIdentifier("clock_position"), Configuration.CLOCK_POSITION);
    public static final BooleanConfigOption CLOCK_FORMAT = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("clock_format"),
            "24_hour", "12_hour", Configuration.CLOCK_FORMAT);
    public static final BooleanConfigOption SHOW_CLOCK_WHEN = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("show_clock_when"),
            "held_in_hand", "in_inventory", Configuration.CLOCK_HOLDING_REQUIRED);
    public static final BooleanConfigOption SHOW_ITEM_FRAME_CLOCK = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("show_item_frame_clock"), Configuration.SHOW_ITEM_FRAME_CLOCK);
    public static final EnumConfigOption<ClockColor> CLOCK_COLOR = new EnumConfigOption<>(
            ClockOverlay.getModIdentifier("clock_color"), Configuration.CLOCK_COLOR);
    public static final EnumConfigOption<ClockColor> ITEM_FRAME_CLOCK_COLOR = new EnumConfigOption<>(
            ClockOverlay.getModIdentifier("item_frame_clock_color"), Configuration.ITEM_FRAME_CLOCK_COLOR);
    public static final BooleanConfigOption HIDE_BROKEN_CLOCK = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("hide_broken_clock"), Configuration.HIDE_BROKEN_CLOCK);

    public static List<SimpleOption<?>> getOptions() {
        List<SimpleOption<?>> options = new ArrayList<>();
        for (Field field : ModOptions.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                try {
                    options.add(((ConfigOption<?>) field.get(null)).asOption());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return options;
    }

}
