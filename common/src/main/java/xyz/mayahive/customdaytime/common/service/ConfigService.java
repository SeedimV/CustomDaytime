package xyz.mayahive.customdaytime.common.service;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;
import xyz.mayahive.customdaytime.api.platform.Platform;
import xyz.mayahive.customdaytime.api.platform.PlatformLogger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigService {

    private final Platform platform;
    private final PlatformLogger logger;
    private final CommentedConfigurationNode rootNode;

    final HoconConfigurationLoader loader;

    public ConfigService(Platform platform) {
        this.platform = platform;
        this.logger = platform.logger();

        this.loader = HoconConfigurationLoader.builder()
                .path(platform.configDirectory().resolve("config.conf"))
                .build();

        saveDefaultConfig();
        rootNode = loadConfig();
    }

    private CommentedConfigurationNode loadConfig() {
        CommentedConfigurationNode root;
        try {
            root = loader.load();
            return root;
        } catch (IOException e) {
            logger.error("An error occurred while loading the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().toString());
            }
            logger.error("Falling back to empty configuration. Plugin features may not work correctly until the issue is resolved.");
            return loader.createNode();
        }
    }

    public <T> T getConfigValue(Class<T> type, T defaultValue, Object... path) {
        try {
            return rootNode.node(path).get(type, defaultValue);
        } catch (SerializationException e) {
            logger.error("An error occurred while loading configuration value: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().toString());
            }
        }
        return defaultValue;
    }

    public void setConfigValue(Object value, Object... path) {
        try {
            ConfigurationNode configNode = rootNode.node(path);
            configNode.set(value.getClass(), value);
            saveConfig();
        } catch (SerializationException e) {
            logger.error("An error occurred while loading configuration value: " + e.getMessage());
        }
    }

    public void saveConfig() {
        try {
            loader.save(rootNode);
        } catch (final ConfigurateException e) {
            logger.error("An error occurred while saving the configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().toString());
            }
        }
    }

    public void saveDefaultConfig() {
        Path targetPath = platform.configDirectory().resolve("config.conf");

        if (Files.exists(targetPath)) {return;}

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.conf")) {

            Files.createDirectories(targetPath.getParent());

            if (inputStream != null) {
                Files.copy(inputStream, targetPath);
            }

        } catch (IOException e) {
            logger.error("An error occurred while saving the default configuration: " + e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().toString());
            }
        }
    }

    public Set<String> getRootKeys() {
        return rootNode.childrenMap().keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

}
