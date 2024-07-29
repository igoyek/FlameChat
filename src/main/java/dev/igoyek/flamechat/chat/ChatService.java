package dev.igoyek.flamechat.chat;

import dev.igoyek.flamechat.configuration.implementation.PluginConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatService {

    private ChatStatus chatStatus;
    private double chatDelay;
    private Map<UUID, Double> cooldowns;

    private final PluginConfiguration pluginConfig;

    public ChatService(PluginConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;
        this.cooldowns = new HashMap<>();

        this.setDefaultConfiguration();
    }

    private void setDefaultConfiguration() {
        this.chatStatus = this.pluginConfig.defaultChatStatus;
        this.chatDelay = this.pluginConfig.defaultChatSlowdown;
    }

    public void changeStatus(ChatStatus status) {
        this.chatStatus = status;
    }

    public ChatStatus getChatStatus() {
        return this.chatStatus;
    }

    public void setDelay(double delay) {
        this.chatDelay = delay;
    }

    public double getChatDelay() {
        return this.chatDelay;
    }

    public void setCooldown(UUID uuid) {
        this.cooldowns.put(uuid, System.currentTimeMillis() / 1000.0);
    }

    public double getCooldown(UUID uuid) {
        return this.cooldowns.getOrDefault(uuid, 0.0);
    }
}
