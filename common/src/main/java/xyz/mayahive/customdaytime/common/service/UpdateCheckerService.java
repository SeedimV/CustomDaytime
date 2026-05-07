/*
 * Copyright (c) 2026 Seedim
 * This file is part of Custom Daytime, which is licensed under GPL-3.0.
 * See the LICENSE file in the project root for full license text.
 */

package xyz.mayahive.customdaytime.common.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.mayahive.customdaytime.api.platform.Platform;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class UpdateCheckerService {

    private final Platform platform;
    private final String projectId;

    public UpdateCheckerService(Platform platform, String projectId) {
        this.platform = platform;
        this.projectId = projectId;

        checkUpdatesAsync();
    }

    private void checkUpdatesAsync() {
        platform.scheduler().runTaskAsync(this::checkUpdates);
    }

    private void checkUpdates() {

        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = String.format("https://api.modrinth.com/v2/project/%s/version", projectId);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonArray versions = JsonParser.parseString(response.body()).getAsJsonArray();

            String minecraftVersion = platform.minecraftVersion();
            String loader = platform.platform().displayName().toLowerCase();

            JsonObject bestMatch = null;

            for (JsonElement element : versions) {

                JsonObject object = element.getAsJsonObject();

                JsonArray gameVersions = object.getAsJsonArray("game_versions");
                JsonArray loaders = object.getAsJsonArray("loaders");

                boolean gameMatch = contains(gameVersions, minecraftVersion);
                boolean loaderMatch = contains(loaders,  loader);

                if (gameMatch && loaderMatch) {
                    bestMatch = object;
                    break;
                }
            }

            if (bestMatch == null) {
                platform.logger().info("No matching Modrinth version found for the loader/version.\nPlease, check manually for updates!");
                return;
            }

            String latestVersion = bestMatch.get("version_number").getAsString();
            String currentVersion = platform.projectVersion();

            if (!isNewerVersion(latestVersion, currentVersion)) {
                platform.logger().info("Custom Daytime is up to date.");
                return;
            }

            platform.logger().info("A new version of Custom Daytime is available: " + latestVersion + ". Current version is " + currentVersion);

            JsonArray files = bestMatch.getAsJsonArray("files");

            if (!files.isEmpty()) {
                String downloadUrl = files.get(0).getAsJsonObject().get("url").getAsString();
                platform.logger().info("Download: " + downloadUrl);
            }

        } catch (Exception e) {
            platform.logger().error("Could not check for updates. Please, check manually.");
            platform.logger().error(e.getMessage());
        }

    }

    private boolean contains(JsonArray array, String value) {
        for (JsonElement element : array) {
            if (element.getAsString().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNewerVersion(String remote, String local) {
        String[] remoteParts = remote.split("\\.");
        String[] localParts = local.split("\\.");

        int length = Math.max(remoteParts.length, localParts.length);

        for (int i = 0; i < length; i++) {
            int r = i < remoteParts.length ? Integer.parseInt(remoteParts[i]) : 0;

            int l = i < localParts.length ? Integer.parseInt(localParts[i]) : 0;

            if (r > l) return true;
            if (r < l) return false;
        }
        return false;
    }

}
