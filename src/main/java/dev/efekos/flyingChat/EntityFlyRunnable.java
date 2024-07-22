package dev.efekos.flyingChat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityFlyRunnable extends BukkitRunnable {

    private final TextDisplay entity;

    public EntityFlyRunnable(TextDisplay entity) {
        this.entity = entity;
    }

    private int ticks = 0;

    @Override
    public void run() {
        if(++ticks>60){
            entity.remove();
            cancel();
            return;
        }
        entity.setTextOpacity((byte) (entity.getTextOpacity()-4));
        entity.teleport(entity.getLocation().add(0,.1,0));
    }
}
