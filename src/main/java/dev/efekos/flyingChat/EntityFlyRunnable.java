package dev.efekos.flyingChat;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityFlyRunnable extends BukkitRunnable {

    private Entity entity;

    public EntityFlyRunnable(Entity entity) {
        this.entity = entity;
    }

    private int ticks = 0;

    @Override
    public void run() {
        if(++ticks>60){
            cancel();
            return;
        }
        entity.teleport(entity.getLocation().add(0,.1,0));
    }
}
