package dev.igoyek.flamechat.notification.implementation;

import dev.igoyek.flamechat.notification.Notification;
import dev.igoyek.flamechat.shared.ComponentHelper;
import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.Plugin;

public class ActionBarNotification implements Notification {

    private final String content;

    public ActionBarNotification(String message) {
        this.content = message;
    }

    @Override
    public void send(Plugin plugin, Audience audience) {
        audience.sendActionBar(ComponentHelper.deserialize(this.content));
    }
}
