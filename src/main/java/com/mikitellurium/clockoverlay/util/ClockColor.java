package com.mikitellurium.clockoverlay.util;

import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;

import java.awt.*;
import java.util.function.Function;

public enum ClockColor {
    WHITE((time) -> Formatting.WHITE.getColorValue()),
    DAY_CYCLE(ClockColor::mapTimeToColor),
    RAINBOW(ClockColor::getRainbowColor);

    private final Function<Long, Integer> colorGetter;

    ClockColor(Function<Long, Integer> colorGetter) {
        this.colorGetter = colorGetter;
    }

    public int getColor() {
        return this.getColor(0);
    }

    public int getColor(long offset) {
        return colorGetter.apply(ClientDataHelper.getAdjustedTimeOfDay() + offset);
    }

    private static int mapTimeToColor(long time) {
        int red, green, blue;
        float redMul = 1.0F;
        float greenMul = 1.0F;
        float blueMul = 1.0F;

        if (time <= 4000) { // From midnight to morning
            redMul = 0.0F;
            greenMul = 0.12F;
            blueMul = 0.6F;
        } else if (time <= 4100) { // Sunrise start
            time -= 4000;
            redMul = (float) time / 110;
            greenMul = 0.12F + (float) time / 100;
            blueMul = 0.6F + (float) time / 100;
        } else if (time <= 4200) {
            time -= 4100;
            greenMul = 1 - (float) time / 200;
            blueMul = 1 - (float) time / 100;
        } else if (time <= 6000) { // Morning
            greenMul = 0.51F;
            blueMul = 0.0F;
        } else if (time <= 6100) { // Sunrise end
            time -= 6000;
            greenMul = 0.51F + (float) time / 100;
            blueMul = (float) time / 100;
        } else if (time <= 6200) {
            time -= 6100;
            redMul = 1 - (float) time / 100;
            greenMul = 1 - (float) time / 650;
        } else if (time <= 17900) { // Daytime
            redMul = 0.0F;
            greenMul = 0.85F;
        } else if (time <= 18000) { // Sunset start
            time -= 17900;
            redMul = (float) time / 100;
            greenMul = 0.85F + (float) time / 100;
        } else if (time <= 18100) {
            time -= 18000;
            greenMul = 1 - (float) time / 200;
            blueMul = 1 - (float) time / 100;
        } else if (time <= 19000) { // Evening
            greenMul = 0.51F;
            blueMul = 0.0F;
        } else if (time <= 19100) { // Sunset end
            time -= 19000;
            greenMul = 0.51F + (float) time / 100;
            blueMul = (float) time / 100;
        } else if (time <= 19200) {
            time -= 19100;
            redMul = 1 - (float) time / 100;
            greenMul = 1 - (float) time / 112;
            blueMul = 1 - (float) time / 248;
        } else if (time <= 24000) { // From sunset to midnight
            redMul = 0.0F;
            greenMul = 0.12F;
            blueMul = 0.6F;
        }
        red = Math.min(255, (int) (255 * redMul));
        green = Math.min(255, (int) (255 * greenMul));
        blue = Math.min(255, (int) (255 * blueMul));
        return ColorHelper.Argb.getArgb(255, red, green, blue);
    }

    private static int getRainbowColor(long time) {
        int value = (int)time % 1000;
        int offset = (int) time % 1024;
        float hue = (float) (value + offset) / 512 * 1000;
        return Color.getHSBColor(hue / 1000, 0.7F, 0.8F).getRGB();
    }

}
