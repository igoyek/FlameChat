package dev.igoyek.flamechat.configuration;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigurationFactory {

    public static <T extends OkaeriConfig> T createConfig(@NotNull Class<T> configClass, @NotNull File file) {
        T configFile = ConfigManager.create(configClass);

        configFile
                .withConfigurer(new YamlBukkitConfigurer())
                .withBindFile(file);

        return configFile;
    }
}
