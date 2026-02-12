package xyz.mayahive.customDaytime.Tasks;

import org.bukkit.GameRules;
import xyz.mayahive.customDaytime.CustomDaytime;
import xyz.mayahive.customDaytime.Utils.TimeUtils;
import org.bukkit.World;

public class TimeTickTask implements Runnable {

    private static final long TICKS_PER_DAY = 24000;
    private static final long TICKS_PER_HALF_DAY = 12000;
    private double carry = 0.0;
    private long customTime = 0;
    private boolean initialized = false;
    private long baseDay = -1;

    @Override
    public void run() {

        // Get default worl
        World world = TimeUtils.getDefaultWorld();
        if (world == null) return; // If world isn't loaded, return

        // Only proceed if daylight cycle is enabled
        boolean cycleOn = Boolean.TRUE.equals(
                world.getGameRuleValue(GameRules.ADVANCE_TIME)
        );


        if (!initialized) {
            long full = world.getFullTime();
            baseDay = (full / TICKS_PER_DAY) * TICKS_PER_DAY;
            customTime = full % TICKS_PER_DAY;
            initialized = true;
        }


        long currentWorldTime = world.getFullTime() %  TICKS_PER_DAY;

        // Detect manual changes and sync time
        if (Math.abs(currentWorldTime - customTime) > 1) {
            customTime = currentWorldTime;
            carry = 0.0;
        }

        if(cycleOn) {
            double increment;

            if (TimeUtils.isNightFastForward(world) && CustomDaytime.getInstance().getConfig().getBoolean("enableNightFastForward", true)) {
                increment = TimeUtils.getFastForwardIncrementPerTick();
            } else if (customTime < TICKS_PER_HALF_DAY) {
                increment = TimeUtils.getDayIncrementPerTick();
            } else {
                increment = TimeUtils.getNightIncrementPerTick();
            }

            carry += increment;

            if (carry >= 1.0) {
                long ticksToAdd = (long) carry;
                carry -= ticksToAdd;

                long newTime = customTime + ticksToAdd;
                if (newTime >= TICKS_PER_DAY) {
                    baseDay += TICKS_PER_DAY;
                    newTime %= TICKS_PER_DAY;
                }

                customTime = newTime;
            }

        }
        world.setFullTime(baseDay + customTime);

    }
}
