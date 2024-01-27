package Controllers;

import org.newdawn.slick.Sound;

public abstract class MusicController {
    
    private static Sound sound;
    private static boolean play;

    public static void init() {
        try {
            sound = new Sound("assets/sounds/music/level1.wav");
        } catch (Exception e) {
            System.out.println("no sound");
        }
    }

    public static void startMusic() {
        if (sound != null) {
            sound.loop(1.0f, 20f);
        }
    }

    public static void stopMusic() {
        if (sound != null) {
            sound.stop();   
        }
    }
}
