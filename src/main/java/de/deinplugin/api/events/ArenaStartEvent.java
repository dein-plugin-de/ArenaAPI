package de.deinplugin.api.events;

import de.deinplugin.api.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ArenaStartEvent extends Event implements Cancellable{
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Arena arena;
    private Reason reason;
    public ArenaStartEvent(Arena a, Reason r){
        arena = a;
        reason = r;
    }
    public Arena getArena(){
        return arena;
    }
    public Reason getReason(){
        return reason;
    }
    public HandlerList getHandlers() {
        return handlers;
    }
    public static  HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public static enum Reason{
        RESTART, SERVERSTART, BY_PLAYER, OTHER;
    }
}
