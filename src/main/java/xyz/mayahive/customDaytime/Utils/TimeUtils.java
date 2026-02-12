package xyz.mayahive.customDaytime.Utils;

import org.bukkit.GameRules;
import xyz.mayahive.customDaytime.CustomDaytime;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TimeUtils {

    // Track which world are in fast-forward mode
    private static final Set<UUID> fastForwardWorlds = new HashSet<>();

    // Start night fast forwarding
    public static void startNightFastForward(World world) {
        if (world != null) {
            fastForwardWorlds.add(world.getUID());

            broadcastActionBar(world, "fastforward.initiate", "The Argument");
        }
    }

    // Stop night fast forwarding
    public static void stopNightFastForward(World world) {
        if (world != null) {fastForwardWorlds.remove(world.getUID());}
    }

    // Check if world is in fast-forward
    public static boolean isNightFastForward(World world) {
        return fastForwardWorlds.contains(world.getUID());
    }

    // Get the default spawn world
    public static World getDefaultWorld() {
        return Bukkit.getWorlds().isEmpty() ? null : Bukkit.getWorlds().getFirst();
    }

    // Check if enough players are sleeping for night fast forwarding
    public static boolean isEnoughPlayersSleeping(World world) {
        if (world == null) return false;

        if (!CustomDaytime.getInstance().getConfig().getBoolean("enableNightFastForward", true)) {return false;}

        // Get player sleeping percentage game rule value
        Integer ruleValue = world.getGameRuleValue(GameRules.PLAYERS_SLEEPING_PERCENTAGE);
        if (ruleValue == null) ruleValue = 100; // fallback

        double percentage = ruleValue / 100.0;
        int totalPlayers = world.getPlayers().size();
        int sleepingPlayers = (int) world.getPlayers().stream().filter(Player::isSleeping).count();

        int required;
        if (percentage <= 0) {
            required = 1;
        } else {
            required = (int) Math.ceil(totalPlayers * percentage);
        }

        return sleepingPlayers >= required;
    }

    // Getter for time increment per tick during day based on config
    public static double getDayIncrementPerTick() {

        // Get day length in game ticks from config
        double dayLengthGameTicks = CustomDaytime.getInstance().getConfig().getDouble("dayLengthMinutes", 10.0) * 60 * 20;

        // Return relative time increment in ticks
        return (12000 / dayLengthGameTicks);
    }

    // Getter for time increment per tick during night based on config
    public static double getNightIncrementPerTick() {

        // Get night length in game ticks from config
        double nightLengthGameTicks = CustomDaytime.getInstance().getConfig().getDouble("nightLengthMinutes", 10.0) * 60 * 20;

        // Return relative time increment in ticks
        return (12000 / nightLengthGameTicks);
    }

    // Getter for time increment during fast forwarding based on config
    public static double getFastForwardIncrementPerTick() {

        // Get fast forwarded night length in game ticks from config
        double fastForwardGameTicks = CustomDaytime.getInstance().getConfig().getDouble("nightFastForwardSeconds", 5.0) * 20;

        // Return relative time increment in ticks
        return (12000 / fastForwardGameTicks);
    }

    public static void broadcastActionBar(World world, String key, String fallback) {
        for  (Player player : world.getPlayers()) {
            player.sendActionBar(Component.translatable(key, Component.text(fallback)));
        }
    }
}
