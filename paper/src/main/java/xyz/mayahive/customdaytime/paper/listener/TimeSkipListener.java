package xyz.mayahive.customdaytime.paper.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;
import xyz.mayahive.customdaytime.common.service.ConfigService;

@RequiredArgsConstructor
public class TimeSkipListener implements Listener {

    private final ConfigService configService;

    @EventHandler
    public void onTimeSkipEvent(TimeSkipEvent event) {
        String worldKey = event.getWorld().key().asString();
        if (!configService.getRootKeys().contains(worldKey)) {return;}

        boolean accelerationEnabled = configService.getConfigValue(Boolean.class, true, event.getWorld().key().asString(), "accelerationEnabled");

        if (accelerationEnabled) {
            if (event.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP)) {
                event.setCancelled(true);
            }
        }
    }
}
