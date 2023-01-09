package betterdeathdrop.betterdeathdrop;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class BetterDeathDrop extends JavaPlugin implements Listener {

    private boolean preventDespawn;
    private boolean makeGlowing;
    private boolean showHologram;
    private int despawnTickTime;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        preventDespawn = getConfig().getBoolean("prevent-despawn");
        makeGlowing = getConfig().getBoolean("make-glowing");
        showHologram = getConfig().getBoolean("show-hologram");
        despawnTickTime = getConfig().getInt("despawn-tick-time");
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (showHologram) {
            Location loc = event.getEntity().getLocation();
            World world = event.getEntity().getWorld();
            ArmorStand armorStand = (ArmorStand) world.spawnEntity(loc, EntityType.ARMOR_STAND);
            armorStand.setCustomName(ChatColor.GOLD + "Item owner: " + event.getEntity().getName());
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);
            armorStand.setVisible(false);
        }
        for (ItemStack itemStack : event.getDrops()) {
            Item item = event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), itemStack);
            if (preventDespawn) {
                item.setTicksLived(despawnTickTime);
            }
            if (makeGlowing) {
                ItemGlow.makeItemGlow(itemStack, event.getEntity());
            }
        }
    }
}