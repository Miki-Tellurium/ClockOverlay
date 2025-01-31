package com.mikitellurium.clockoverlay.util;

import com.mikitellurium.clockoverlay.clock.BrokenClock;
import com.mikitellurium.clockoverlay.config.Configuration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ClientDataHelper {

    public static boolean playerIsHoldingClock() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return false;

        ItemStack mainHandItem = player.getMainHandStack();
        ItemStack offHandItem = player.getOffHandStack();

        return mainHandItem.isOf(Items.CLOCK) || offHandItem.isOf(Items.CLOCK);
    }

    public static boolean playerHasClockInInventory() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return false;

        return checkInventory(player.getInventory());
    }

    private static boolean checkInventory(PlayerInventory inventory) {
        if (inventory.isEmpty()) return false;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (stack.isEmpty()) continue;
            if (stack.isOf(Items.CLOCK)) return true;
        }

        return false;
    }

    public static long getRawTimeOfDay() {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return -1;
        return world.getTimeOfDay() % 24000;
    }

    public static long getAdjustedTimeOfDay() {
        final long rawTime = getRawTimeOfDay();
        if (rawTime == -1) return -1;
        return (int)(30000 + rawTime) % 24000;
    }

    public static String getTimeString() {
        if (shouldClockBeBroken()) {
            return BrokenClock.INSTANCE.getTimeString();
        } else {
            long time = getAdjustedTimeOfDay();
            int seconds = (int) time / 20;
            int clockHours = seconds / 50;
            int clockMinutes = (int) Math.floor((time % 1000) / 16.66D);

            String timeString;
            boolean is24Hour = Configuration.CLOCK_FORMAT.getValue();
            if (is24Hour) {
                timeString = String.format("%02d:%02d", clockHours, clockMinutes == 60 ? 59 : clockMinutes);
            } else {
                String timeSuffix = time < 12000 ? "AM" : "PM";
                timeString = String.format("%02d:%02d %s", clockHours % 12, clockMinutes == 60 ? 59 : clockMinutes, timeSuffix);
            }
            return timeString;
        }
    }

    public static boolean shouldClockBeBroken() {
        return !MinecraftClient.getInstance().world.getDimension().natural();
    }

    public static boolean isDebugScreenOpen() {
        return MinecraftClient.getInstance().inGameHud.getDebugHud().shouldShowDebugHud();
    }

    public static boolean isHudHidden() {
        return MinecraftClient.getInstance().options.hudHidden;
    }

    public static boolean hasStatusBar() {
        return MinecraftClient.getInstance().interactionManager.hasStatusBars();
    }

}
