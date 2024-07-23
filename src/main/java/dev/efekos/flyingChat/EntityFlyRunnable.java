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

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class EntityFlyRunnable extends BukkitRunnable {

    private final TextDisplay entity;
    private final Player sender;
    private final FlyingChat flyingChat;
    private int ticks = 0;

    public EntityFlyRunnable(TextDisplay entity, Player sender,FlyingChat i) {
        this.entity = entity;
        this.sender = sender;
        flyingChat = i;
    }

    @Override
    public void run() {
        if (++ticks > 60) {
            entity.remove();
            cancel();
            return;
        }
        entity.setTextOpacity((byte) (entity.getTextOpacity() - 4));
        entity.teleport(entity.getLocation().add(0, .1, 0));
        spawnGlow();
    }

    public void spawnGlow(){
        if(!sender.hasPermission("flyingchat.glow"))return;
        Color color = flyingChat.findTextColor(sender).asBungee().getColor();
        org.bukkit.Color bukkitColor = org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());

        float size = entity.getTransformation().getScale().x;

        entity.getWorld().spawnParticle(Particle.DUST,entity.getLocation(),1,size,size,size,new Particle.DustOptions(bukkitColor,(float) Math.random()*1.5f));
    }

}
