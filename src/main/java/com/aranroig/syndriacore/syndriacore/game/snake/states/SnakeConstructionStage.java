package com.aranroig.syndriacore.syndriacore.game.snake.states;

import com.aranroig.syndriacore.syndriacore.game.snake.SnakeGame;
import com.aranroig.syndriacore.syndriacore.interfaces.GameState;
import com.aranroig.syndriacore.syndriacore.utils.ConfigUtils;
import com.aranroig.syndriacore.syndriacore.utils.WorldUtils;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SnakeConstructionStage implements GameState {

    public SnakeGame snakeGame;

    int constructionTime = 600;

    int currentTime = 0;

    public SnakeConstructionStage(SnakeGame snakeGame){
        this.snakeGame = snakeGame;
    }

    @Override
    public void Start() {

        snakeGame.players = snakeGame.participants;

        constructionTime = ConfigUtils.GetInt("Snake.ConstructionTime");

        int arenaRadius = ConfigUtils.GetInt("Snake.ArenaRadius");
        int arenaHeight = ConfigUtils.GetInt("Snake.ArenaHeight");

        WorldUtils.Fill(snakeGame.world, Material.WHITE_CONCRETE,
                snakeGame.arenaCoordinates.x - arenaRadius, snakeGame.arenaCoordinates.y - 1, snakeGame.arenaCoordinates.z - arenaRadius,
                snakeGame.arenaCoordinates.x + arenaRadius, snakeGame.arenaCoordinates.y - 1, snakeGame.arenaCoordinates.z + arenaRadius);

        if(ConfigUtils.ExistsConfigLocation("Snake.Arena")){
            for(Player p : snakeGame.players){
                p.setFallDistance(0);
                p.teleport(snakeGame.arenaCoordinates.location(snakeGame.world));
            }
        }

        ItemStack arcoCheto = new ItemStack(Material.BOW);
        arcoCheto.addUnsafeEnchantment(Enchantment.DURABILITY, 50);
        arcoCheto.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 3);
        arcoCheto.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        for(Player p : snakeGame.players){
            // Lo de les fleches

            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            p.getInventory().addItem(arcoCheto);
            p.getInventory().setItem(9, new ItemStack(Material.ARROW));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, constructionTime, 3, true, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, constructionTime, 0, true, false));
        }
    }

    @Override
    public void Loop() {

        for(Player p : snakeGame.players) {

            ((CraftPlayer) p).getHandle().ai().b(new DataWatcherObject<>(10, DataWatcherRegistry.b),0);

            p.setLevel((constructionTime - currentTime) / 20 + 1);
            p.setExp((float) currentTime / constructionTime);

            p.setHealth(20);
            p.setSaturation(20);

            Location loc = p.getLocation();

            if(loc.getBlockY() > -63) WorldUtils.FillAir(snakeGame.world, Material.WHITE_CONCRETE,
                    loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1,
                    loc.getBlockX() + 1, loc.getBlockY() - 1, loc.getBlockZ() + 1);

            if(loc.getBlockY() > -62) WorldUtils.FillAir(snakeGame.world, Material.GRAY_CONCRETE,
                    loc.getBlockX() - 1, loc.getBlockY() - 2, loc.getBlockZ() - 1,
                    loc.getBlockX() + 1, loc.getBlockY() - 2, loc.getBlockZ() + 1);

            if(loc.getBlockY() > -61) WorldUtils.FillAir(snakeGame.world, Material.BLACK_CONCRETE,
                    loc.getBlockX() - 1, loc.getBlockY() - 3, loc.getBlockZ() - 1,
                    loc.getBlockX() + 1, loc.getBlockY() - 3, loc.getBlockZ() + 1);
        }

        if(currentTime == constructionTime){
            snakeGame.SetState(new SnakeBattleStage(snakeGame));
        }

        currentTime++;
    }

    @Override
    public void CleanUp() {


    }

    @Override
    public void Event(Event e) {
        if(e instanceof ProjectileHitEvent){
            ProjectileHitEvent event = (ProjectileHitEvent) e;
            event.getEntity().remove();
        }
    }
}
