package com.mikitellurium.clockoverlay.clock;

import com.mikitellurium.clockoverlay.util.ClientDataHelper;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;

public enum ClockColor {
    @SuppressWarnings("ConstantConditions")
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
    private static final int WHITE_RGB = 0;
    private static final int NIGHT = 1;
    private static final int SUNSET = 2;
    private static final int DAY = 3;
    private static final int[] DAY_COLORS = new int[] {
            ColorHelper.Argb.getArgb(255, 255, 255),
            ColorHelper.Argb.getArgb(0, 30, 153),
            ColorHelper.Argb.getArgb(255, 130, 0),
            ColorHelper.Argb.getArgb(0,217,255),
    };

    private final ColorFunction colorFunction;

    ClockColor(ColorFunction colorGetter) {
        this.colorFunction = colorGetter;
    }

    public int getColor(float delta) {
        return this.getColor(0, delta);
    }

    public int getColor(int offset, float delta) {
        return colorFunction.apply(ClientDataHelper.getAdjustedTimeOfDay(), offset, delta);
    }

    private static int mapTimeToColor(long time, int offset, float delta) {
        if (ClientDataHelper.shouldClockBeBroken()) {
            BrokenClock clock = BrokenClock.INSTANCE;
            return ColorHelper.Argb.lerp(clock.getTimeMultiplier() ,
                    DAY_COLORS[clock.getOldDayColorIndex()],
                    DAY_COLORS[clock.getCurrentDayColorIndex()]);
        }

        int color = DAY_COLORS[WHITE_RGB];

        if (time <= 4000) { // From midnight to morning
            color = DAY_COLORS[NIGHT];
        } else if (time <= 4100) { // Sunrise start
            time -= 4000;
            color = lerpColor(time, DAY_COLORS[NIGHT], DAY_COLORS[WHITE_RGB]);
        } else if (time <= 4200) {
            time -= 4100;
            color = lerpColor(time, DAY_COLORS[WHITE_RGB], DAY_COLORS[SUNSET]);
        } else if (time <= 6000) { // Morning
            color = DAY_COLORS[SUNSET];
        } else if (time <= 6100) { // Sunrise end
            time -= 6000;
            color = lerpColor(time, DAY_COLORS[SUNSET], DAY_COLORS[WHITE_RGB]);
        } else if (time <= 6200) {
            time -= 6100;
            color = lerpColor(time, DAY_COLORS[WHITE_RGB], DAY_COLORS[DAY]);
        } else if (time <= 17900) { // Daytime
            color = DAY_COLORS[DAY];
        } else if (time <= 18000) { // Sunset start
            time -= 17900;
            color = lerpColor(time, DAY_COLORS[DAY], DAY_COLORS[WHITE_RGB]);
        } else if (time <= 18100) {
            time -= 18000;
            color = lerpColor(time, DAY_COLORS[WHITE_RGB], DAY_COLORS[SUNSET]);
        } else if (time <= 19000) { // Evening
            color = DAY_COLORS[SUNSET];
        } else if (time <= 19100) { // Sunset end
            time -= 19000;
            color = lerpColor(time, DAY_COLORS[SUNSET], DAY_COLORS[WHITE_RGB]);
        } else if (time <= 19200) {
            time -= 19100;
            color = lerpColor(time, DAY_COLORS[WHITE_RGB], DAY_COLORS[NIGHT]);
        } else if (time <= 24000) { // From sunset to midnight
            color = DAY_COLORS[NIGHT];
        }

        return color;
    }

    private static int lerpColor(long time, int color, int color1) {
        return ColorHelper.Argb.lerp((float)time / 100, color, color1);
    }

    private static int getRainbowColor(long time, int offset, float delta) {
        int speed = 50;
        int value = (int) time / speed + offset;

        int colorsNum = RAINBOW_COLORS.length;
        int id = value % colorsNum;
        int id1 = (value + 1) % colorsNum;
        float multiplier = ((float)(time % speed) + delta) / (float) speed;
        int rgb = SheepEntity.getRgbColor(RAINBOW_COLORS[id]);
        int rgb1 = SheepEntity.getRgbColor(RAINBOW_COLORS[id1]);

        return ColorHelper.Argb.lerp(multiplier, rgb, rgb1);
    }

    @FunctionalInterface
    private interface ColorFunction {
        int apply(long time, int offset, float delta);
    }

}
