package me.zombieman.dev.bedrockoffhand.managers;

import me.zombieman.dev.bedrockoffhand.BedrockOffhand;
import me.zombieman.dev.bedrockoffhand.commands.OffhandCmd;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Bed;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OffhandManager {

    private static boolean hasOffhand(Player player) {
        return player.getInventory().getItemInOffHand().getType() != Material.AIR;
    }

    private static ItemStack getOffhand(Player player) {
        return player.getInventory().getItemInOffHand();
    }

    private static Component getItemDisplayName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            String displayName = item.getItemMeta().getDisplayName();
            return MiniMessage.miniMessage().deserialize(stripLegacyFormatting(displayName));
        }
        return MiniMessage.miniMessage().deserialize("<yellow>" + item.getType().name().toLowerCase().replace('_', ' '));
    }

    private static String stripLegacyFormatting(String input) {
        return input.replaceAll("(?i)§[0-9a-fklmnoprs]", "");
    }

    public static void switchOffhand(BedrockOffhand plugin, Player player) {
        ItemStack offhandItem = getOffhand(player);
        ItemStack itemToSwitch = player.getInventory().getItemInMainHand();

        if (itemToSwitch.getType() == Material.AIR && !hasOffhand(player)) {
            if (plugin.getConfig().getBoolean("showMessages")) player.sendMessage(MiniMessage.miniMessage().deserialize("<#FF0000>You don't have any items to switch."));
            if (plugin.getConfig().getBoolean("playSounds")) player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            return;
        }

        String green = "<#00FF00>";
        if (itemToSwitch.getType() == Material.AIR && hasOffhand(player)) {
            if (plugin.getConfig().getBoolean("showMessages")) player.sendMessage(MiniMessage.miniMessage().deserialize(green + "Successfully removed <yellow>%itemToRemove% " + green + "from your offhand!")
                    .replaceText(builder -> {
                        builder.matchLiteral("%itemToRemove%");
                        builder.replacement(getItemDisplayName(offhandItem));
                    }));
            if (plugin.getConfig().getBoolean("playSounds")) player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            player.getInventory().setItemInOffHand(null);
            player.getInventory().setItemInMainHand(offhandItem);
            return;
        }

        if (itemToSwitch.getType() != Material.AIR && !hasOffhand(player)) {
            if (plugin.getConfig().getBoolean("showMessages")) player.sendMessage(MiniMessage.miniMessage().deserialize(green + "Successfully added <yellow>%itemToAdd% " + green + "to your offhand!")
                    .replaceText(builder -> {
                        builder.matchLiteral("%itemToAdd%");
                        builder.replacement(getItemDisplayName(itemToSwitch));
                    }));
            if (plugin.getConfig().getBoolean("playSounds")) player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);
            player.getInventory().setItemInMainHand(null);
            player.getInventory().setItemInOffHand(itemToSwitch);
            return;
        }

        if (itemToSwitch.getType() != Material.AIR && hasOffhand(player)) {
            if (plugin.getConfig().getBoolean("showMessages")) player.sendMessage(MiniMessage.miniMessage().deserialize(green + "Successfully replaced <yellow>%itemToReplace% <#00FF00>with <yellow>%itemToReplaceWith%" + green + "!")
                    .replaceText(builder -> {
                        builder.matchLiteral("%itemToReplace%");
                        builder.replacement(getItemDisplayName(itemToSwitch));
                    })
                    .replaceText(builder -> {
                        builder.matchLiteral("%itemToReplaceWith%");
                        builder.replacement(getItemDisplayName(offhandItem));
                    }));
            if (plugin.getConfig().getBoolean("playSounds")) player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 1.0f);

            player.getInventory().setItemInOffHand(itemToSwitch);
            player.getInventory().setItemInMainHand(offhandItem);
            return;
        }
    }
}
