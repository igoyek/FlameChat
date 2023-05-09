package dev.igoyek.flamechat.command;

import dev.igoyek.flamechat.ChatPlugin;
import dev.igoyek.flamechat.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChatCommand implements CommandExecutor {

    private final ChatPlugin plugin;

    public AdminChatCommand(ChatPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.is-console")));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("flamechat.adminchat")) {
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.missing-permission"))
                    .replace("{permission}", "flamechat.adminchat"));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("messages.invalid-usage"))
                    .replace("{usage}", "/adminchat <message>"));
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String argument : args) {
            message.append(argument).append(" ");
        }

        for (Player staff : Bukkit.getOnlinePlayers()) {
            if (staff.hasPermission("flamechat.adminchat")) {
                player.sendMessage(StringUtil.color(this.plugin.getConfig().getString("adminchat.format")
                        .replace("{player}", player.getName())
                        .replace("{message}", message.toString())));
            }
        }
        return true;
    }
}
