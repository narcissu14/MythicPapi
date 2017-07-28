package com.narcissu14.mythicpapi;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class mmPapiPlaceholders extends EZPlaceholderHook {

    public mmPapiPlaceholders(Plugin plugin, String identifier) {
        super(plugin, identifier);
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if (player == null) {
            return "";
        }
        if (s.startsWith("hasitem_")) {
            // %mythicpapi_hasitem_<MythicItemName>_<amount>%
            s = s.replace("hasitem_", "");
            String[] itemInfo = s.split("_");
            int amount;
            try {
                amount = Integer.parseInt(itemInfo[1]);
            } catch (Exception e) {
                return PlaceholderAPIPlugin.booleanFalse();
            }
            ItemStack item = BukkitAdapter.adapt(MythicMobs.inst().getItemManager().getItem(itemInfo[0]).get().generateItemStack(amount));
            for (ItemStack invItem : player.getInventory().getContents()) {
                if (invItem == null || invItem.getType() == Material.AIR) {
                    continue;
                }
                if (invItem.isSimilar(item)) {
                    if (invItem.getAmount() < item.getAmount()) {
                        amount -= invItem.getAmount();
                        if (amount <= 0) {
                            return PlaceholderAPIPlugin.booleanTrue();
                        }
                    } else {
                        return PlaceholderAPIPlugin.booleanTrue();
                    }
                }
            }
            return PlaceholderAPIPlugin.booleanFalse();
        }
        return s;
    }
}
