package com.aranroig.syndriacore.syndriacore.game.snake.states;

import com.aranroig.syndriacore.syndriacore.game.snake.SnakeGame;
import com.aranroig.syndriacore.syndriacore.interfaces.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class SnakeRestartStage implements GameState {

    Player winner;

    SnakeGame snakeGame;

    private int currentTime = 0;


    public SnakeRestartStage(SnakeGame snakeGame, Player winner){
        this.winner = winner;
        this.snakeGame = snakeGame;
    }

    // No ha guanyat ningú constructor
    public SnakeRestartStage(SnakeGame snakeGame){
        this.snakeGame = snakeGame;
    }

    @Override
    public void Start() {
        snakeGame.ClearArena();
        for(Player p : snakeGame.players){
            if(winner != null){
                p.sendTitle(winner.getName() + " ha guanyat", "");
            } else {
                p.sendTitle("No ha guanyat ningú", "");
            }
            p.setGameMode(GameMode.ADVENTURE);
            p.setFallDistance(0);
            p.teleport(snakeGame.spawnLocation);
            p.getInventory().clear();
        }

        snakeGame.players = new ArrayList<>();
    }

    @Override
    public void Loop() {
        UpdateStartTimer();
    }

    @Override
    public void CleanUp() {

    }

    @Override
    public void Event(Event e) {

    }

    public void UpdateStartTimer(){
        if(snakeGame.participants.size() >= 2){
            currentTime += 1;
            // Updatear text
            int time = (int) currentTime;

            snakeGame.SetStatusText(Component.text("Començant en ").color(TextColor.color(255,255,255))
                    .append(Component.text((snakeGame.timeToRestart - time + 20) / 20 + "s").color(TextColor.color(255, 156, 32))));

            if(currentTime > snakeGame.timeToRestart){
                snakeGame.SetStatusText(Component.text("Partida en curs").color(TextColor.color(255, 156, 32)));

                snakeGame.SetState(new SnakeConstructionStage(snakeGame));
            }
        } else {
            snakeGame.SetState(new SnakeWaitingStage(snakeGame));
        }
    }
}
