package betterdeathdrop.betterdeathdrop;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public class BetterDeathDrop extends JavaPlugin implements Listener {

    private boolean preventDespawn;
    private int despawnTickTime;

    @Override
    public void onEnable() {
        // Check if the config.yml file is out of date
        if (isOutdatedConfig()) {
            // Save the default config.yml file
            saveDefaultConfig();
        }
        getCommand("debugtickdespawnnear").setExecutor(new DebugTickDespawnNearCommand());

        preventDespawn = getConfig().getBoolean("prevent-despawn");
        despawnTickTime = getConfig().getInt("despawn-tick-time");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Get the player who died
        Player player = event.getEntity();

        // Drop the player's items
        event.getDrops().forEach(itemStack -> player.getWorld().dropItem(player.getLocation(), itemStack).setTicksLived(preventDespawn ? despawnTickTime : 0));
    }

    private boolean isOutdatedConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        // Check if the config.yml file does not exist
        if (!configFile.exists()) {
            return true;
        }

        // Read the config.yml file and check the version
        try {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            String configVersion = config.getString("config-version");
            String currentConfigVersion = getConfig().getString("config-version");
            // Compare the versions and return true if the config.yml file is out of date
            return !configVersion.equals(currentConfigVersion);
        } catch (Exception e) {
            // An error occurred while reading the config.yml file, so return true to save the default config.yml file
            return true;
        }
    }
}
