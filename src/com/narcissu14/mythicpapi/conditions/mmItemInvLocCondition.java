package com.narcissu14.mythicpapi.conditions;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.items.MythicItem;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.ConditionAction;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Optional;

public class mmItemInvLocCondition extends SkillCondition implements IEntityCondition {
    private String itemName;
    private int amount;
    private int slot;

    public mmItemInvLocCondition(String line, MythicLineConfig config) {
        super(line);
        this.itemName = config.getString(new String[]{"i", "item"}, "v:STONE:1");
        this.amount = config.getInteger(new String[]{"a", "amout"}, 1);
        this.slot = config.getInteger(new String[]{"s", "slot"}, 0);
        String act = config.getString(new String[] { "condition", "c" }, "TRUE", new String[0]).toUpperCase();
        this.ACTION = (ConditionAction.isAction(act) ? ConditionAction.valueOf(act) : ConditionAction.TRUE);
    }

    /** invitem{i=<物品名>;a=<数量>;s=<位置>}
     * 物品名以 v: 开头表示原版物品 | null则指代空手
     */
    @Override
    public boolean check(AbstractEntity target) {
        if (target.isPlayer() && target.asPlayer().isOnline()){
            Player player = (Player)target.asPlayer().getBukkitEntity();
            ItemStack invItem = player.getInventory().getItem(slot);
            final ItemStack[] item = new ItemStack[1];
            if (itemName.startsWith("v:")) {
                String[] itemInfo = itemName.split(":");
                item[0] = new ItemStack(Material.getMaterial(itemInfo[1].toUpperCase()), amount, Short.valueOf(itemName.length() == 3 ? itemInfo[2] : "0"));
            } else if (itemName.equalsIgnoreCase("null")) {
                return invItem == null || invItem.getType().equals(Material.AIR);
            } else {
                Optional<MythicItem> mi = MythicMobs.inst().getItemManager().getItem(itemName);
                mi.ifPresent(mythicItem -> item[0] = BukkitAdapter.adapt(mythicItem.generateItemStack(amount)));
            }
            if (invItem != null && !invItem.getType().equals(Material.AIR)) {
                if (invItem.getAmount() >= item[0].getAmount()) {
                    if (invItem.isSimilar(item[0])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
