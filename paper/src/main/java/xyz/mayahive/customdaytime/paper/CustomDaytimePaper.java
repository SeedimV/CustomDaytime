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

package xyz.mayahive.customdaytime.paper;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.common.bootstrap.AbstractBootstrap;
import xyz.mayahive.customdaytime.common.event.EventBus;
import xyz.mayahive.customdaytime.common.service.ConfigService;
import xyz.mayahive.customdaytime.paper.listener.BedActivityListener;
import xyz.mayahive.customdaytime.paper.listener.TimeSkipListener;
import xyz.mayahive.customdaytime.paper.listener.WorldActivityListener;
import xyz.mayahive.customdaytime.paper.listener.WorldListener;
import xyz.mayahive.customdaytime.paper.platform.PaperPlatform;

public final class CustomDaytimePaper extends JavaPlugin {

    @Override
    public void onEnable() {

        new Metrics(this, 26910);

        AbstractBootstrap bootstrap = new AbstractBootstrap() {
            @Override
            protected Platform platform() {
                return new PaperPlatform(CustomDaytimePaper.this);
            }
        };

        bootstrap.initialize();

        EventBus eventBus = bootstrap.context().eventBus();
        ConfigService configService = bootstrap.context().configService();

        Bukkit.getPluginManager().registerEvents(new BedActivityListener(eventBus), this);
        Bukkit.getPluginManager().registerEvents(new TimeSkipListener(configService), this);
        Bukkit.getPluginManager().registerEvents(new WorldActivityListener(eventBus), this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(eventBus), this);
    }
}
