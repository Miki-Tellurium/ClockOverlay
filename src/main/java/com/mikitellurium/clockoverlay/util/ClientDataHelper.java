package com.mikitellurium.clockoverlay.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
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

        return player.getInventory().contains(Items.CLOCK.getDefaultStack());
    }

    public static long getRawTimeOfDay() {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return -1;
        return world.getTimeOfDay() % 24000;
    }

    public static int getAdjustedTimeOfDay() {
        ClientWorld world = MinecraftClient.getInstance().world;
        if (world == null) return -1;
        return (int)(24000 + getRawTimeOfDay() + 6000) % 24000;
    }

    public static String getTimeString() {
        int time = getAdjustedTimeOfDay();
        int seconds = time / 20;
        int clockHours = seconds / 50;
        int clockMinutes = (int) Math.floor((time % 1000) / 16.66D);
        return String.format("%02d:%02d", clockHours, clockMinutes == 60 ? 59 : clockMinutes);
    }

    public static boolean isDebugScreenOpen() {
        return MinecraftClient.getInstance().options.debugEnabled;
    }

    public static boolean isHudHidden() {
        return MinecraftClient.getInstance().options.hudHidden;
    }

}
