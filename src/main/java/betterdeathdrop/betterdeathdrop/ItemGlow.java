package betterdeathdrop.betterdeathdrop;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class ItemGlow {
    private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    public static void makeItemGlow(ItemStack itemStack, Player player) {
        World world = player.getWorld();
        Item item = world.dropItem(player.getLocation(), itemStack);

        // Create a packet to add the glowing effect to the item
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EFFECT);
        // Set the entity ID of the item
        packet.getIntegers().write(0, item.getEntityId());
        // Set the effect ID to 21 (glowing effect)
        packet.getIntegers().write(1, 21);
        // Set the amplifier to 1 (strength of the effect)
        packet.getIntegers().write(2, 1);
        // Set the duration to 1 tick (the effect will last for 1 tick)
        packet.getIntegers().write(3, 1);
        // Set the flags to 0 (no special behavior)
        packet.getIntegers().write(4, 0);

        try {
            // Send the packet to the player
            protocolManager.sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}