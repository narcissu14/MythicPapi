package com.narcissu14.mythicpapi;

import com.narcissu14.mythicpapi.conditions.mmPapiConditionsLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MythicPapi extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            if (checkVersion()) {
                Bukkit.getLogger().info("MythicMobs version valid... Registering custom conditions!");
                Bukkit.getServer().getPluginManager().registerEvents(new mmPapiConditionsLoadEvent(), this);
            }
            else {
                Bukkit.getLogger().info("You need MythicMobs 4.0.0+ in order to use MythicPapi!");
            }
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            throw new RuntimeException("Could not find PlaceholderAPI! MythicPapi can not work without it!");
        } else {
            if (!setupPlaceholders()) {
                Bukkit.getConsoleSender().sendMessage("[MythicPapi] PlaceholderAPI hook failed.");
            }
        }
    }

    @Override
    public void onDisable() {

    }

    private boolean checkVersion(){
        return (int) Bukkit.getServer().getPluginManager().getPlugin("MythicMobs").getDescription().getVersion().charAt(0) >= 4;
    }

    private boolean setupPlaceholders() {
        if (new mmPapiPlaceholders(this, "mythicpapi").hook()) {
            Bukkit.getConsoleSender().sendMessage("[MythicPapi] PlaceholderAPI successfully hooked.");
            return true;
        }
        return false;
    }
}
