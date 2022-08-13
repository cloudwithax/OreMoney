package me.cloud.oremoney;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import me.cloud.oremoney.events.OnMine;
import me.cloud.oremoney.events.OnPlayerBlockPlace;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Plugin extends JavaPlugin {


    private HashMap<Location, Material> placedOres= new HashMap<>();

    public final Material[] validOres = {Material.COAL_ORE, Material.IRON_ORE, Material.COPPER_ORE,
            Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.REDSTONE_ORE,
            Material.EMERALD_ORE, Material.ANCIENT_DEBRIS, Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_IRON_ORE, Material.DEEPSLATE_COPPER_ORE,
            Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_GOLD_ORE, Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_EMERALD_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE};

    public void addPlacedOre(Location location, Material material) {
        placedOres.put(location, material);
    }

    public boolean checkForPlacedOre(Location location, Material material) {
        return placedOres.containsKey(location);
    }

    public void removePlacedOre(Location location, Material material) {
        placedOres.remove(location, material);
    }

    public HashMap<Location, Material> getPlacedOres() {
        return placedOres;
    }

    private Economy econ;

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public Economy getEconomy() {
        return econ;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new OnMine(this), this);
        getServer().getPluginManager().registerEvents(new OnPlayerBlockPlace(this), this);
        if (!setupEconomy()) {
            getLogger().severe("Vault not detected! Please install Vault before using this plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }
        getLogger().info("OreMoney has successfully started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("OreMoney has been disabled, goodbye!");
    }
}
