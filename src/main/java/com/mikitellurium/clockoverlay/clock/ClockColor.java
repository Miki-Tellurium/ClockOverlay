package com.mikitellurium.clockoverlay.clock;

import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;

public enum ClockColor {
    WHITE((time, offset, delta) -> Formatting.WHITE.getColorValue()),
    DAY_CYCLE(ClockColor::mapTimeToColor),
    RAINBOW(ClockColor::getRainbowColor);

    private static final DyeColor[] RAINBOW_COLORS = new DyeColor[] {
            DyeColor.RED,
            DyeColor.ORANGE,
            DyeColor.YELLOW,
            DyeColor.LIME,
            DyeColor.LIGHT_BLUE,
            DyeColor.BLUE,
            DyeColor.MAGENTA,
            DyeColor.PURPLE,
            DyeColor.PINK
    };
    private final ColorFunction colorGetter;

    ClockColor(ColorFunction colorGetter) {
        this.colorGetter = colorGetter;
    }

    public int getColor(float delta) {
        return this.getColor(0, delta);
    }

    public int getColor(int offset, float delta) {
        return colorGetter.apply(ClientDataHelper.getAdjustedTimeOfDay(), offset, delta);
    }

    private static int mapTimeToColor(long time, int offset, float delta) {
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

    private static int getRainbowColor(long time, int offset, float delta) {
        int speed = 50;
        int value = (int) time / speed + offset;

        int colorsNum = RAINBOW_COLORS.length;
        int id = value % colorsNum;
        int id2 = (value + 1) % colorsNum;
        float multiplier = ((float)(time % speed) + delta) / (float) speed;
        float[] rgb = RAINBOW_COLORS[id].getColorComponents();
        float[] rgb1 = RAINBOW_COLORS[id2].getColorComponents();
        float f = rgb[0] * (1.0f - multiplier) + rgb1[0] * multiplier;
        float f1 = rgb[1] * (1.0f - multiplier) + rgb1[1] * multiplier;
        float f2 = rgb[2] * (1.0f - multiplier) + rgb1[2] * multiplier;
        float red = f * 255.0F;
        float green = f1 * 255.0F;
        float blue = f2 * 255.0F;
        return ColorHelper.Argb.getArgb(255, (int) red, (int) green, (int) blue);
    }

    @FunctionalInterface
    private interface ColorFunction {
        int apply(long time, int offset, float delta);
    }

}
