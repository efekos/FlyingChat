package dev.efekos.flyingChat;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.checkerframework.checker.units.qual.C;
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
        e.setCancelled(true);
       new BukkitRunnable(){

           private final FlyingChat instance = FlyingChat.this;

           @Override
           public void run() {
               Player p = e.getPlayer();
               World world = p.getWorld();
               TextDisplay entity = (TextDisplay) world.spawnEntity(p.getLocation().add(0,2,0), EntityType.TEXT_DISPLAY);
               entity.setAlignment(TextDisplay.TextAlignment.CENTER);
               entity.setBillboard(Display.Billboard.CENTER);
               entity.setText(findColor(p)+e.getMessage());
               entity.setShadowed(true);
               entity.setBackgroundColor(Color.fromARGB(0,0,0,0));
               entity.setTransformation(new Transformation(new Vector3f(),new Quaternionf(),new Vector3f(1.5f,1.5f,1.5f),new Quaternionf()));
               new EntityFlyRunnable(entity).runTaskTimer(instance,1,1);
           }
       }.runTaskLater(this,5);
    }

    private static final ChatColor[] ALL_COLORS = new ChatColor[]{
            ChatColor.YELLOW,
            ChatColor.GOLD,
            ChatColor.AQUA,
            ChatColor.RED,
            ChatColor.BLUE,
            ChatColor.DARK_AQUA,
            ChatColor.DARK_BLUE,
            ChatColor.DARK_GRAY,
            ChatColor.DARK_GREEN,
            ChatColor.DARK_PURPLE,
            ChatColor.DARK_RED,
            ChatColor.GREEN,
            ChatColor.BLACK,
            ChatColor.LIGHT_PURPLE
    };

    private Color findColor(Player player) {
        ChatColor color = findTextColor(player);
        java.awt.Color c = color.asBungee().getColor();
        return Color.fromRGB(c.getRed(),c.getGreen(),c.getBlue());
    }

    private ChatColor findTextColor(Player player){
        for (ChatColor color : ALL_COLORS) if(player.hasPermission("flyingchat."+color.name())) return color;
        return ChatColor.WHITE;
    }

}
