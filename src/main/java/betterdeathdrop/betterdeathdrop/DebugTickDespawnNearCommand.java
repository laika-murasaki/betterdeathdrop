package betterdeathdrop.betterdeathdrop;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;


public class DebugTickDespawnNearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }
        if (!sender.hasPermission("betterdeathdrop.debugtickdespawnnear")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /bdd debugtickdespawnnear <radius>");
            return true;
        }
        try {
            int radius = Integer.parseInt(args[0]);
            Player player = (Player) sender;
            int count = 0;
            for (Item item : player.getWorld().getEntitiesByClass(Item.class)) {
                if (item.getLocation().distance(player.getLocation()) <= radius) {
                    sender.sendMessage(String.format("Item at %d,%d,%d has despawn tick %d and second %.1f",
                            item.getLocation().getBlockX(), item.getLocation().getBlockY(), item.getLocation().getBlockZ(),
                            item.getTicksLived(), item.getTicksLived() / 20.0));
                    count++;
                }
            }
            if (count == 0) {
                sender.sendMessage(ChatColor.RED + "No nearby items found.");
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Error: radius must be a number.");
        }
        return true;
    }
}
