package de.deinplugin.system;

import de.deinplugin.api.Arena;
import de.deinplugin.api.Game;
import de.deinplugin.api.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ConfigUtils {
    public static void saveArena(Arena arena, Configuration config) {
        config.set(arena.getName() + ".game", arena.getGame().getGameName());
        config.set(arena.getName() + ".min", arena.getMinPlayers());
        config.set(arena.getName() + ".max", arena.getMaxPlayers());
        config.set(arena.getName() + ".hasSign", arena.getSign() != null);
        if (arena.getSign() != null) saveLocation(arena.getSign().getLocation(), config, arena.getName() + ".sign");
        saveRegion(arena.getRegion(), config, arena.getName() + ".region");
        for (String name : arena.savedVars.keySet()) {
            Object o = arena.savedVars.get(name);
            if (o instanceof Location) {
                saveLocation((Location) o, config, arena.getName() + ".vars." + name);
            } else if (o instanceof Region) {
                saveRegion((Region) o, config, arena.getName() + ".vars." + name);
            } else {
                config.set(arena.getName() + ".vars." + name, o);
            }
            System.out.println("Saved: " + arena.getName() + ".vars." + name);
        }
    }

    public static List<Arena> loadArenas(final Configuration config) {
        LinkedList<Arena> arenas = new LinkedList<>();
        for (final String name : config.getKeys(false)) {
            Arena arena = new Arena() {
                @Override
                public Game getGame() {
                    try {
                        return GameManager.getGame(config.getString(name + ".game"));
                    } catch (GameManager.GameNotRegisteredException e) {
                        e.printStackTrace();
                        return null;
                    }
                }


                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Sign getSign() {
                    if (config.getBoolean(name + "hasSign")) {
                        Location signloc = loadLocation(config, name + ".sign");
                        if (!signloc.getBlock().getType().equals(Material.WALL_SIGN)) {
                            signloc.getBlock().setType(Material.WALL_SIGN);
                        }
                        return (Sign) signloc.getBlock().getState();
                    } else {
                        return null;
                    }
                }
            };
            arena.setMaxPlayers((int) config.get(name + ".max"));
            arena.setMinPlayers((int) config.get(name + ".min"));
            arena.setRegion(loadRegion(config, name + ".region"));
            if (config.getConfigurationSection(name + ".vars") != null) {


                for (String key : config.getConfigurationSection(name + ".vars").getKeys(false)) {
                    Object o = config.get(name + ".vars." + key);
                    System.out.println("Loading " + key);
                    if(config.get(name + "." + key+".location") != null){
                        arena.savedVars.put(key, loadLocation(config, name + "." + key));
                    }else if(config.get(name + "." +key+".region") != null){
                        arena.savedVars.put(key, loadRegion(config, name + "." + key));
                    }else{
                        arena.savedVars.put(key, o);
                    }






                }
            }
            arenas.add(arena);
        }
        return arenas;
    }

    public static Region loadRegion(Configuration config, String path) {
        return new Region(loadLocation(config, path + ".min"), loadLocation(config, path + ".max"));
    }

    public static Location loadLocation(Configuration config, String path) {
        return new Location(Bukkit.getWorld(config.getString(path + ".world")), config.getDouble(path + ".x"), config.getDouble(path + ".y"), config.getDouble(path + ".z"));
    }

    public static void saveRegion(Region r, Configuration config, String path) {
        config.set(path + ".region", true);
        saveLocation(new Location(r.world,  r.minX, r.minY, r.minZ), config, path + ".min");
        saveLocation(new Location(r.world, r.maxX, r.maxY, r.maxZ), config, path + ".max");
    }

    public static void saveLocation(Location loc, Configuration config, String path) {
        config.set(path + ".location", true);
        config.set(path + ".world", loc.getWorld().getName());
        config.set(path + ".x", loc.getX());
        config.set(path + ".y", loc.getY());
        config.set(path + ".z", loc.getZ());
        config.set(path + ".yaw", loc.getYaw());
        config.set(path + ".pitch", loc.getPitch());

    }
}
