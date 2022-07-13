package com.aranroig.syndriacore.syndriacore.utils;

import org.bukkit.Location;
import org.bukkit.World;

public class Coordinates {

    public int x;
    public int y;
    public int z;
    public float yaw;
    public float pitch;

    public Coordinates(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = 0;
        this.pitch = 0;
    }

    public Coordinates(int x, int y, int z, float yaw, float pitch){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Coordinates(Location loc){
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    public Location location(World world){
        return new Location(world,x,y,z,yaw,pitch);
    }
}
