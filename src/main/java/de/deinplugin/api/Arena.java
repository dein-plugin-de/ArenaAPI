package de.deinplugin.api;

import org.bukkit.block.Sign;

/**
 * Created by Jonas on 29.10.2014.
 */
public abstract class Arena {
    /**
     *
     * @return Das Spiel, welches in der Arena gespielt werden soll.
     */
    public abstract Game getGame();
    public abstract int getMaxPlayers();
    public abstract int getMinPlayers();

    /**
     *
     * @return Der Name der Arena/Map. Wird ggf. auf dem Schild angezeigt.
     */
    public abstract String getName();

    /**
     *
     * @return Das Schild, welches aktualisiert werden soll. Wenn "null" zurückgegeben wird, wird kein Schild aktualisiert.
     *
     */
    public abstract Sign getSign();
}
