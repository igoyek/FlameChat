package dev.igoyek.flamechat.command;

import dev.igoyek.flamechat.configuration.implementation.MessageConfiguration;
import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.notification.implementation.ActionBarNotification;
import dev.igoyek.flamechat.notification.implementation.ChatNotification;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChatCommand implements CommandExecutor {

    private final NotificationService notificationService;
    private final PluginConfiguration pluginConfig;
    private final MessageConfiguration messageConfig;

    public AdminChatCommand(NotificationService notificationService, PluginConfiguration pluginConfig, MessageConfiguration messageConfig) {
        this.notificationService = notificationService;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            this.notificationService.send(sender, new ChatNotification(this.messageConfig.onlyForPlayers));
            return true;
        }

        if (!player.hasPermission("flamechat.adminchat")) {
            this.notificationService.send(player, new ChatNotification(this.messageConfig.requiredPermission
                    .replace("{PERMISSION}", "flamechat.adminchat")));
            return true;
        }

        if (args.length < 1) {
            this.notificationService.send(player, new ChatNotification(this.messageConfig.invalidUsage
                    .replace("{USAGE}", "/adminchat <message>")));
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String argument : args) {
            message.append(argument).append(" ");
        }

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("flamechat.adminchat")) {
                String formatted = this.pluginConfig.adminChat.format
                        .replace("{PLAYER}", player.getName())
                        .replace("{MESSAGE}", message.toString().trim());

                this.notificationService.send(staff, new ChatNotification(formatted));
                this.notificationService.send(staff, new ActionBarNotification(formatted));
            }
        }
        return true;
    }
}
