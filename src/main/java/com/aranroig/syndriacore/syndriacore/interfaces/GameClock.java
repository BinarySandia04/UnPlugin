package com.aranroig.syndriacore.syndriacore.interfaces;

import com.aranroig.syndriacore.syndriacore.utils.Print;
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
