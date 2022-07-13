package com.aranroig.syndriacore.syndriacore.commands;

import com.aranroig.syndriacore.syndriacore.Syndriacore;
import com.aranroig.syndriacore.syndriacore.interfaces.Game;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GotoWorld implements CommandExecutor {

    private Syndriacore plugin;

    public GotoWorld(Syndriacore plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase("goto") || command.getName().equalsIgnoreCase("go") ){
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(args.length == 0){
                    p.sendMessage("Posa un lloc lol");
                } else {
                    String world = args[0];
                    if(world.equalsIgnoreCase("lobby")){
                        p.setFallDistance(0);
                        p.teleport(plugin.spawnpointLocation);
                        Goto(p, plugin.spawnpointLocation);
                    } else {
                        for(Game g : plugin.games){
                           if(g.gameName.equalsIgnoreCase(args[0])){
                               Goto(p, g.spawnLocation);
                               return true;
                           }
                        }
                    }
                }
                return true;
            } else {
                sender.sendMessage("No pots ets una consola gay");
                return true;
            }
        }

        return false;
    }

    private void Goto(Player p, Location loc){

        for(Game g : plugin.games){
            if(g.participants.contains(p)){
                g.participants.remove(p);
                break;
            }
        }

        p.setFallDistance(0);
        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(loc);
    }
}
