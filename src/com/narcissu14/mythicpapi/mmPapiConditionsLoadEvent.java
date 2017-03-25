package com.narcissu14.mythicpapi;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicConditionLoadEvent;
import io.lumine.xikage.mythicmobs.skills.SkillCondition;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class mmPapiConditionsLoadEvent implements Listener {
    @EventHandler
    public void papiConditionsLoader(MythicConditionLoadEvent e) {
        if (e.getConditionName().equalsIgnoreCase("papi")) {
            SkillCondition sc = new mmPapiCondition(e.getConfig().getLine(), e.getConfig());
            e.register(sc);
        }
    }
}
