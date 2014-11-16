package de.deinplugin.system;

import de.deinplugin.api.Arena;
import de.deinplugin.api.events.ArenaStopEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ArenaManager {
    public static HashMap<String, Arena> arenas = new HashMap<>();

    public static List<Player> getPlayersInArenas() {
        List<Player> players = new LinkedList<>();
        for (Arena a : arenas.values()) {
            players.addAll(a.getPlayers());
        }
        return players;
    }

    public static Arena getArena(Player p) {
        for (Arena a : arenas.values()) {
            for (Player player : a.getPlayers()) {

                if (player.getName().equalsIgnoreCase(p.getName())) return a;
            }
        }
        return null;
    }

    public static void registerArena(Arena a) {
        a.stop(ArenaStopEvent.Reason.BY_PLUGIN);
        for(Player p : a.getPlayers()){
            a.removePlayer(p);
        }

        arenas.put(a.getName(), a);
    }
}
