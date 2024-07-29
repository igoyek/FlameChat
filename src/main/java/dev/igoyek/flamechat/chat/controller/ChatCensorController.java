package dev.igoyek.flamechat.chat.controller;

import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatCensorController implements Listener {

    private final PluginConfiguration pluginConfig;

    public ChatCensorController(PluginConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;
    }

    @Deprecated
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!player.hasPermission("flamechat.bypass.censor")) {
            List<String> badWords = this.pluginConfig.censor.words;
            for (String badWord : badWords) {
                int length = badWord.length();
                String replacement = this.pluginConfig.censor.replacement.repeat(length);
                message = message.replaceAll("(?i)" + badWord, replacement);
            }

            if (!message.equals(event.getMessage())) {
                event.setMessage(message);
            }
        }
    }
}
