package com.aranroig.syndriacore.syndriacore;

import com.aranroig.syndriacore.syndriacore.commands.CommandRegister;
import com.aranroig.syndriacore.syndriacore.game.snake.SnakeGame;
import com.aranroig.syndriacore.syndriacore.interfaces.Game;
import com.aranroig.syndriacore.syndriacore.listeners.EventRegister;
import com.aranroig.syndriacore.syndriacore.utils.ConfigUtils;
import com.aranroig.syndriacore.syndriacore.utils.Coordinates;
import com.aranroig.syndriacore.syndriacore.utils.worldgen.VoidWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Syndriacore extends JavaPlugin implements Listener {

    public static Syndriacore instance;

    private SnakeGame gameManager;

    private CommandRegister commandRegister;

    public List<Game> games = new ArrayList<>();
    public Location spawnpointLocation;

    @Override
    public void onEnable() {
        // A instancia aixo
        instance = this;


        // Config i tal
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        EventRegister.RegisterEvents(this);
        commandRegister = new CommandRegister(this);


        // Crear games!
        games.add(new SnakeGame(this, "Snake"));

        InitGames();

        LoadWorlds();

        StartGames();

        LoadSpawnpoint();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ClearGames();
        System.out.println("Plugin stopped");
    }

    private void LoadWorlds(){
        List<String> list = (List<String>) getConfig().getList("Worlds");
        for(String s : list){
            VoidWorldGenerator.CreateEmptyWorld(s);
        }
    }

    private void LoadSpawnpoint(){
        Coordinates cords = ConfigUtils.GetConfigCoordinates("ServerSpawn");
        spawnpointLocation = cords.location(Bukkit.getWorld(
                ConfigUtils.GetString("ServerSpawn.world")
        ));
    }


    private void InitGames(){
        for(Game i : games){
            i.Init();
        }
    }

    private void StartGames(){
        for(Game i : games){
            i.Start();
        }
    }

    private void ClearGames(){
        for(Game i : games){
            i.Clear();
        }
    }
}
