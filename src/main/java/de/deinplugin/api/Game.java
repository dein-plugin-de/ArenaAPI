package de.deinplugin.api;

/**
 * Created by Jonas on 29.10.2014.
 */
public interface Game {
    /**
     * @return Das Prefix, welches vor jeder Nachricht des Games angezeigt werden soll.
     */
    public String getPrefix();

    /**
     * Wird ausgeführt,  wenn eine Arena mit diesem Spiel startet.
     *
     * @param arena Die Arena, die gestartet wurde.
     */
    public void onStart(Arena arena);

    /**
     * Wird ausgeführt,  wenn eine Arena mit diesem Spiel stoppt.
     *
     * @param arena Die Arena, die gestoppt wurde.
     */
    public void onStop(Arena arena);

    /**
     * @return Gibt den Namen des Spiels zurück. Wird für Nachrichten benötigt.
     */
    public String getName();


}
