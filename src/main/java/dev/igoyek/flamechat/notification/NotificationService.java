package dev.igoyek.flamechat.notification;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NotificationService {

    private final Plugin plugin;
    private final AudienceProvider audienceProvider;

    public NotificationService(Plugin plugin, AudienceProvider audienceProvider) {
        this.plugin = plugin;
        this.audienceProvider = audienceProvider;
    }

    public void send(CommandSender sender, Notification notification) {
        Audience audience = this.createAudience(sender);
        notification.send(this.plugin, audience);
    }

    public void broadcast(Notification notification) {
        Audience audience = this.audienceProvider.players();
        notification.send(this.plugin, audience);
    }

    private Audience createAudience(CommandSender sender) {
        if (sender instanceof Player player) {
            return audienceProvider.player(player.getUniqueId());
        }

        return audienceProvider.console();
    }
}
