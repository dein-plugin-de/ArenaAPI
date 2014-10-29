package de.deinplugin.api;

import org.bukkit.block.Sign;

import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * Diese Daten werden in der Config gespeichert und automatisch wieder hergestellt. Locations und Regions können auch gespeichert werden.
     */
    public Map<String, Object> savedVars = new LinkedHashMap<String, Object>();
}
