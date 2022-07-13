package com.aranroig.syndriacore.syndriacore.interfaces;

import com.aranroig.syndriacore.syndriacore.Syndriacore;
import com.aranroig.syndriacore.syndriacore.utils.Print;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public String gameName;
    public GameState currentState;
    public GameClock loop;

    public World world;
    public Location spawnLocation;

    public List<Player> participants = new ArrayList<>();
    public List<Player> players = new ArrayList<>();

    public Syndriacore plugin;

    public Game(Syndriacore plugin, String gameName){
        this.plugin = plugin;
        this.gameName = gameName;

        this.world = Bukkit.getWorld(plugin.getConfig().getString(gameName + ".World"));

        this.loop = new GameClock(this);
    }

    public void SetState(GameState state){
        if(this.currentState != null)
            this.currentState.CleanUp();
        this.currentState = state;
        state.Start();
    }

    public void Init() { }
    public void Start() {
        loop.runTaskTimer(plugin, 0, 1);
    }

    public void Clear(){
        loop.cancel();
    }

    public void Loop() {}

    public void Event(Event e) {

        currentState.Event(e);
    }

    public void PlayerDisconnect(Player p) {
        participants.remove(p);
        players.remove(p);
    }

}
