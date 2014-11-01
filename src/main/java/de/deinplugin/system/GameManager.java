package de.deinplugin.system;

import de.deinplugin.api.Game;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Jonas on 29.10.2014.
 */
public class GameManager {
    private static Map<String, Game> games = new LinkedHashMap<>();
    private static Map<String, Plugin> plugins = new LinkedHashMap<>();

    public static void registerGame(Game game, Plugin owner) {
        games.put(game.getGameName(), game);
        plugins.put(game.getGameName(), owner);
        System.out.println("Registered Game: " + game.getGameName());
    }

    public static Plugin getPlugin(Game game) throws GameNotRegisteredException {
        if (plugins.containsKey(game.getGameName())) {
            return plugins.get(game.getGameName());
        } else {
            throw new GameNotRegisteredException();
        }
    }

    public static Game getGame(String game) throws GameNotRegisteredException {
        if (games.containsKey(game)) {
            return games.get(game);
        } else {
            throw new GameNotRegisteredException();
        }
    }

    /**
     * Wird ausgeführt, wenn es kein registriertes Game gibt, nach dem gesucht wird.
     */
    public static class GameNotRegisteredException extends Exception {
    }
}
