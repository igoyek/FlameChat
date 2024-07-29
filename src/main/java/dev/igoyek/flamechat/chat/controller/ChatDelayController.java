package dev.igoyek.flamechat.chat.controller;

import dev.igoyek.flamechat.chat.ChatService;
import dev.igoyek.flamechat.configuration.implementation.MessageConfiguration;
import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.notification.implementation.ChatNotification;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import panda.utilities.text.Formatter;

import java.util.UUID;

public class ChatDelayController implements Listener {

    private final ChatService chatService;
    private final NotificationService notificationService;
    private final PluginConfiguration pluginConfig;
    private final MessageConfiguration messageConfig;

    public ChatDelayController(ChatService chatService, NotificationService notificationService, PluginConfiguration pluginConfig, MessageConfiguration messageConfig) {
        this.chatService = chatService;
        this.notificationService = notificationService;
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Deprecated
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("flamechat.bypass.slow")) {
            double delay = this.chatService.getChatDelay();

            if (delay <= 0) return;

            UUID playerUniqueId = player.getUniqueId();
            double lastMessageTime = this.chatService.getCooldown(playerUniqueId);

            double currentTime = System.currentTimeMillis() / 1000.0;
            double elapsedTime = currentTime - lastMessageTime;

            if (elapsedTime < delay) {
                double remainingTime = delay - elapsedTime;
                event.setCancelled(true);

                String formattedTime = String.format("%.2f", remainingTime);
                Formatter formatter = new Formatter().register("{DELAY}", formattedTime);

                this.notificationService.send(player, new ChatNotification(formatter.format(this.messageConfig.chatIsSlowed)));
                return;
            }

            this.chatService.setCooldown(playerUniqueId);
        }
    }
}
