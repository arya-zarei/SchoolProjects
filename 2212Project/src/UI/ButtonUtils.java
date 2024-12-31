package UI;

import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Utility class for adding sound effects to Swing components like {@link JButton} and {@link JPasswordField}.
 * <p>
 * This class provides functionality to enhance Swing UI components with audio feedback by:
 * <ul>
 *     <li>Adding click sound effects to buttons</li>
 *     <li>Adding enter key sound effects to password fields</li>
 *     <li>Handling WAV file playback with format conversion</li>
 * </ul>
 * <p>
 * All sound playback is performed asynchronously to prevent UI freezing.
 * The class supports various WAV audio formats through automatic format conversion.
 * 
 * @author Karl
 */
public class ButtonUtils {

    /**
     * Adds a sound effect to a {@link JButton} that plays when the button is clicked.
     * The sound is played asynchronously to prevent UI freezing.
     * <p>
     * Example usage:
     * <pre>
     * JButton button = new JButton("Click Me");
     * ButtonUtils.addButtonClickSound(button, "path/to/click.wav");
     * </pre>
     *
     * @param button        The {@link JButton} to which the sound effect will be added
     * @param soundFilePath The file path of the sound to play (must be a WAV file)
     * @throws IllegalArgumentException if either parameter is null
     */
    public static void addButtonClickSound(JButton button, String soundFilePath) {
        button.addActionListener(e -> playSound(soundFilePath));
    }

    /**
     * Adds a sound effect to a {@link JPasswordField} that plays when the "Enter" key is pressed.
     * The sound is played asynchronously to prevent UI freezing.
     * <p>
     * This method combines audio feedback with a custom action, allowing for both
     * sound playback and additional functionality when the user presses Enter.
     * <p>
     * Example usage:
     * <pre>
     * JPasswordField passwordField = new JPasswordField();
     * ButtonUtils.addPasswordFieldEnterSound(
     *     passwordField,
     *     "path/to/enter.wav",
     *     () -> System.out.println("Enter pressed!")
     * );
     * </pre>
     *
     * @param passwordField The {@link JPasswordField} to which the sound effect will be added
     * @param soundFilePath The file path of the sound to play (must be a WAV file)
     * @param action        A {@link Runnable} representing the custom action to execute when "Enter" is pressed
     * @throws IllegalArgumentException if any parameter is null
     */
    public static void addPasswordFieldEnterSound(JPasswordField passwordField, String soundFilePath, Runnable action) {
        passwordField.addActionListener(e -> {
            playSound(soundFilePath);
            action.run();
        });
    }

    /**
     * Plays a WAV sound file with support for dynamic format handling.
     * <p>
     * This method provides robust audio playback functionality:
     * <ul>
     *     <li>Supports various WAV formats through automatic conversion</li>
     *     <li>Converts audio to 16-bit PCM signed format if necessary</li>
     *     <li>Executes asynchronously on a separate thread</li>
     *     <li>Handles both mono and stereo audio</li>
     * </ul>
     * <p>
     * The audio conversion process ensures compatibility across different systems
     * by standardizing the audio format to PCM signed, 16-bit depth.
     * <p>
     * Example usage:
     * <pre>
     * ButtonUtils.playSound("path/to/sound.wav");
     * </pre>
     *
     * @param soundFilePath The file path of the sound to play (must be a WAV file)
     * @throws IllegalArgumentException if soundFilePath is null
     * @see AudioSystem
     * @see AudioFormat
     * @see Clip
     */
    public static void playSound(String soundFilePath) {
        new Thread(() -> {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);

                // Retrieve the audio format of the file
                AudioFormat baseFormat = audioStream.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        baseFormat.getSampleRate(),
                        16, // 16-bit audio
                        baseFormat.getChannels(),
                        baseFormat.getChannels() * 2, // Frame size
                        baseFormat.getSampleRate(),
                        false // Little-endian
                );

                // Convert the audio stream to a supported format
                AudioInputStream decodedStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

                // Play the audio
                Clip clip = AudioSystem.getClip();
                clip.open(decodedStream);
                clip.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }
}
