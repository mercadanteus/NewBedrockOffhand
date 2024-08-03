package me.zombieman.dev.bedrockoffhand;

import me.zombieman.dev.bedrockoffhand.commands.OffhandAdminCmd;
import me.zombieman.dev.bedrockoffhand.commands.OffhandCmd;
import org.bukkit.plugin.java.JavaPlugin;

public final class BedrockOffhand extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getCommand("offhand").setExecutor(new OffhandCmd(this));
        getCommand("offhandadmin").setExecutor(new OffhandAdminCmd(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
