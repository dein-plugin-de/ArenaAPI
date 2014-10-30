package de.deinplugin.api.listener;

import de.deinplugin.api.Arena;
import de.deinplugin.api.commands.AdminCommands;
import de.deinplugin.system.ArenaManager;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Jonas on 29.10.2014.
 */
public class ActionListener implements Listener{
    @EventHandler
    public void onPlayer(PlayerQuitEvent e){
        if(ArenaManager.getArena(e.getPlayer()) != null){
            ArenaManager.getArena(e.getPlayer()).removePlayer(e.getPlayer());
        }
    }
    @EventHandler
    public void onJoin(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.WALL_SIGN){
                Sign s = (Sign) e.getClickedBlock().getState();
                if(s.getLine(0).equalsIgnoreCase("§7[ARENA]")){
                    Arena a = ArenaManager.arenas.get(s.getLine(1));
                    if(a.getState() == Arena.State.LOBBY){
                        a.addPlayer(e.getPlayer());
                    }
                }
            }
        }
    }
    @EventHandler
    public void onChoos(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && AdminCommands.signchooser.containsKey(e.getPlayer())){
            if(e.getClickedBlock().getType() == Material.WALL_SIGN){
                Sign s = (Sign) e.getClickedBlock().getState();
                ArenaManager.arenas.get(AdminCommands.signchooser.get(e.getPlayer()).getName()).setSign(s);
                e.getPlayer().sendMessage("§aDas Schild wurde gesetzt.");
                AdminCommands.signchooser.remove(e.getPlayer());
            }else{
                e.getPlayer().sendMessage("§cSie müssen ein Schild an einer Wand auswählen.");
            }
        }
    }
}
