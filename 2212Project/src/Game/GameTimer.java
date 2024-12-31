package Game;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * The GameTimer class is responsible for tracking the elapsed time of a game session.
 * It records the start time when an instance is created and calculates the total
 * elapsed time when the application is shutting down. It also updates the total
 * and average playtime in a CSV file.
 */
public class GameTimer {
    
    /**
     * Constructs a GameTimer instance and sets up a shutdown hook to record
     * the elapsed time when the application is terminated.
     */
    public GameTimer() {
        // Record the start time
        Instant start = Instant.now(); // Capture the current time as the start time

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Record the end time
            Instant end = Instant.now(); // Capture the current time as the end time

            // Calculate the elapsed time
            Duration elapsed = Duration.between(start, end); // Calculate the duration between start and end

            long totalSeconds = elapsed.getSeconds(); // Get the total elapsed seconds
            long hours = totalSeconds / 3600; // Calculate hours from total seconds
            long minutes = (totalSeconds % 3600) / 60; // Calculate remaining minutes from total seconds

            Map<String,String> timeData = DataManager.loadState("", "restrictions.csv"); // Load existing time data from CSV
            // Format as HH:MM
            String formattedTime = String.format("%02d:%02d", hours, minutes); // Format elapsed time as HH:MM

            System.out.print(formattedTime); // Print the formatted elapsed time

            String total = timeData.getOrDefault("Total", "00:00"); // Get the total playtime or default to "00:00"

            String[] parts1 = total.split(":"); // Split the total time into hours and minutes
            String[] parts2 = formattedTime.split(":"); // Split the formatted elapsed time into hours and minutes

            int hours1 = Integer.parseInt(parts1[0]); // Parse hours from total time
            int minutes1 = Integer.parseInt(parts1[1]); // Parse minutes from total time
            int hours2 = Integer.parseInt(parts2[0]); // Parse hours from formatted time
            int minutes2 = Integer.parseInt(parts2[1]); // Parse minutes from formatted time

            // Add hours and minutes separately
            int totalMinutes = minutes1 + minutes2; // Sum the minutes
            int totalHours = hours1 + hours2 + (totalMinutes / 60); // Sum the hours and carry over minutes to hours
            totalMinutes %= 60; // Remainder minutes after carrying over

            String totalTime = String.format("%02d:%02d", totalHours, totalMinutes); // Format the new total time as HH:MM
            timeData.put("Total", totalTime); // Update the total time in the data map

            int timesPlayed = Integer.parseInt(timeData.get("Times")); // Get the number of times played
            timesPlayed++; // Increment the times played

            String[] parts = totalTime.split(":"); // Split the updated total time into hours and minutes
            int totalTimeHours = Integer.parseInt(parts[0]); // Parse hours from total time
            int totalTimeMinutes = Integer.parseInt(parts[1]); // Parse minutes from total time

            // Convert total time to minutes
            int totalTotalTime = (totalTimeHours * 60) + totalTimeMinutes; // Convert total time to total minutes

            // Divide by the divisor
            int dividedMinutes = totalTotalTime / timesPlayed; // Calculate average playtime in minutes

            // Convert back to hours and minutes
            int resultHours = dividedMinutes / 60; // Convert average minutes back to hours
            int resultMinutes = dividedMinutes % 60; // Get remaining minutes

            // Format as HH:MM
            timeData.put("Average", String.format("%02d:%02d", resultHours, resultMinutes)); // Update average time in the data map

            timeData.put("Times", String.valueOf(timesPlayed)); // Update the times played in the data map

            DataManager.saveState("restrictions.csv", timeData); // Save the updated time data back to the CSV
        }));

    }

    public static boolean isRunning() {
        // Implementation to check if timer is running
        return true; // Or actual implementation logic
    }
}
