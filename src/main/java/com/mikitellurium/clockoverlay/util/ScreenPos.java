package com.mikitellurium.clockoverlay.util;

import com.mikitellurium.clockoverlay.config.Configuration;

public enum ScreenPos {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    OVER_INVENTORY;

    public static void setScreenPosConfig(ScreenPos pos) {
        Configuration.CLOCK_POSITION.setValue(pos.toString());
    }

    public static ScreenPos getScreenPosConfig() {
        return ScreenPos.valueOf(Configuration.CLOCK_POSITION.getValue());
    }

}
