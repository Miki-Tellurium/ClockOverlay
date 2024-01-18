package com.mikitellurium.clockoverlay.util;

import com.mikitellurium.clockoverlay.config.Configuration;

public enum OverlayPos {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    OVER_INVENTORY;

    public static void setScreenPosConfig(OverlayPos pos) {
        Configuration.CLOCK_POSITION.setValue(pos);
    }

    public static OverlayPos getScreenPosConfig() {
        return Configuration.CLOCK_POSITION.getValue();
    }

}
