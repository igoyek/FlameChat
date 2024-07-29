package dev.igoyek.flamechat.configuration.implementation;

import dev.igoyek.flamechat.chat.ChatStatus;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Header;

import java.util.List;

@Header("# ")
@Header("# FlameChat configuration")
@Header("# Permissions:")
@Header("# - flamechat.command.* - master permission for the /chat command")
@Header("# - flamechat.reload - allows to reload plugin configuration")
@Header("# - flamechat.bypass.* - bypassing chat restrictions")
@Header("# - flamechat.adminchat - admin chat access")
@Header("# - flamechat.revieveupdates - receive update notifications")
@Header("# ")
public class PluginConfiguration extends OkaeriConfig {

    public ChatStatus defaultChatStatus = ChatStatus.ENABLED;
    public double defaultChatSlowdown = 5.0;

    public AdminChat adminChat = new AdminChat();

    public Censor censor = new Censor();

    public static class AdminChat extends OkaeriConfig {
        public String format = "<dark_gray>[<dark_red><bold>AdminChat<reset><dark_gray>] <red>{PLAYER}<reset>: <gray>{MESSAGE}";
    }

    public static class Censor extends OkaeriConfig {
        public String replacement = "*";
        public List<String> words = List.of("bad", "word");
    }
}
