package me.zombieman.dev.bedrockoffhand.commands;

import me.zombieman.dev.bedrockoffhand.BedrockOffhand;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OffhandAdminCmd implements CommandExecutor, TabCompleter {

    private final BedrockOffhand plugin;

    public OffhandAdminCmd(BedrockOffhand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("bedrockoffhand.command.offhandadmin")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<#FF0000>You don't have permission to run this command!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return false;
        }

        String usageColor = "<#FFFF00>";
        if (args.length != 3) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(usageColor + "Usage: /offhandadmin <setConfig> <showMessages|playSounds> <true|false>"));
            return false;
        }

        String action = args[0];
        String key = args[1];
        String valueStr = args[2];

        if (!action.equalsIgnoreCase("setconfig")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(usageColor + "Usage: /offhandadmin <setConfig> <showMessages|playSounds> <true|false>"));
            return false;
        }

        if (!valueStr.equalsIgnoreCase("true") && !valueStr.equalsIgnoreCase("false")) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>The value: " + valueStr + " is not a valid boolean!"));
            return false;
        }

        boolean value = Boolean.parseBoolean(valueStr);

        switch (key.toLowerCase()) {
            case "showmessages":
                plugin.getConfig().set("showMessages", value);
                plugin.saveConfig();
                player.sendMessage(ChatColor.YELLOW + key + " has been set to: " + value);
                return true;
            case "playsounds":
                plugin.getConfig().set("playSounds", value);
                plugin.saveConfig();
                player.sendMessage(ChatColor.YELLOW + key + " has been set to: " + value);
                return true;
            default:
                player.sendMessage(MiniMessage.miniMessage().deserialize(usageColor + "Usage: /offhandadmin <setConfig> <showMessages|playSounds> <true|false>"));
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("bedrockoffhand.command.offhandadmin")) {
                if (args.length == 1) {
                    completions.add("setConfig");
                } else if (args.length == 2) {
                    completions.add("showMessages");
                    completions.add("playSounds");
                } else if (args.length == 3) {
                    completions.add("true");
                    completions.add("false");
                }
            }
        }

        String lastArg = args[args.length - 1].toLowerCase();
        return completions.stream().filter(s -> s.startsWith(lastArg.toLowerCase())).collect(Collectors.toList());
    }
}
