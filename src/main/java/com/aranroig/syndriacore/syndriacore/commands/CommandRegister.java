package com.aranroig.syndriacore.syndriacore.commands;

import com.aranroig.syndriacore.syndriacore.Syndriacore;

public class CommandRegister {
    public CommandRegister(Syndriacore plugin){
        plugin.getCommand("gspawnpoint").setExecutor(new GlobalSpawnpoint());
        plugin.getCommand("goto").setExecutor(new GotoWorld(plugin));
    }
}
