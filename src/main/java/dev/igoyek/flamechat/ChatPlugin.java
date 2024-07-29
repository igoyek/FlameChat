package dev.igoyek.flamechat;

import dev.igoyek.flamechat.chat.ChatService;
import dev.igoyek.flamechat.chat.controller.ChatCensorController;
import dev.igoyek.flamechat.chat.controller.ChatDelayController;
import dev.igoyek.flamechat.chat.controller.ChatStatusController;
import dev.igoyek.flamechat.command.AdminChatCommand;
import dev.igoyek.flamechat.command.ChatCommand;
import dev.igoyek.flamechat.command.ChatCommandTabCompleter;
import dev.igoyek.flamechat.configuration.ConfigurationService;
import dev.igoyek.flamechat.configuration.implementation.MessageConfiguration;
import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.updater.UpdaterService;
import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ChatPlugin extends JavaPlugin {

    private final Logger logger = this.getLogger();

    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        this.softwareCheck();

        new Metrics(this, 18413);

        this.audiences = BukkitAudiences.create(this);
        NotificationService notificationService = new NotificationService(this, audiences);

        ConfigurationService configurationService = new ConfigurationService();
        PluginConfiguration pluginConfig = configurationService.createConfig(PluginConfiguration.class, new File(this.getDataFolder(), "config.yml"));
        MessageConfiguration messageConfig = configurationService.createConfig(MessageConfiguration.class, new File(this.getDataFolder(), "messages.yml"));

        new UpdaterService(this.getDescription());

        Server server = this.getServer();
        PluginManager pluginManager = server.getPluginManager();

        ChatService chatService = new ChatService(pluginConfig);

        this.getCommand("chat").setExecutor(new ChatCommand(notificationService, configurationService, pluginConfig, messageConfig, chatService));
        this.getCommand("chat").setTabCompleter(new ChatCommandTabCompleter());
        this.getCommand("adminchat").setExecutor(new AdminChatCommand(notificationService, pluginConfig, messageConfig));

        Stream.of(
                new ChatCensorController(pluginConfig),
                new ChatDelayController(chatService, notificationService, pluginConfig, messageConfig),
                new ChatStatusController(chatService, notificationService, messageConfig)
        ).forEach(controller -> pluginManager.registerEvents(controller, this));
    }

    @Override
    public void onDisable() {
        if (audiences != null) {
            audiences.close();
        }
    }

    private void softwareCheck() {
        Environment environment = PaperLib.getEnvironment();

        if (!environment.isPaper()) {
            logger.warning("Your server running unsupported software, please use Paper or its forks");
            logger.warning("WARNING: Supported MC versions: 1.16.5 - 1.21");
            return;
        }

        if (!environment.isVersion(19)) {
            logger.warning("Your server running unsupported version, please use 1.16.5 - 1.21");
            return;
        }

        logger.info("Your server is running on supported software and version, congratulations!");
        logger.info("Server version: " + this.getServer().getBukkitVersion());
    }

    public BukkitAudiences getAudiences() {
        return audiences;
    }
}
