package dev.igoyek.flamechat.notification;

import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.Plugin;

public interface Notification {

    void send(Plugin plugin, Audience audience);
}
