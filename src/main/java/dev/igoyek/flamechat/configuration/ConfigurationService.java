package dev.igoyek.flamechat.configuration;

import eu.okaeri.configs.OkaeriConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationService {

    private final Set<OkaeriConfig> configs = new HashSet<>();

    public <T extends OkaeriConfig> T createConfig(@NotNull Class<T> configClass, @NotNull File file) {
        T configFile = ConfigurationFactory.createConfig(configClass, file);

        configFile
                .saveDefaults()
                .load(true);

        this.configs.add(configFile);

        return configFile;
    }

    public void reloadAllConfigs() {
        this.configs.forEach(OkaeriConfig::load);
    }

    public void saveAllConfigs() {
        this.configs.forEach(OkaeriConfig::save);
    }
}
