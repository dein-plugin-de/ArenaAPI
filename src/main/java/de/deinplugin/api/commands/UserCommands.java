package de.deinplugin.api.commands;

import de.deinplugin.api.Arena;
import de.deinplugin.api.Game;
import de.deinplugin.api.Region;
import de.deinplugin.api.events.ArenaStartEvent;
import de.deinplugin.api.events.ArenaStopEvent;
import de.deinplugin.system.ArenaManager;
import de.deinplugin.system.GameManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Jonas on 29.10.2014.
 */
public class UserCommands implements CommandExecutor {
    private String prefix = "§7[§6Arena§7] §6";
    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, final String[] args) {
        if(sender.hasPermission("Arena.user")){
            sender.sendMessage("§cDu hast keine Berechtigung für diesen Befehl!");
            return false;
        }
        if(args.length == 0){
            sendHelp(sender, command.getName());
            return true;
        }
        if(args[0].equalsIgnoreCase("help")){
            sendHelp(sender, command.getName());
        }else if(args[0].equalsIgnoreCase("join")){
            if(ArenaManager.arenas.containsKey(args[1])){
                ArenaManager.arenas.get(args[1]).addPlayer((Player) sender);
                return true;
            }
            sender.sendMessage(prefix + "Die Arena wurde nicht gefunden.");
        }else if(args[0].equalsIgnoreCase("leave")){
            if(ArenaManager.getArena((Player) sender) != null){
                ArenaManager.getArena((Player) sender).removePlayer((Player) sender);
                return true;
            }
            sender.sendMessage(prefix + "§cDu bist in keiner Arena!");
        }else{
            sendHelp(sender, command.getName());
        }

        return true;
    }
    public void sendHelp(CommandSender sender, String root){
        sender.sendMessage(prefix + "/" + root + " join <arena>");
        sender.sendMessage(prefix + "/" + root + " leave <name>");
        sender.sendMessage(prefix + "/" + root + " help");
    }
    private HashMap<String, Location> pos1 = new HashMap<String, Location>();
    private HashMap<String, Location> pos2 = new HashMap<String, Location>();
}
