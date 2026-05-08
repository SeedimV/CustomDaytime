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
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.paper.platform.PaperWorld;

@RequiredArgsConstructor
public class WorldListener implements Listener {

    private final EventBus eventBus;

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        PaperWorld world = new PaperWorld(event.getWorld());
        eventBus.fire(new xyz.mayahive.customdaytime.common.event.type.WorldLoadEvent(world));
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        PaperWorld world = new PaperWorld(event.getWorld());
        eventBus.fire(new xyz.mayahive.customdaytime.common.event.type.WorldUnloadEvent(world));
    }

}
