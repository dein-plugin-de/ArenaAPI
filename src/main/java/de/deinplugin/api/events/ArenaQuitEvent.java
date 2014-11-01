package de.deinplugin.api.events;

import de.deinplugin.api.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ArenaQuitEvent extends Event{
    private static final HandlerList handlers = new HandlerList();
    private Arena arena;
    private Player player;
    public ArenaQuitEvent(Arena a, Player p){
        arena = a;
        player = p;
    }
    public Arena getArena(){
        return arena;
    }
    public Player getPlayer(){
        return player;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static  HandlerList getHandlerList() {
        return handlers;
    }

}
