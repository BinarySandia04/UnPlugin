package com.aranroig.syndriacore.syndriacore.listeners;

import com.aranroig.syndriacore.syndriacore.Syndriacore;
import com.aranroig.syndriacore.syndriacore.interfaces.Game;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    Syndriacore plugin;

    public PlayerConnection(Syndriacore plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void OnPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){
        event.joinMessage(Component.text(event.getPlayer().getName())
                .color(TextColor.color(255, 255, 255))
                .append(Component.text(" has joined the game").
                        color(TextColor.color(133, 253, 69))));

        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().teleport(plugin.spawnpointLocation);
    }

    @EventHandler
    public void OnPlayerLeave(PlayerQuitEvent event){
        event.quitMessage(Component.text(event.getPlayer().getName())
                .color(TextColor.color(255, 255, 255))
                .append(Component.text(" has left the game").
                        color(TextColor.color(253, 80, 72))));

        for(Game i : Syndriacore.instance.games){
            i.PlayerDisconnect(event.getPlayer());
        }
    }
}
