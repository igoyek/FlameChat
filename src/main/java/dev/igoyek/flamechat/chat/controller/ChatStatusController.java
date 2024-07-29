package dev.igoyek.flamechat.chat.controller;

import dev.igoyek.flamechat.chat.ChatService;
import dev.igoyek.flamechat.configuration.implementation.MessageConfiguration;
import dev.igoyek.flamechat.notification.NotificationService;
import dev.igoyek.flamechat.notification.implementation.ChatNotification;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatStatusController implements Listener {

    private final ChatService chatService;
    private final NotificationService notificationService;
    private final MessageConfiguration messageConfig;

    public ChatStatusController(ChatService chatService, NotificationService notificationService, MessageConfiguration messageConfig) {
        this.chatService = chatService;
        this.notificationService = notificationService;
        this.messageConfig = messageConfig;
    }

    @Deprecated
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        switch (this.chatService.getChatStatus()) {
            case DISABLED -> {
                if (player.hasPermission("flamechat.bypass.disabled")) {
                    return;
                }

                event.setCancelled(true);
                this.notificationService.send(player, new ChatNotification(this.messageConfig.chatIsDisabled));
            }

            case PREMIUM -> {
                if (player.hasPermission("flamechat.bypass.premium")) {
                    return;
                }

                event.setCancelled(true);
                this.notificationService.send(player, new ChatNotification(this.messageConfig.chatIsPremium));
            }
        }
    }
}