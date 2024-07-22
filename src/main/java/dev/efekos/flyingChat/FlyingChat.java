package dev.efekos.flyingChat;

import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class FlyingChat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
       new BukkitRunnable(){

           private final FlyingChat instance = FlyingChat.this;

           @Override
           public void run() {
               World world = e.getPlayer().getWorld();
               TextDisplay entity = (TextDisplay) world.spawnEntity(e.getPlayer().getLocation().add(0,2,0), EntityType.TEXT_DISPLAY);
               entity.setAlignment(TextDisplay.TextAlignment.CENTER);
               entity.setBillboard(Display.Billboard.CENTER);
               entity.setText(e.getMessage());
               entity.setShadowed(true);
               entity.setBackgroundColor(Color.fromARGB(0,0,0,0));
               entity.setTransformation(new Transformation(new Vector3f(),new Quaternionf(),new Vector3f(1.5f,1.5f,1.5f),new Quaternionf()));
               new EntityFlyRunnable(entity).runTaskTimer(instance,1,1);

               e.setCancelled(true);
           }
       }.runTaskLater(this,5);
    }

}
