/*
 *     Copyright (c) 2026 Seedim
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.mayahive.customdaytime.paper.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ClockTimeSkipEvent;
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
            if (event.getSkipReason().equals(ClockTimeSkipEvent.SkipReason.NIGHT_SKIP)) {
                event.setCancelled(true);
            }
        }
    }
}
