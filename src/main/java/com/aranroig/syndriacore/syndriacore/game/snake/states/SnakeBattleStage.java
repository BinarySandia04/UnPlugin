package com.aranroig.syndriacore.syndriacore.game.snake.states;

import com.aranroig.syndriacore.syndriacore.game.snake.SnakeGame;
import com.aranroig.syndriacore.syndriacore.interfaces.GameState;
import com.aranroig.syndriacore.syndriacore.utils.Coordinates;
import com.aranroig.syndriacore.syndriacore.utils.Print;
import com.aranroig.syndriacore.syndriacore.utils.WorldUtils;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnakeBattleStage implements GameState {

    private SnakeGame snakeGame;

    private List<Player> alive = new ArrayList<>();
    private Map<Player,Integer> deaths;

    List<Material> arrowRemoval = new ArrayList<>() {{
        add(Material.WHITE_CONCRETE);
        add(Material.GRAY_CONCRETE);
        add(Material.BLACK_CONCRETE);
    }};

    public SnakeBattleStage(SnakeGame snakeGame){
        this.snakeGame = snakeGame;
        deaths = new HashMap<>();
    }

    @Override
    public void Start() {
        for(Player p : snakeGame.players) {
            p.setLevel(0);
            p.setExp(0);

            alive.add(p);

            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));

            ItemStack botas = new ItemStack(Material.IRON_BOOTS);
            botas.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 69);

            p.getInventory().setBoots(botas);


        }

        WorldUtils.Fill(snakeGame.world, Material.AIR,
                snakeGame.arenaCoordinates.x - snakeGame.arenaRadius,
                snakeGame.arenaCoordinates.y - 1,
                snakeGame.arenaCoordinates.z - snakeGame.arenaRadius,

                snakeGame.arenaCoordinates.x + snakeGame.arenaRadius,
                snakeGame.arenaCoordinates.y - 1,
                snakeGame.arenaCoordinates.z + snakeGame.arenaRadius);

        Print.Debug("Hora de picarse!");
    }

    @Override
    public void Loop() {
        for(Player p : snakeGame.players) {

            p.setArrowsInBody(0);
            p.setSaturation(20);

            if(p.getLocation().getBlockY() < snakeGame.arenaCoordinates.y - 20){
                KillPlayer(p);
            }

        }

        for(Player p : deaths.keySet()){
            int time = deaths.get(p);
            deaths.put(p, time + 1);

            int quant = (snakeGame.bombSpawnTime - time + 20) / 20;
            Print.StatusBar(p, Component.text("Bomba en " + quant));

            if(time > snakeGame.bombSpawnTime){


                TNTPrimed tnt = (TNTPrimed) snakeGame.world.spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
                tnt.setFuseTicks(snakeGame.bombTime);

                deaths.put(p, 0);
            }
        }

        WorldUtils.Fill(snakeGame.world, Material.GREEN_CONCRETE,
                snakeGame.arenaCoordinates.x - 2,
                snakeGame.arenaCoordinates.y - 1,
                snakeGame.arenaCoordinates.z - 2,

                snakeGame.arenaCoordinates.x + 2,
                snakeGame.arenaCoordinates.y - 1,
                snakeGame.arenaCoordinates.z + 2);

        if(alive.size() == 1){
            // Print.Debug("Hola nomes queda un");
            snakeGame.SetState(new SnakeRestartStage(snakeGame, alive.get(0)));
        }

        if(alive.size() == 0){
            snakeGame.SetState(new SnakeRestartStage(snakeGame));
        }
    }

    List<Entity> arrows = new ArrayList<>();

    @Override
    public void CleanUp(){
        for(Entity entity : arrows){
            entity.remove();
        }
    }

    @Override
    public void Event(Event e) {
        if(e instanceof EntityDamageEvent){
            EntityDamageEvent event = (EntityDamageEvent) e;
            if(event.getEntity() instanceof Player){
                Player p = (Player) event.getEntity();
                if(p.getHealth() <= event.getFinalDamage()){
                    event.setCancelled(true);
                    KillPlayer(p);
                }
            }
        }

        if(e instanceof EntityShootBowEvent){
            EntityShootBowEvent event = (EntityShootBowEvent) e;
            arrows.add(event.getProjectile());
        }

        if(e instanceof ProjectileHitEvent){
            ProjectileHitEvent event = (ProjectileHitEvent) e;
            Entity entity =  event.getEntity();

            if(arrows.contains(entity)){
                if(event.getHitEntity() == null){
                    arrows.remove(entity);

                    // Eh doncs buida i tal
                    Coordinates c = new Coordinates(event.getHitBlock().getLocation());

                    WorldUtils.SafeFill(snakeGame.world, Material.AIR, arrowRemoval,
                            c.x - 1, c.y, c.z - 1,
                            c.x + 1, c.y, c.z + 1);
                    entity.getLocation();

                    entity.remove();
                } else {
                    arrows.remove(entity);
                    entity.remove();
                }
            }
        }
    }

    private void KillPlayer(Player p){
        if(alive.contains(p)){

            p.setHealth(20);

            p.setFallDistance(0);
            p.teleport(snakeGame.arenaCoordinates.location(snakeGame.world));

            alive.remove(p);
            deaths.put(p, 0);
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage("Has mort!");
        }
    }
}
