/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.event.type;

import lombok.NonNull;
import xyz.mayahive.customdaytime.api.platform.PlatformWorld;
import xyz.mayahive.customdaytime.common.event.Event;

public record WorldSleepingPlayerCountChangeEvent(@NonNull PlatformWorld world) implements Event {
}
