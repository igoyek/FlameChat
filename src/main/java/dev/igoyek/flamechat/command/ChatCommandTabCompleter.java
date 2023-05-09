package dev.igoyek.flamechat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.LinkedList;
import java.util.List;

public class ChatCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("flamechat.command.chat")) return null;

        List<String> completions = new LinkedList<>();
        if (args.length == 1) {
            String argument = args[0].toLowerCase();
            if ("enable".startsWith(argument)) completions.add("enable");
            if ("disable".startsWith(argument)) completions.add("disable");
            if ("slow".startsWith(argument)) completions.add("slow");
            if ("clear".startsWith(argument)) completions.add("clear");
            if ("reload".startsWith(argument)) completions.add("reload");
        }
        return completions;
    }
}
