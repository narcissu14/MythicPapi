package com.narcissu14.mythicpapi;

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
    }

    @Override
    public void onDisable() {

    }

    private boolean checkVersion(){
        int version = Integer.valueOf(Bukkit.getServer().getPluginManager().getPlugin("MythicMobs").getDescription().getVersion().toString().charAt(0));
        if (version >= 4){
            return true;
        }
        return false;
    }
}
