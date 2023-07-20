package dev.igoyek.flamechat.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("&(#[a-fA-F0-9]{6})");

    public static String color(String message)
    {
        Matcher matcher = pattern.matcher(message);
        while (matcher.find())
        {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, String.valueOf(ChatColor.of(color)));
            matcher = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private StringUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }
}
