package UI;

import javax.sound.sampled.*;
import java.io.File;

/**
 * Utility class for handling background music in the application.
 * This class provides static methods to control background music playback,
 * including starting and stopping background music tracks.
 * 
 * The class uses the Java Sound API to handle audio playback and supports
 * continuous looping of background music.
 * @author Karl
 */
public class MusicUtils {
    /** The audio clip used for playing background music */
    private static Clip backgroundClip;

    /**
     * Starts playing background music in a continuous loop.
     * If music is already playing, this method will not start a new track.
     * The music will continue to loop until explicitly stopped.
     *
     * @param musicFilePath the file path to the music file to be played.
     *                      Supported formats include WAV, AIFF, and AU
     *                      (depending on the system's audio file support)
     * @throws RuntimeException if there are issues loading or playing the audio file
     *                         (wrapped in try-catch internally)
     */
    public static void playBackgroundMusic(String musicFilePath) {
        try {
            if (backgroundClip == null || !backgroundClip.isRunning()) {
                File musicFile = new File(musicFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop indefinitely
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Stops the currently playing background music.
     * This method will stop playback, close the audio resources,
     * and reset the background clip. If no music is playing,
     * this method has no effect.
     */
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }
}
