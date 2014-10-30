package de.deinplugin.api.commands;

import de.deinplugin.api.Arena;
import de.deinplugin.api.Game;
import de.deinplugin.api.Region;
import de.deinplugin.api.events.ArenaStartEvent;
import de.deinplugin.api.events.ArenaStopEvent;
import de.deinplugin.system.ArenaManager;
import de.deinplugin.system.GameManager;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jonas on 29.10.2014.
 */
public class AdminCommands implements CommandExecutor {
    private String prefix = "§7[§6ArenaAPI§7] §6";
    @Override
    public boolean onCommand(final CommandSender sender, Command command, String label, final String[] args) {
        if(!sender.hasPermission("Arena.cmd")){
            sender.sendMessage("§cDu hast keine Berechtigung für diesen Befehl!");
            return false;
        }
        if(args.length == 0){
            sendHelp(sender, command.getName());
            return true;
        }
        if(args[0].equalsIgnoreCase("help")){
            sendHelp(sender, command.getName());
        }else if(args[0].equalsIgnoreCase("addArena")){
            ArenaManager.registerArena(new Arena() {
                @Override
                public Game getGame() {
                    try {
                        return GameManager.getGame(args[2]);
                    } catch (GameManager.GameNotRegisteredException e) {
                        e.printStackTrace();
                        sender.sendMessage(prefix + "§cDas Spiel wurde nicht gefunden!");
                    }
                    return null;
                }

                @Override
                public String getName() {
                    return args[1];
                }

            });
            sender.sendMessage(prefix + "Arena hinzugefügt.");
        }else if(args[0].equalsIgnoreCase("removeArena")){
            ArenaManager.arenas.remove(args[1]);
            sender.sendMessage(prefix + "Arena wurde entfernt.");
        }else if(args[0].equalsIgnoreCase("setRegion")){
            if(pos1.containsKey(sender.getName()) && pos2.containsKey(sender.getName())){
                ArenaManager.arenas.get(args[1]).setRegion(new Region(pos1.get(sender.getName()), pos2.get(sender.getName())));
                sender.sendMessage(prefix + "Region wurde gesetzt.");
            }else{
                sender.sendMessage(prefix + "Makiere erst eine Region mit /" + command.getName() + "pos1/pos2");
            }

        }else if(args[0].equalsIgnoreCase("min")){
            ArenaManager.arenas.get(args[1]).setMinPlayers(Integer.parseInt(args[2]));
            sender.sendMessage(prefix + "Die Mindestanzahl der Spieler wurde gesetzt.");
        }else if(args[0].equalsIgnoreCase("max")){
            ArenaManager.arenas.get(args[1]).setMaxPlayers(Integer.parseInt(args[2]));
            sender.sendMessage(prefix + "Die Maximalanzahl der Spieler wurde gesetzt.");
        }else if(args[0].equalsIgnoreCase("start")){
            ArenaManager.arenas.get(args[1]).start(ArenaStartEvent.Reason.BY_PLAYER);
            sender.sendMessage(prefix + "Die Arena wurde gestartet.");
        }else if(args[0].equalsIgnoreCase("stop")){
            ArenaManager.arenas.get(args[1]).stop(ArenaStopEvent.Reason.BY_PLAYER);
            sender.sendMessage(prefix + "Die Arena wurde gestoppt.");
        }else if(args[0].equalsIgnoreCase("restart")){
            ArenaManager.arenas.get(args[1]).restart();
            sender.sendMessage(prefix + "Die Arena wird neugestartet.");
        }else if(args[0].equalsIgnoreCase("pos1")){
            pos1.put(sender.getName(), ((Player) sender).getLocation());
            sender.sendMessage(prefix + "Pos1 wurde gesetzt. ");
        }else if(args[0].equalsIgnoreCase("pos2")){
            pos2.put(sender.getName(), ((Player) sender).getLocation());
            sender.sendMessage(prefix + "Pos2 wurde gesetzt. ");
        }else if(args[0].equalsIgnoreCase("setSign")){
            signchooser.put((Player) sender, ArenaManager.arenas.get(args[1]));
            sender.sendMessage(prefix + "Wählen Sie ein Schild aus indem Sie Rechtsklick auf dieses machen.");
        }else{
            sendHelp(sender, command.getName());
        }

        return true;
    }
    public void sendHelp(CommandSender sender, String root){
        sender.sendMessage(prefix + "/" + root + " addArena <name> <game>");
        sender.sendMessage(prefix + "/" + root + " removeArena <name>");
        sender.sendMessage(prefix + "/" + root + " setRegion <arena>");
        sender.sendMessage(prefix + "/" + root + " min <arena> <minplayer>");
        sender.sendMessage(prefix + "/" + root + " max <arena> <maxplayer>");
        sender.sendMessage(prefix + "/" + root + " stop <arena>");
        sender.sendMessage(prefix + "/" + root + " start <arena>");
        sender.sendMessage(prefix + "/" + root + " restart <arena>");
        sender.sendMessage(prefix + "/" + root + " pos1");
        sender.sendMessage(prefix + "/" + root + " pos2");
        sender.sendMessage(prefix + "/" + root + " setSign");
        sender.sendMessage(prefix + "/" + root + " help");
    }
    public static HashMap<Player, Arena> signchooser = new HashMap<>();
    private HashMap<String, Location> pos1 = new HashMap<String, Location>();
    private HashMap<String, Location> pos2 = new HashMap<String, Location>();
}
