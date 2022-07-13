package com.aranroig.syndriacore.syndriacore.utils;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.List;

public class WorldUtils {
    public static int Fill(World world, Material material, int sx, int sy, int sz, int fx, int fy, int fz){

        if(sx > fx){
            int t = sx;
            sx = fx;
            fx = t;
        }
        if(sy > fy){
            int t = sy;
            sy = fy;
            fy = t;
        }
        if(sz > fz){
            int t = sz;
            sz = fz;
            fz = t;
        }

        for(int y = sy; y <= fy; y++){
            for(int x = sx; x <= fx; x++){
                for(int z = sz; z <= fz; z++){
                    world.getBlockAt(new Location(world, x, y, z)).setType(material);
                }
            }
        }

        return (fx - sx) * (fy - sy) * (fz - sz);
    }

    public static int FillAir(World world, Material material, int sx, int sy, int sz, int fx, int fy, int fz){

        if(sx > fx){
            int t = sx;
            sx = fx;
            fx = t;
        }
        if(sy > fy){
            int t = sy;
            sy = fy;
            fy = t;
        }
        if(sz > fz){
            int t = sz;
            sz = fz;
            fz = t;
        }

        for(int y = sy; y <= fy; y++){
            for(int x = sx; x <= fx; x++){
                for(int z = sz; z <= fz; z++){
                    Block b = world.getBlockAt(new Location(world, x, y, z));
                    if(b.getType() == Material.AIR)
                        b.setType(material);
                }
            }
        }

        return (fx - sx) * (fy - sy) * (fz - sz);
    }

    public static ArmorStand SetupHologram(TextComponent text, Location loc) {
        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setCustomNameVisible(true);
        stand.customName(text);
        stand.setGravity(false);
        stand.setCanPickupItems(false);
        stand.setArms(false);
        stand.setBasePlate(false);
        stand.setCanMove(false);
        stand.setCanTick(false);
        stand.setSmall(false);
        stand.setInvulnerable(true);
        stand.teleport(loc);
        return stand;
    }

    public static int SafeFill(World world, Material material, List<Material> materials, int sx, int sy, int sz, int fx, int fy, int fz) {
        if(sx > fx){
            int t = sx;
            sx = fx;
            fx = t;
        }
        if(sy > fy){
            int t = sy;
            sy = fy;
            fy = t;
        }
        if(sz > fz){
            int t = sz;
            sz = fz;
            fz = t;
        }

        for(int y = sy; y <= fy; y++){
            for(int x = sx; x <= fx; x++){
                for(int z = sz; z <= fz; z++){
                    Block b = world.getBlockAt(new Location(world, x, y, z));
                    if(materials.contains(b.getType()))
                        b.setType(material);
                }
            }
        }

        return (fx - sx) * (fy - sy) * (fz - sz);
    }
}
