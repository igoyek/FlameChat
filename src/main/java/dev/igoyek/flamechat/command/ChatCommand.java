package dev.igoyek.flamechat.command;

import dev.igoyek.flamechat.chat.ChatService;
import dev.igoyek.flamechat.chat.ChatStatus;
import dev.igoyek.flamechat.configuration.ConfigurationService;
import dev.igoyek.flamechat.configuration.implementation.MessageConfiguration;
import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.notification.implementation.ChatNotification;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import panda.utilities.text.Formatter;

public class ChatCommand implements CommandExecutor {

    private final NotificationService notificationService;
    private final ConfigurationService configService;
    private final PluginConfiguration pluginConfig;
    private final MessageConfiguration messageConfig;
    private final ChatService chatService;

    public ChatCommand(NotificationService notificationService, ConfigurationService configService, PluginConfiguration pluginConfig, MessageConfiguration messageConfig, ChatService chatService) {
        this.notificationService = notificationService;
        this.configService = configService;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
        this.chatService = chatService;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            this.notificationService.send(sender, new ChatNotification(this.messageConfig.onlyForPlayers));
            return true;
        }

        if (args.length == 0) {
            Formatter formatter = new Formatter().register("{USAGE}", "/chat <enable|disable|premium|clear|slow> [delay]");

            this.notificationService.send(player, new ChatNotification(formatter.format(this.messageConfig.invalidUsage)));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "enable" -> {
                if (this.chatService.getChatStatus() == ChatStatus.ENABLED) {
                    this.notificationService.send(player, new ChatNotification(this.messageConfig.chatStatusCannotBeTheSame));
                    return true;
                }

                Formatter formatter = new Formatter().register("{STATUS}", this.messageConfig.enabledStatus);

                this.chatService.changeStatus(ChatStatus.ENABLED);
                this.notificationService.broadcast(new ChatNotification(formatter.format(this.messageConfig.chatStatusChanged)));
            }
            case "disable" -> {
                if (this.chatService.getChatStatus() == ChatStatus.DISABLED) {
                    this.notificationService.send(player, new ChatNotification(this.messageConfig.chatStatusCannotBeTheSame));
                    return true;
                }

                Formatter formatter = new Formatter().register("{STATUS}", this.messageConfig.disabledStatus);

                this.chatService.changeStatus(ChatStatus.DISABLED);
                this.notificationService.broadcast(new ChatNotification(formatter.format(this.messageConfig.chatStatusChanged)));
            }
            case "premium" -> {
                if (this.chatService.getChatStatus() == ChatStatus.PREMIUM) {
                    this.notificationService.send(player, new ChatNotification(this.messageConfig.chatStatusCannotBeTheSame));
                    return true;
                }

                Formatter formatter = new Formatter().register("{STATUS}", this.messageConfig.premiumStatus);

                this.chatService.changeStatus(ChatStatus.PREMIUM);
                this.notificationService.broadcast(new ChatNotification(formatter.format(this.messageConfig.chatStatusChanged)));
            }
            case "clear" -> {
                for (int i = 0; i < 100; i++) {
                    this.notificationService.broadcast(new ChatNotification(" "));
                }

                this.notificationService.broadcast(new ChatNotification(this.messageConfig.chatCleared));
            }
            case "slow" -> {
                if (args.length != 2) {
                    Formatter formatter = new Formatter().register("{USAGE}", "/chat slow <delay>");

                    this.notificationService.send(player, new ChatNotification(formatter.format(this.messageConfig.invalidUsage)));

                    return true;
                }

                try {
                    double delay = Double.parseDouble(args[1]);
                    this.chatService.setDelay(delay);

                    Formatter formatter = new Formatter().register("{DELAY}", String.valueOf(delay));

                    this.notificationService.broadcast(new ChatNotification(formatter.format(this.messageConfig.chatSlowed)));
                } catch (NumberFormatException exception) {
                    this.notificationService.send(player, new ChatNotification(this.messageConfig.argumentMustBeNumber));
                }
            }
            case "reload" -> {
                this.configService.reloadAllConfigs();
                this.notificationService.send(player, new ChatNotification(this.messageConfig.configReloaded));
            }
            default -> {
                Formatter formatter = new Formatter().register("{USAGE}", "/chat <enable|disable|premium|clear|slow> [delay]");

                this.notificationService.send(player, new ChatNotification(formatter.format(this.messageConfig.invalidUsage)));
            }
        }
        return false;
    }
}
