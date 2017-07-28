package com.narcissu14.mythicpapi.conditions;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.adapters.AbstractPlayer;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.ConditionAction;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class mmPapiRangeCondition extends SkillCondition implements IEntityCondition {
    private String placeholderLeft;
    private String operator;
    private String placeholderRight;
    private int radius;
    private boolean matchAll;

    public mmPapiRangeCondition(String line, MythicLineConfig config) {
        super(line);
        this.placeholderLeft = config.getString(new String[]{"pl", "papileft"}, "%player_name%");
        this.operator = config.getString(new String[]{"operator", "o"}, "==").replace("<&eq>", "=");
        this.placeholderRight = config.getString(new String[]{"pr", "papiright"}, "Narcissu14");
        this.radius = config.getInteger(new String[]{"r", "radius"}, 3);
        this.matchAll = config.getBoolean(new String[]{"ma", "matchall"}, false);
        String act = config.getString(new String[] { "condition", "c" }, "TRUE", new String[0]).toUpperCase();
        this.ACTION = (ConditionAction.isAction(act) ? ConditionAction.valueOf(act) : ConditionAction.TRUE);
    }

    @Override
    public boolean check(AbstractEntity target) {
        List<AbstractPlayer> abstractPlayers = target.getWorld().getPlayersNearLocation(target.getLocation(), radius);
        if (!abstractPlayers.isEmpty()) {
            for (AbstractPlayer ap : abstractPlayers) {
                if (ap.isPlayer() && ap.asPlayer().isOnline()){
                    Player player = (Player)ap.asPlayer().getBukkitEntity();
                    String newPL = PlaceholderAPI.setPlaceholders(player, this.placeholderLeft);
                    String newPR = PlaceholderAPI.setPlaceholders(player, this.placeholderRight);
                    if (this.matchAll) {
                        if (!checkPlaceholder(newPL, newPR)) {
                            return false;
                        }
                    } else {
                        if (checkPlaceholder(newPL, newPR)) {
                            return true;
                        }
                    }
                }
            }
            return this.matchAll;
        }
        return false;
    }

    private boolean checkPlaceholder(String newPL, String newPR){
        double numPL;
        double numPR;
        try {
            numPL = Double.parseDouble(newPL);
            numPR = Double.parseDouble(newPR);
        } catch (Exception e){
            return false;
        }
        switch (this.operator){
            case "==":
                return newPL.equals(newPR);
            case "!=":
                return !newPL.equals(newPR);
            case "<=":
                return (numPL <= numPR);
            case ">=":
                return (numPL >= numPR);
            case "<":
                return (numPL < numPR);
            case ">":
                return (numPL > numPR);
            default:
                break;
        }
        return false;
    }
}
