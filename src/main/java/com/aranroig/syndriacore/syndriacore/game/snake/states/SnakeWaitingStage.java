package com.aranroig.syndriacore.syndriacore.game.snake.states;

import com.aranroig.syndriacore.syndriacore.game.snake.SnakeGame;
import com.aranroig.syndriacore.syndriacore.interfaces.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.Event;

public class SnakeWaitingStage implements GameState {

    public SnakeGame snakeGame;

    public SnakeWaitingStage(SnakeGame snakeGame){
        this.snakeGame = snakeGame;
    }

    private int currentTime = 0;

    @Override
    public void Start() {
        snakeGame.ClearArena();
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
                    .append(Component.text((snakeGame.timeToStart - time + 20) / 20 + "s").color(TextColor.color(255, 156, 32))));

            if(currentTime > snakeGame.timeToStart){
                snakeGame.SetStatusText(Component.text("Partida en curs").color(TextColor.color(255, 156, 32)));

                snakeGame.SetState(new SnakeConstructionStage(snakeGame));
            }
        } else {
            currentTime = 0;
            snakeGame.SetStatusText(Component.text("Esperant jugadors").color(TextColor.color(255, 109, 83)));
        }
    }
}
