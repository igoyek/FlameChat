package dev.igoyek.flamechat.command;

import dev.igoyek.flamechat.ChatPlugin;
import dev.igoyek.flamechat.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {

    private final ChatPlugin plugin;

    public ChatCommand(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.is-console")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("flamechat.command.chat")) {
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                    .replace("{permission}", "flamechat.command.chat"));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-usage"))
                    .replace("{usage}", "/chat <enable/disable/slow/reload> [time]"));
            return true;
        }

        if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("on")) {
            if (!player.hasPermission("flamechat.command.chat.enable")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                        .replace("{permission}", "flamechat.command.chat.enable"));
                return true;
            }

            if (this.plugin.getConfig().getBoolean("settings.chat-status")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-already-enabled")));
                return true;
            }

            this.plugin.getConfig().set("settings.chat-status", true);
            this.plugin.saveConfig();

            for (Player online : Bukkit.getOnlinePlayers()) online.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-enabled")));
        } else if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("off")) {
            if (!player.hasPermission("flamechat.command.chat.disable")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                        .replace("{permission}", "flamechat.command.chat.disable"));
                return true;
            }

            if (!this.plugin.getConfig().getBoolean("settings.chat-status")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-already-disabled")));
                return true;
            }

            this.plugin.getConfig().set("settings.chat-status", false);
            this.plugin.saveConfig();

            for (Player online : Bukkit.getOnlinePlayers())
                online.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-disabled")));

        } else if (args[0].equalsIgnoreCase("clear") || args[0].equalsIgnoreCase("c")) {
            if (!player.hasPermission("flamechat.command.chat.clear")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                        .replace("{permission}", "flamechat.command.chat.clear"));
                return true;
            }
            for (int i = 0; i < 100; i++) {
                for (Player online : Bukkit.getOnlinePlayers()) online.sendMessage("");
            }
            Bukkit.broadcastMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-cleared")));
        } else if (args[0].equalsIgnoreCase("slow")) {
            if (!player.hasPermission("flamechat.command.chat.slow")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                        .replace("{permission}", "flamechat.command.chat.slow"));
                return true;
            }

            if (args.length < 2) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-usage"))
                        .replace("{usage}", "/chat slow <time>"));
                player.sendTitle("", "", 1, 5, 1);
                return true;
            }

            int time;
            try {
                time = Integer.parseInt(args[1]);
            } catch (NumberFormatException exception) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-time")));
                return true;
            }

            if (time < 0) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-time")));
                return true;
            }

            this.plugin.getConfig().set("settings.slowdown", time);
            this.plugin.saveConfig();

            for (Player online : Bukkit.getOnlinePlayers())
                online.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.chat-slowed"))
                        .replace("{time}", String.valueOf(time)));
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("flamechat.reload")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                        .replace("{permission}", "flamechat.reload"));
                return true;
            }

            this.plugin.reloadConfig();
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.reload-success")));
        } else {
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-usage"))
                    .replace("{usage}", "/chat <enable/disable/slow/reload> [time]"));
        }

        return false;
    }
}
