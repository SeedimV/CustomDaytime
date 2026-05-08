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

package xyz.mayahive.customdaytime.sponge.listener;

import lombok.RequiredArgsConstructor;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ChangeEntityWorldEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.event.type.WorldPlayerCountChangeEvent;
import xyz.mayahive.customdaytime.common.event.type.WorldSleepingPlayerCountChangeEvent;
import xyz.mayahive.customdaytime.sponge.platform.SpongeWorld;

@RequiredArgsConstructor
public class WorldActivityListener {

    private final EventBus eventBus;

    @Listener
    public void onJoin(ServerSideConnectionEvent.Join event) {
        eventBus.fire(new WorldPlayerCountChangeEvent(new SpongeWorld(event.player().world())));
    }

    @Listener
    public void onLeave(ServerSideConnectionEvent.Leave event) {
        eventBus.fire(new WorldPlayerCountChangeEvent(new SpongeWorld(event.player().world())));
    }

    @Listener
    public void onWorldChange(ChangeEntityWorldEvent.Reposition event) {
        if (!(event.entity() instanceof ServerPlayer)) {return;}

        eventBus.fire(new WorldPlayerCountChangeEvent(new SpongeWorld(event.originalWorld())));
        eventBus.fire(new WorldSleepingPlayerCountChangeEvent(new SpongeWorld(event.destinationWorld())));
    }

}
