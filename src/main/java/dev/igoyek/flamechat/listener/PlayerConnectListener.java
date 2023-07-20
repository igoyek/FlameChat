package dev.igoyek.flamechat.listener;

import dev.igoyek.flamechat.ChatPlugin;
import dev.igoyek.flamechat.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectListener implements Listener {

    private final ChatPlugin plugin;

    public PlayerConnectListener(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (this.plugin.getConfig().getString("connect-messages.join") != null) {
            event.setJoinMessage(StringUtil.color(this.plugin.getConfig().getString("connect-messages.join"))
                    .replace("{player}", player.getName()));
        }

        if (!player.hasPlayedBefore()) {
            if (this.plugin.getConfig().getString("connect-messages.first-join") != null) {
                Bukkit.broadcastMessage(StringUtil.color(this.plugin.getConfig().getString("connect-messages.first-join")
                        .replace("{player}", player.getName())));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (this.plugin.getConfig().getString("connect-messages.quit") != null) {
            event.setQuitMessage(StringUtil.color(this.plugin.getConfig().getString("connect-messages.quit"))
                    .replace("{player}", player.getName()));
        }
    }
}
