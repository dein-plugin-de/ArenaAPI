package de.deinplugin.api;

import de.deinplugin.api.listener.ActionListener;
import de.deinplugin.system.ArenaManager;
import de.deinplugin.system.ConfigUtils;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ArenaAPI {
    private static boolean isregistered = false;
    public static void registerListener(Plugin pl){
        if(!isregistered){

            pl.getServer().getPluginManager().registerEvents(new ActionListener(), pl);
        }
    }
    public static void loadArenas(Configuration config){
        for(Arena a : ConfigUtils.loadArenas(config)){
            ArenaManager.registerArena(a);
        }
    }
    public static void saveArenas(Configuration config){
        for(Arena a : ArenaManager.arenas.values()){
            ConfigUtils.saveArena(a, config);
        }

    }
}
