package com.aranroig.syndriacore.syndriacore.game.snake;

import com.aranroig.syndriacore.syndriacore.interfaces.GameCommands;
import com.aranroig.syndriacore.syndriacore.utils.Print;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SnakeCommand extends GameCommands {

    private SnakeGame snakeGame;

    public SnakeCommand(SnakeGame snakeGame){
        this.snakeGame = snakeGame;
    }

    private HashMap<String,String> usage = new HashMap<String,String>() {{
        put("set join", "Canvia a on s'han d'unir els jugadors");
        put("set leave", "Canvia a on han de sortir els jugadors");
        put("set arena", "Canvia el centre de la arena");
        put("set lobby", "Canvia el lobby del joc");
    }};

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase("snake")){
            if(!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "Només jugadors!");
            } else {

                Player p = (Player) sender;
                Location loc = p.getLocation();

                if(!p.isOp()){
                    Print.Error(p, "No tens permissos per executar aquesta comanda");
                } else {
                    if(args.length == 0){
                        Print.Usage(p,"snake", usage);
                    } else {
                        if(args.length > 0){
                            if(args[0].equalsIgnoreCase("set")){
                                if(args.length > 1){
                                    if(args[1].equalsIgnoreCase("join")){
                                        snakeGame.SetJoinLocation(loc);

                                        Print.Message(p, "S'ha guardat la nova posició de Snake->Join a: ");
                                        Print.IntLocation(p, loc);

                                        return true;
                                    }

                                    if(args[1].equalsIgnoreCase("leave")){
                                        snakeGame.SetLeaveLocation(loc);

                                        Print.Message(p, "S'ha guardat la nova posició de Snake->Leave a: ");
                                        Print.IntLocation(p, loc);

                                        return true;
                                    }

                                    if(args[1].equalsIgnoreCase("arena")){
                                        snakeGame.SetArenaLocation(loc);

                                        Print.Message(p, "S'ha guardat la nova posició de Snake->Arena a: ");
                                        Print.IntLocation(p, loc);

                                        return true;
                                    }

                                    if(args[1].equalsIgnoreCase("lobby")){
                                        snakeGame.SetLobbyLocation(loc);

                                        Print.Message(p, "S'ha guardat la nova posició de Snake->Lobby a: ");
                                        Print.IntLocation(p, loc);

                                        return true;
                                    }
                                }
                            }


                        }
                        Print.Error(p,"Has posat malament algo");
                    }
                }
            }
            return true;
        }
        return false;
    }
}
