package com.aranroig.syndriacore.syndriacore.utils.worldgen;

import net.kyori.adventure.util.TriState;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class VoidWorldGenerator {

    public static void CreateEmptyWorld(String name){
        WorldCreator creator = new WorldCreator(name);

        creator.generateStructures(false);
        creator.environment(World.Environment.NORMAL);
        creator.keepSpawnLoaded(TriState.FALSE);
        creator.generator(new VoidChunkGenerator());

        creator.createWorld();
    }

}
