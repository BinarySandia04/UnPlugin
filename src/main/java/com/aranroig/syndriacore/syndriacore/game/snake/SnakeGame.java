package com.aranroig.syndriacore.syndriacore.game.snake;

import com.aranroig.syndriacore.syndriacore.Syndriacore;
import com.aranroig.syndriacore.syndriacore.game.snake.states.SnakeWaitingStage;
import com.aranroig.syndriacore.syndriacore.interfaces.Game;
import com.aranroig.syndriacore.syndriacore.utils.ConfigUtils;
import com.aranroig.syndriacore.syndriacore.utils.Coordinates;
import com.aranroig.syndriacore.syndriacore.utils.Print;
import com.aranroig.syndriacore.syndriacore.utils.WorldUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SnakeGame extends Game {

    private ArmorStand joinGameStand, timerStand;

    public Coordinates joinGameCoordinates, leaveGameCoordinates;
    public Coordinates arenaCoordinates;

    public int arenaRadius;
    public int timeToStart = 600;
    public int timeToRestart = 200;

    public SnakeGame(Syndriacore plugin, String gameName) {
        super(plugin, gameName);
    }

    private SnakeCommand commands;

    @Override
    public void Init() {
        super.Init();

        // Init commands
        commands = new SnakeCommand(this);
        plugin.getCommand(gameName).setExecutor(commands);

        joinGameCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Join");
        leaveGameCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Leave");
        spawnLocation = ConfigUtils.GetConfigCoordinates("Snake.Lobby").location(world);
        arenaCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Arena");

        arenaRadius = ConfigUtils.GetInt("Snake.ArenaRadius");
        timeToStart = ConfigUtils.GetInt("Snake.TimeToStart");
        timeToRestart = ConfigUtils.GetInt("Snake.TimeToRestart");
    }

    @Override
    public void Start(){
        InitArmorStands();
        // Init game state
        SetState(new SnakeWaitingStage(this));
    }

    @Override
    public void Clear() {

    }

    @Override
    public void Loop(){
        for(Player p : joinGameCoordinates.location(world).getNearbyPlayers(1)){
            if(!participants.contains((p))){
                participants.add(p);
                Print.GlobalBroadcast(Component.text(p.getName()).color(TextColor.color(0,255,0))
                        .append(Component.text(" ha entrado al bocadillo de fiambre!").color(NamedTextColor.WHITE)));
            }

            UpdatePlayerCount();
        }

        for(Player p : leaveGameCoordinates.location(world).getNearbyPlayers(1)){
            if(participants.contains((p))){
                participants.remove(p);
                Print.GlobalBroadcast(Component.text(p.getName()).color(TextColor.color(255,0,0))
                        .append(Component.text(" ha salido del bocadillo de fiambre!").color(NamedTextColor.WHITE)));
            }

            UpdatePlayerCount();
        }

        for(Player p : world.getPlayers()){
            if(!(participants.contains(p) && players.contains(p))){
                if(p.getLocation().getBlockY() < -64){
                    p.setFallDistance(0);
                    p.teleport(spawnLocation);
                    p.setGameMode(GameMode.ADVENTURE);
                }
            }
        }
    }

    private void UpdatePlayerCount(){
        joinGameStand.customName(Component.text("Players: ").color(TextColor.color(255,255,255))
                .append(Component.text(participants.size() + "/2").color(NamedTextColor.AQUA)));
    }

    private void InitArmorStands() {
        // Config stands
        world.getEntitiesByClass(ArmorStand.class).forEach(c -> c.remove());

        Location standLocationJoin = joinGameCoordinates.location(world).add(-0.5,0,-0.5);
        Location standLocationLeave = leaveGameCoordinates.location(world).add(-0.5,0,-0.5);

        standLocationJoin = standLocationJoin.add(0,1.3,0);
        standLocationLeave = standLocationLeave.add(0,1.3,0);

        WorldUtils.SetupHologram(Component.text("Join game").color(TextColor.color(0,255,0)),
                standLocationJoin);

        WorldUtils.SetupHologram(Component.text("Leave game").color(TextColor.color(255,0,0)),
                standLocationLeave);

        joinGameStand = WorldUtils.SetupHologram(Component.text("Players: ").color(TextColor.color(255,255,255))
                        .append(Component.text("0/2").color(NamedTextColor.AQUA)),
                standLocationJoin.add(0.0,-0.3,0.0));

        timerStand = WorldUtils.SetupHologram(Component.text("Esperant jugadors").color(TextColor.color(255, 109, 83)),standLocationJoin.add(0.0,-0.3,0.0));
    }


    public void SetStatusText(Component text){
        timerStand.customName(text);
    }

    public void SetJoinLocation(Location loc){
        ConfigUtils.SetConfigCoordinates(new Coordinates(loc), "Snake.Join");
        joinGameCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Join");
    }
    public void SetLeaveLocation(Location loc){
        ConfigUtils.SetConfigCoordinates(new Coordinates(loc), "Snake.Leave");
        leaveGameCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Leave");
    }
    public void SetArenaLocation(Location loc){
        ConfigUtils.SetConfigCoordinates(new Coordinates(loc), "Snake.Arena");
        arenaCoordinates = ConfigUtils.GetConfigCoordinates("Snake.Arena");
    }
    public void SetLobbyLocation(Location loc){
        ConfigUtils.SetConfigCoordinates(new Coordinates(loc), "Snake.Lobby");
        spawnLocation = ConfigUtils.GetConfigCoordinates("Snake.Lobby").location(world);
    }

    public int ClearArena() {
        int minY = -64, maxY = 150;
        int minX = -30, maxX = 30;
        int minZ = -30, maxZ = 30;

        return WorldUtils.Fill(world, Material.AIR, minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public void Event(Event e){


        super.Event(e);
    }
}
