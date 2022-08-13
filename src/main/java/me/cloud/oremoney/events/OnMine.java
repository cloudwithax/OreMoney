package me.cloud.oremoney.events;

import me.cloud.oremoney.Plugin;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OnMine implements Listener {

    private final Plugin plugin;

    public OnMine(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockMaterial = block.getBlockData().getMaterial();
        Location blockLocation = block.getLocation();
        if (Arrays.asList(plugin.validOres).contains(blockMaterial)) {
            if (!plugin.checkForPlacedOre(blockLocation, blockMaterial)) {
                int randomAmount = ThreadLocalRandom.current().nextInt(1, 3 + 1);
                plugin.getEconomy().depositPlayer(player, randomAmount);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aAdded $" + randomAmount + " to your balance"));
            } else {
                plugin.removePlacedOre(blockLocation, blockMaterial);
            }
        }
    }
}
