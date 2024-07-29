package dev.igoyek.flamechat.shared;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ComponentHelper {

    private static final CharSequence LEGACY_CHAR = "&";

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    private ComponentHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Component deserialize(String text) {
        return text.contains(LEGACY_CHAR)
                ? LEGACY_COMPONENT_SERIALIZER.deserialize(text)
                : MINI_MESSAGE.deserialize(text);
    }
}
