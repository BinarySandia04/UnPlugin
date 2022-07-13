package com.aranroig.syndriacore.syndriacore.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Print {
    public static void GlobalBroadcast(Component message){
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendMessage(message);
        }
    }

    public static void Debug(Player p, String message){
        p.sendMessage(Component.text(message).color(TextColor.color(246, 255, 126)));
    }

    public static void Debug(String message){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Component.text(message).color(TextColor.color(246, 255, 126)));
        }
    }

    public static void DebugError(String message){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Component.text(message).color(TextColor.color(255, 0, 12)));
        }
    }

    public static void Message(Player p, String message){
        p.sendMessage(Component.text(message).color(TextColor.color(255, 195, 111)));
    }

    public static void IntLocation(Player p, Location loc){
        p.sendMessage(Component.text("X: ").color(TextColor.color(185, 185, 181))
                .append(Component.text(loc.getBlockX()).color(TextColor.color(255,0,0)))
                .append(Component.text(" Y: ").color(TextColor.color(185, 185, 181)))
                .append(Component.text(loc.getBlockY()).color(TextColor.color(133, 253, 69)))
                .append(Component.text(" Z: ").color(TextColor.color(185, 185, 181)))
                .append(Component.text(loc.getBlockZ()).color(TextColor.color(134, 131, 255)))
        );
    }

    public static void Error(Player p, String message){
        p.sendMessage(Component.text(message).color(TextColor.color(255, 67, 57)));
    }

    public static void Usage(Player p, String command, HashMap<String,String> args){
        p.sendMessage(Component.text("--- Comanda " + command + " ---").color(TextColor.color(156, 218, 237)));

        for (String key : args.keySet()) {
            p.sendMessage(Component.text("/" + command + " " + key).color(TextColor.color(48, 149, 237))
                    .append(Component.text(" - " + args.get(key)).color(TextColor.color(255,255,255))));
        }

        p.sendMessage(Component.text("----------------").color(TextColor.color(156, 218, 237)));
    }
}
