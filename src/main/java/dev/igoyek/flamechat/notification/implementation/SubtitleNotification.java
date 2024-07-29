package dev.igoyek.flamechat.notification.implementation;

import dev.igoyek.flamechat.notification.Notification;
import dev.igoyek.flamechat.shared.ComponentHelper;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.plugin.Plugin;

public class SubtitleNotification implements Notification {

    private final String content;

    public SubtitleNotification(String message) {
        this.content = message;
    }

    @Override
    public void send(Plugin plugin, Audience audience) {
        audience.showTitle(Title.title(Component.empty(), ComponentHelper.deserialize(this.content)));
    }
}
