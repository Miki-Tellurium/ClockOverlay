package com.mikitellurium.clockoverlay.clock;

import net.minecraft.util.math.random.Random;

import java.util.function.Supplier;

public class BrokenClock {

    public static final BrokenClock INSTANCE = new BrokenClock();

    private BrokenClock() {}

    private final Random random = Random.create();
    private final Supplier<String> randInt = () -> String.valueOf(random.nextInt(10));
    private int age = 0;
    private String randomTime;
    private int currentDayColorIndex = 0;
    private int oldDayColorIndex = 0;
    private final int colorChangeSpeed = 50;

    public void tick() {
        age++;
        String i = randInt.get();
        String j = randInt.get();
        String k = randInt.get();
        String l = randInt.get();
        this.randomTime = i + j + ":" + k + l;
        if (age % colorChangeSpeed == 0) {
            oldDayColorIndex = currentDayColorIndex;
            currentDayColorIndex = this.random.nextInt(4);
        }
    }

    public String getTimeString() {
        return this.randomTime;
    }

    public float getTimeMultiplier() {
        return (float)(this.age % colorChangeSpeed) / colorChangeSpeed;
    }

    public int getCurrentDayColorIndex() {
        return currentDayColorIndex;
    }

    public int getOldDayColorIndex() {
        return oldDayColorIndex;
    }

}
