package dev.igoyek.flamechat.notification.implementation;

import dev.igoyek.flamechat.notification.Notification;
import dev.igoyek.flamechat.shared.ComponentHelper;
import net.kyori.adventure.audience.Audience;
import org.bukkit.plugin.Plugin;

public class ChatNotification implements Notification {

    private final String content;

    public ChatNotification(String content) {
        this.content = content;
    }

    @Override
    public void send(Plugin plugin, Audience audience) {
        audience.sendMessage(ComponentHelper.deserialize(this.content));
    }
}
