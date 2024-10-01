/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 efekos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package dev.efekos.flyingChat;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
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

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        new BukkitRunnable() {

            private final FlyingChat instance = FlyingChat.this;

            @Override
            public void run() {
                Player p = e.getPlayer();
                World world = p.getWorld();
                TextDisplay entity = world.createEntity(p.getLocation().add(0, 2, 0), TextDisplay.class);
                entity.setAlignment(TextDisplay.TextAlignment.CENTER);
                entity.setBillboard(Display.Billboard.CENTER);
                entity.setText(findTextColor(p) + e.getMessage());
                entity.setShadowed(true);
                entity.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
                float s = (float) instance.getConfig().getDouble("text-size");
                entity.setTransformation(new Transformation(new Vector3f(), new Quaternionf(), new Vector3f(s, s, s), new Quaternionf()));
                world.addEntity(entity);
                new EntityFlyRunnable(entity,p,instance).runTaskTimer(instance, 1, 1);
            }
        }.runTaskLater(this, 5);
    }

    public ChatColor findTextColor(Player player) {
        try {
            if (player.isOp()) return ChatColor.valueOf(getConfig().getString("default-op-color", "YELLOW"));
            for (ChatColor color : ALL_COLORS) if (player.hasPermission("flyingchat." + color.name())) return color;
            return ChatColor.valueOf(getConfig().getString("default-color", "WHITE"));
        } catch (Exception e) {
            throw new RuntimeException("FlyingChat configuration is broken, please make sure you put valid inputs to op-color and default-color", e);
        }
    }

}
