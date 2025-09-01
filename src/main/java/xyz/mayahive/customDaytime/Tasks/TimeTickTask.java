package xyz.mayahive.customDaytime.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import xyz.mayahive.customDaytime.CustomDaytime;
import xyz.mayahive.customDaytime.Utils.TimeUtils;
import org.bukkit.World;

public class TimeTickTask implements Runnable {

    private static final long TICKS_PER_DAY = 24000;
    private static final long TICKS_PER_HALF_DAY = 12000;
    private double carry = 0.0;
    private long customTime = 0;
    private boolean initialized = false;

    @Override
    public void run() {

        // Get default worl
        World world = TimeUtils.getDefaultWorld();
        if (world == null) return; // If world isn't loaded, return

        // Only proceed if daylight cycle is enabled
        Boolean daylightCycle = world.getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE);
        boolean cycleOn = daylightCycle != null && daylightCycle;

        if (!initialized) {
            // Initialize to current world time
            customTime = world.getFullTime() % TICKS_PER_DAY;
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
                customTime = (customTime + ticksToAdd) % TICKS_PER_DAY;
            }
        }
        world.setFullTime(customTime);
    }
}
