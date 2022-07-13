package com.aranroig.syndriacore.syndriacore.listeners;

import com.aranroig.syndriacore.syndriacore.Syndriacore;

public class EventRegister {
    public static void RegisterEvents(Syndriacore plugin){
        plugin.getServer().getPluginManager().registerEvents(new PlayerConnection(plugin), Syndriacore.instance);
        plugin.getServer().getPluginManager().registerEvents(new GameEvents(), Syndriacore.instance);
    }
}
