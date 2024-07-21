package dev.efekos.flyingChat;

import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class FlyingChat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        World world = e.getPlayer().getWorld();
        TextDisplay entity = world.createEntity(e.getPlayer().getLocation(), TextDisplay.class);
        entity.setAlignment(TextDisplay.TextAlignment.CENTER);
        entity.setBillboard(Display.Billboard.VERTICAL);
        entity.setText(e.getMessage());
        entity.setBackgroundColor(Color.fromARGB(0,0,0,0));
        entity.setTransformation(new Transformation(new Vector3f(),new Quaternionf(),new Vector3f(2,2,2),new Quaternionf()));
        world.addEntity(entity);
        new EntityFlyRunnable(entity).runTaskTimer(this,1,1);

        e.setCancelled(true);
    }

}
