package me.zombieman.dev.bedrockoffhand.commands;

import me.zombieman.dev.bedrockoffhand.BedrockOffhand;
import me.zombieman.dev.bedrockoffhand.managers.OffhandManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class OffhandCmd implements CommandExecutor {

    private final BedrockOffhand plugin;

    public OffhandCmd(BedrockOffhand plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        OffhandManager.switchOffhand(plugin, player);

        return true;
    }
}
