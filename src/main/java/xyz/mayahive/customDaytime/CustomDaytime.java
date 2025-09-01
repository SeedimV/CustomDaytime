package xyz.mayahive.customDaytime;

import xyz.mayahive.customDaytime.Listeners.BedEnterListener;
import xyz.mayahive.customDaytime.Listeners.BedLeaveListener;
import xyz.mayahive.customDaytime.Listeners.TimeSkipListener;
import xyz.mayahive.customDaytime.Tasks.TimeTickTask;
import xyz.mayahive.customDaytime.Updates.UpdateManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomDaytime extends JavaPlugin {

    private static CustomDaytime instance;

    @Override
    public void onEnable() {

        // Set plugin instance
        instance = this;

        // Config
        saveDefaultConfig();

        // Metrics
        new Metrics(instance, 26910);

        // Updates
        new UpdateManager(instance, "C7YliNqw");

        // Events
        Bukkit.getPluginManager().registerEvents(new TimeSkipListener(), instance); // Vanilla time skip prevention
        Bukkit.getPluginManager().registerEvents(new BedEnterListener(), instance); // Bed Enter Listener
        Bukkit.getPluginManager().registerEvents(new BedLeaveListener(), instance); // Bed Leave Listener

        // Timer
        TimeTickTask timeTickTask = new TimeTickTask();
        Bukkit.getGlobalRegionScheduler().runAtFixedRate(
                instance,
                task -> timeTickTask.run(),
                1,
                1
        );
    }

    // Plugin instance getter
    public static CustomDaytime getInstance() {return instance;}
}
