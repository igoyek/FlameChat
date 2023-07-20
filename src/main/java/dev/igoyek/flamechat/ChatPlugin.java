package dev.igoyek.flamechat;

import dev.igoyek.flamechat.command.AdminChatCommand;
import dev.igoyek.flamechat.command.ChatCommand;
import dev.igoyek.flamechat.command.ChatCommandTabCompleter;
import dev.igoyek.flamechat.listener.ChatListener;
import dev.igoyek.flamechat.listener.PlayerConnectListener;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Level;

public class ChatPlugin extends JavaPlugin {

    private ChatPlugin plugin;

    private Metrics metrics;

    private Map<UUID, Long> chatCooldowns;

    @Override
    public void onLoad() {
        this.getLogger().log(Level.INFO, "Starting metrics...");
        this.metrics = new Metrics(this, 18413);
        this.getLogger().log(Level.INFO, "Loading configuration...");
        this.saveDefaultConfig();
        this.reloadConfig();
    }

    @Override
    public void onEnable() {
        this.plugin = this;
        this.chatCooldowns = new HashMap<UUID, Long>();
        this.getLogger().log(Level.INFO, "Registering command...");
        this.getCommand("chat").setExecutor(new ChatCommand(this));
        this.getCommand("chat").setTabCompleter(new ChatCommandTabCompleter());
        this.getCommand("adminchat").setExecutor(new AdminChatCommand(this));
        this.getLogger().log(Level.INFO, "Registering listeners...");
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this);
        pluginManager.registerEvents(new PlayerConnectListener(this), this);
    }

    public ChatPlugin getPlugin() {
        return this.plugin;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public Map<UUID, Long> getChatCooldowns() {
        return this.chatCooldowns;
    }
}
