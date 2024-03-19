package com.mikitellurium.clockoverlay.clock;

import net.minecraft.util.math.random.Random;

import java.util.function.Supplier;

public class TickedClock {

    public static final TickedClock INSTANCE = new TickedClock();

    private final Random random = Random.create();
    private final Supplier<String> randInt = () -> String.valueOf(random.nextInt(10));
    private int age = 0;
    private String randomTime;
    private int randomDayTimeColor = -1;

    public void tick() {
        if (++age % 2 == 0) {
            String i = randInt.get();
            String j = randInt.get();
            String k = randInt.get();
            String l = randInt.get();
            this.randomTime = i + j + ":" + k + l;
        }
    }

    public String getTimeString() {
        return this.randomTime;
    }

}
