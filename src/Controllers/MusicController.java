package Controllers;

import org.newdawn.slick.Sound;

/**
 * Diese Klasse verwaltet die Hintergrundmusik des Spiels
 */
public final class MusicController {
    
    //Attribute

    private static Sound sound;


    //Konstruktoren

    private MusicController() {
        throw new AssertionError();
    }


    //Methoden

    /**
     * Diese Methode initalisiert den MusicController, indem die zum aktuell geladenen Level passende Musik geladen wird.
     */
    public static void init() {
        try {
            sound = new Sound("assets/sounds/music/level" + LevelController.getLevelNumber() + ".wav");
        } catch (Exception e) {
            System.out.println("no sound");
        }
    }

    /**
     * Diese Methode startet die Musik
     */
    public static void startMusic() {
        if (sound != null) {
            sound.loop(1.0f, 20f);
        }
    }

    /**
     * Diese Methode stoppt die Musik
     */
    public static void stopMusic() {
        if (sound != null) {
            sound.stop();   
        }
    }
}
