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

package xyz.mayahive.customdaytime.api.model;

public record WorldKey(String namespace, String value) {

    public static WorldKey fromString(String key) {
        String[] split = key.split(":", 2);

        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid world key format: " + key);
        }

        return new WorldKey(split[0], split[1]);
    }

    public String asString() {
        return namespace + ":" + value;
    }
}
