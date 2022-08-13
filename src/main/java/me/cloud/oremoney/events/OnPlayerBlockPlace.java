package me.cloud.oremoney.events;

import me.cloud.oremoney.Plugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Arrays;

public class OnPlayerBlockPlace implements Listener {

    private final Plugin plugin;

    public OnPlayerBlockPlace(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Material blockMaterial = block.getBlockData().getMaterial();
        Location blockLocation = block.getLocation();

        if (Arrays.stream(plugin.validOres).toList().contains(blockMaterial)) {
            plugin.addPlacedOre(blockLocation, blockMaterial);
        }
    }
}
