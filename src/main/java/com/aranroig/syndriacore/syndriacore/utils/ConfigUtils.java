package com.aranroig.syndriacore.syndriacore.utils;

import com.aranroig.syndriacore.syndriacore.Syndriacore;

public class ConfigUtils {
    public static void SetConfigCoordinates(Coordinates coords, String path){
        Syndriacore.instance.getConfig().set(path + ".x", coords.x);
        Syndriacore.instance.getConfig().set(path + ".y", coords.y);
        Syndriacore.instance.getConfig().set(path + ".z", coords.z);

        Syndriacore.instance.getConfig().set(path + ".yaw", coords.yaw);
        Syndriacore.instance.getConfig().set(path + ".pitch", coords.pitch);

        Syndriacore.instance.saveConfig();
    }

    public static Coordinates GetConfigCoordinates(String path){
        int x = Syndriacore.instance.getConfig().getInt(path + ".x");
        int y = Syndriacore.instance.getConfig().getInt(path + ".y");
        int z = Syndriacore.instance.getConfig().getInt(path + ".z");
        int yaw = Syndriacore.instance.getConfig().getInt(path + ".yaw");
        int pitch = Syndriacore.instance.getConfig().getInt(path + ".pitch");

        return new Coordinates(x, y, z, yaw, pitch);
    }

    public static int GetInt(String path){
        return Syndriacore.instance.getConfig().getInt(path);
    }

    public static boolean ExistsConfigLocation(String path){
        return Syndriacore.instance.getConfig().contains(path + ".x") &&
                Syndriacore.instance.getConfig().contains(path + ".y") &&
                Syndriacore.instance.getConfig().contains(path + ".z");
    }

    public static void SetString(String value, String path) {
        Syndriacore.instance.getConfig().set(path, value);

        Syndriacore.instance.saveConfig();
    }

    public static String GetString(String path) {
        return Syndriacore.instance.getConfig().getString(path);
    }
}
