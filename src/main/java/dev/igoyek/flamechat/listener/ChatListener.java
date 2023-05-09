package dev.igoyek.flamechat.listener;

import dev.igoyek.flamechat.ChatPlugin;
import dev.igoyek.flamechat.util.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatListener implements Listener {

    private final ChatPlugin plugin;

    public ChatListener(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (!player.hasPermission("flamechat.bypass.locked") && !this.plugin.getConfig().getBoolean("settings.chat-status")) {
            event.setCancelled(true);
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.locked-alert")));
        }

        if (!player.hasPermission("flamechat.bypass.slow")) {
            if (!this.plugin.getConfig().getBoolean("settings.chat-status")) return;

            int delayTime = this.plugin.getConfig().getInt("settings.slowdown");

            if (delayTime <= 0) return;

            if (this.plugin.getChatCooldowns().containsKey(player.getUniqueId())) {
                long lastMessageTime = this.plugin.getChatCooldowns().get(player.getUniqueId());
                long remainingTime = delayTime - ((System.currentTimeMillis() - lastMessageTime) / 1000);

                if (remainingTime > 0) {
                    event.setCancelled(true);
                    player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.slowdown-alert"))
                            .replace("{time}", String.valueOf(remainingTime)));
                    return;
                }
            }

            this.plugin.getChatCooldowns().put(player.getUniqueId(), System.currentTimeMillis());
        }

        if (!player.hasPermission("flamechat.bypass.censor")) {
            List<String> badWords = this.plugin.getConfig().getStringList("censor.bad-words");
            for (String badWord : badWords) {
                int len = badWord.length();
                String stars = this.plugin.getConfig().getString("censor.replacer").repeat(len);
                message = message.replaceAll("(?i)" + badWord, stars);
            }

            if (!message.equals(event.getMessage())) {
                event.setMessage(message);
            }
        }
    }
}
