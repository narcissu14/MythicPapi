package com.narcissu14.mythicpapi;

import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import io.lumine.xikage.mythicmobs.skills.conditions.ConditionAction;
import io.lumine.xikage.mythicmobs.skills.conditions.IEntityCondition;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class mmPapiCondition extends SkillCondition implements IEntityCondition {
    private String placeholderLeft;
    private String operator;
    private String placeholderRight;

    public mmPapiCondition(String line, MythicLineConfig config) {
        super(line);
        this.placeholderLeft = config.getString(new String[]{"pl", "papileft"}, "%player_name%");
        this.operator = config.getString(new String[]{"operator", "o"}, "==").replace("<&eq>", "=");
        this.placeholderRight = config.getString(new String[]{"pr", "papiright"}, "Narcissu14");
        String act = config.getString(new String[] { "condition", "c" }, "TRUE", new String[0]).toUpperCase();
        this.ACTION = (ConditionAction.isAction(act) ? ConditionAction.valueOf(act) : ConditionAction.TRUE);
    }

    @Override
    public boolean check(AbstractEntity target) {
        if (target.isPlayer() && target.asPlayer().isOnline()){
            Player player = (Player)target.asPlayer().getBukkitEntity();
            String newPL = PlaceholderAPI.setPlaceholders(player, this.placeholderLeft);
            String newPR = PlaceholderAPI.setPlaceholders(player, this.placeholderRight);
            switch (this.operator){
                case "==":
                    if (newPL.equals(newPR)){
                        return true;
                    }
                    return false;
                case "!=":
                    if (!newPL.equals(newPR)){
                        return true;
                    }
                    return false;
                default:
                    return checkPlaceholder(newPL, newPR);
            }
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
            case "<=":
                return (numPL <= numPR) ? true : false;
            case ">=":
                return (numPL >= numPR) ? true : false;
            case "<":
                return (numPL < numPR) ? true : false;
            case ">":
                return (numPL > numPR) ? true : false;
            default:
                break;
        }
        return false;
    }
}
