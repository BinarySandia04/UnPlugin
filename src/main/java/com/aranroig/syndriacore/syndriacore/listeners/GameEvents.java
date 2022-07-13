package com.aranroig.syndriacore.syndriacore.listeners;

import com.aranroig.syndriacore.syndriacore.Syndriacore;
import com.aranroig.syndriacore.syndriacore.interfaces.Game;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class GameEvents implements Listener {

    @EventHandler
    public void OnEntityShootBow(EntityShootBowEvent event){
        World w = event.getEntity().getWorld();
        SendEvent(w,event);
    }

    @EventHandler
    public void OnPlayerDamage(EntityDamageEvent event){
        World w = event.getEntity().getWorld();
        SendEvent(w,event);
    }

    @EventHandler
    public void OnProjectileHit(ProjectileHitEvent event){
        World w = event.getEntity().getWorld();
        SendEvent(w,event);
    }

    @EventHandler
    public void OnPlayerRespawn(PlayerRespawnEvent event){
        World w = event.getPlayer().getWorld();
        SendEvent(w,event);
    }

    private void SendEvent(World w, Event event){
        for(Game i : Syndriacore.instance.games){
            if(w == i.world){
                i.Event(event);
                break;
            }
        }
    }

}
