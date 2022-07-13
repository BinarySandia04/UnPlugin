package com.aranroig.syndriacore.syndriacore.commands;

import com.aranroig.syndriacore.syndriacore.utils.ConfigUtils;
import com.aranroig.syndriacore.syndriacore.utils.Coordinates;
import com.aranroig.syndriacore.syndriacore.utils.Print;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GlobalSpawnpoint implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase("gspawnpoint") || command.getName().equalsIgnoreCase("globalspawnpoint") ){
            if(sender instanceof Player){
                Player p = (Player) sender;
                Location loc = p.getLocation();
                if(p.isOp()){
                    ConfigUtils.SetConfigCoordinates(new Coordinates(loc), "ServerSpawn");
                    ConfigUtils.SetString(loc.getWorld().getName(), "ServerSpawn.world");

                    Print.Message(p, "Has canviat el spawn del server a:");
                    Print.IntLocation(p, loc);
                    return true;
                } else {
                    sender.sendMessage("No tens permisos");
                    return true;
                }
            } else {
                sender.sendMessage("No pots ets una consola gay");
                return true;
            }
        }

        return false;
    }
}
