package de.deinplugin.api.events;

import de.deinplugin.api.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ArenaStopEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Arena arena;
    private Reason reason;
    public ArenaStopEvent(Arena a, Reason r){
        arena = a;
        reason = r;
    }
    public Arena getArena(){
        return arena;
    }
    public Reason getReason(){
        return reason;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static enum Reason{
        RESTART, SERVERSTOP, BY_PLAYER, BY_PLUGIN, OTHER;
    }
}
