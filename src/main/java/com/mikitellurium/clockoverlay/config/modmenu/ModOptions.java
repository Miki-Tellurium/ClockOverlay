package com.mikitellurium.clockoverlay.config.modmenu;

import com.mikitellurium.clockoverlay.ClockOverlay;
import com.mikitellurium.clockoverlay.config.Configuration;
import com.mikitellurium.clockoverlay.util.OverlayPos;
import com.mikitellurium.telluriumforge.option.BooleanConfigOption;
import com.mikitellurium.telluriumforge.option.EnumConfigOption;
import net.minecraft.client.option.SimpleOption;

import java.util.Locale;

public class ModOptions {

    public static final SimpleOption<OverlayPos> CLOCK_POSITION = new EnumConfigOption<>(
            ClockOverlay.getModIdentifier("clock_position"), Configuration.CLOCK_POSITION).asOption();
    public static final SimpleOption<Boolean> SHOW_CLOCK_WHEN = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("show_clock_when"),
            "held_in_hand", "in_inventory", Configuration.CLOCK_HOLDING_REQUIRED).asOption();
    public static final SimpleOption<Boolean> SHOW_ITEM_FRAME_CLOCK = new BooleanConfigOption(
            ClockOverlay.getModIdentifier("show_item_frame_clock"), Configuration.SHOW_ITEM_FRAME_CLOCK).asOption();

}
