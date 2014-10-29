package de.deinplugin.api;

import de.deinplugin.api.events.ArenaJoinEvent;
import de.deinplugin.api.events.ArenaQuitEvent;
import de.deinplugin.api.events.ArenaStartEvent;
import de.deinplugin.api.events.ArenaStopEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Jonas on 29.10.2014.
 */
public abstract class Arena {
    /**
     * @return Das Spiel, welches in der Arena gespielt werden soll.
     */
    public abstract Game getGame();

    public abstract int getMaxPlayers();

    public abstract int getMinPlayers();

    public abstract Region getRegion();

    /**
     * @return Der Name der Arena/Map. Wird ggf. auf dem Schild angezeigt.
     */
    public abstract String getName();

    /**
     * @return Das Schild, welches aktualisiert werden soll. Wenn "null" zurückgegeben wird, wird kein Schild aktualisiert.
     */
    public abstract Sign getSign();
    public void broadcastMessage(String msg){
        for(Player p : player){
            p.sendMessage(msg);
        }
    }
    /**
     *
     * @return Gibt an, welche Spieler sich in der Arena befinden.
     */
    public List<Player> getPlayers(){
        return player;
    }
    public void addPlayer(Player p){
        Location join = p.getLocation();
        ArenaJoinEvent e = new ArenaJoinEvent(this, p);
        Bukkit.getPluginManager().callEvent(e);
        if(e.isCancelled()) return;
        joinloc.put(p, join);
        player.add(p);
        broadcastMessage(getGame().getPrefix() + p.getName() + " hat die Arena betreten.");
        if(getSign() != null){
            getSign().setLine(0, "§l§7[Arena]");
            getSign().setLine(1, "§7" + getName());
            getSign().setLine(2, "" + ChatColor.DARK_BLUE + player.size() + "§7/" + ChatColor.DARK_BLUE  +getMaxPlayers());
            getSign().setLine(3, "§i§6" + getState().toString());
        }
    }
    public void removePlayer(Player p){
        ArenaQuitEvent e = new ArenaQuitEvent(this, p);
        Bukkit.getPluginManager().callEvent(e);
        player.remove(p);
        p.teleport(joinloc.get(p));
        if(state == State.RESTARTING || state == State.OFFLINE) return;
        if(getSign() != null){
            getSign().setLine(0, "§l§7[Arena]");
            getSign().setLine(1, "§7" + getName());
            getSign().setLine(2, "" + ChatColor.DARK_BLUE + player.size() + "§7/" + ChatColor.DARK_BLUE  +getMaxPlayers());
            getSign().setLine(3, "§i§6" + getState().toString());
        }
        broadcastMessage(getGame().getPrefix() + p.getName() + " hat die Arena verlassen.");
        if(player.size() < getMinPlayers()){
            broadcastMessage(getGame().getPrefix() + "Es sind zu wenige Spieler in der Arena.");
            broadcastMessage(getGame().getPrefix() + "Die Arena startet nun neu.");
            restart();
        }
    }
    public void restart(){
        stop(ArenaStopEvent.Reason.RESTART);
        start(ArenaStartEvent.Reason.RESTART);
    }
    private HashMap<Player, Location> joinloc = new HashMap<Player, Location>();
    public boolean isInArena(Player p){
        return player.contains(p);
    }
    public void start(ArenaStartEvent.Reason reason){
        ArenaStartEvent e = new ArenaStartEvent(this, reason);
        Bukkit.getPluginManager().callEvent(e);
        if(!e.isCancelled()){
            state = State.LOBBY;
            if(getSign() != null){
                getSign().setLine(0, "§l§7[Arena]");
                getSign().setLine(1, "§7" + getName());
                getSign().setLine(2, "" + ChatColor.DARK_BLUE + player.size() + "§7/" + ChatColor.DARK_BLUE  +getMaxPlayers());
                getSign().setLine(3, "§i§6" + getState().toString());
            }
            getGame().onStart(this);
        }
    }
    public void stop(ArenaStopEvent.Reason reason){
        ArenaStopEvent e = new ArenaStopEvent(this, reason);
        Bukkit.getPluginManager().callEvent(e);
        if(reason != ArenaStopEvent.Reason.RESTART){
            state = State.OFFLINE;
            if(getSign() != null){
                getSign().setLine(0, "§l§7[Arena]");
                getSign().setLine(1, "§7" + getName());
                getSign().setLine(2, "" + ChatColor.DARK_BLUE +  "---§7/" + ChatColor.DARK_BLUE  +"---");
                getSign().setLine(3, "§8" + getState().toString());
            }
        }
        for(Player p : player){
            removePlayer(p);
        }
        getGame().onStop(this);
    }
    public State getState(){
        return state;
    }
    /**
     * Diese Daten werden in der Config gespeichert und automatisch wieder hergestellt. Locations und Regions können auch gespeichert werden.
     */
    public Map<String, Object> savedVars = new LinkedHashMap<String, Object>();
    private State state = State.OFFLINE;
    private List<Player> player = new LinkedList<>();
    public static enum State{
        LOBBY, INGAME, OFFLINE, RESTARTING;
    }
}
