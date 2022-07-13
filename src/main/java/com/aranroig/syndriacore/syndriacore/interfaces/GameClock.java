package com.aranroig.syndriacore.syndriacore.interfaces;

import org.bukkit.scheduler.BukkitRunnable;

public class GameClock extends BukkitRunnable {

    Game game;

    public GameClock(Game g){
        this.game = g;
    }

    @Override
    public void run() {
        // Do something!!!
        game.Loop();
        game.currentState.Loop();
    }
}
