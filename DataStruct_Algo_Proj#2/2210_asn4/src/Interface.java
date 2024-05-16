
//file readers
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//for list method
import java.util.ArrayList;
import java.util.Collections;

/**
 * This interface class reads the text file and adds all the Records of the text
 * file, label type and data to a Binary Search Tree dictionary. Then a user is
 * able to input commands such as define, translate, sound, play, say,
 * show, animate, browse, add, delete, list, first and last that will perform
 * specific operations and outputs from the text file.
 * 
 * @author Arya Zarei
 *         2210B Assignment 4
 */

public class Interface {
    public static void main(String[] args) throws DictionaryException {
        // the main method of the Interface reads the text file
        // and puts the Records in the BSTDictionary

        String inputFile = args[0]; // input file name

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

            BSTDictionary dictionary = new BSTDictionary(); // BSTDictionary to store records
            ArrayList<String> labelList = new ArrayList<>(); // List all all labels for the list command

            String label; // string to process label from file
            String data; // string to process data from file
            String line; // string to process line of a file

            while ((label = reader.readLine()) != null) { // iterate through text file

                line = reader.readLine().trim(); // trim the line to make it clear to read
                int type = getType(line); // get type of line
                data = line.substring(0); // string data starts from first index of line
                Key key = new Key(label, type); // make key with type and label of line from file
                Record record = new Record(key, data); // make a record with the key and data
                dictionary.put(record); // add the record to the BSTDictionary

                // for list command
                labelList.add(label); // add label to the list of labels
            }

            // Process user commands
            processCommands(dictionary, labelList);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param line //given the string line from the file
     * @return //returns the type of the key
     */
    private static int getType(String line) {

        char firstChar = line.charAt(0);

        switch (firstChar) {
            case '/':
                return 2; // French translation
            case '-':
                return 3; // Sound file
            case '+':
                return 4; // Music file
            case '*':
                return 5; // Voice file

            default: // file extensions
                String[] parts = line.split("\\."); // have . in the name
                if (parts.length == 2) {
                    String extension = parts[1];
                    switch (extension) {
                        case "jpg": // show file
                            return 6;
                        case "gif": // animate file
                            return 7;
                        case "html": // browse file
                            return 8;
                    }
                }
                return 1; // Define command
        }
    }

    /**
     * @param dictionary storing all the records
     * @param labelList  storing the list of all labels in file
     * @throws IOException
     * @throws DictionaryException
     * 
     *                             The processCommands method reads the user's input
     *                             and calls the method to run the user's input
     *                             according
     *                             to the type of input. For example define
     *                             computer, calls the defineCommand
     *                             with the command string "define computer".
     */
    private static void processCommands(BSTDictionary dictionary, ArrayList<String> labelList)
            throws IOException, DictionaryException {

        StringReader keyboard = new StringReader(); // reads user input
        String command; // stores user input

        while (true) {

            command = keyboard.read("Enter next command: "); // displayed for user to enter command

            if (command.equals("exit")) {
                break; // exit interface
            }

            else if (command.startsWith("define")) {
                defineCommand(dictionary, command); // define word of user input
            }

            else if (command.startsWith("translate ")) {
                translateCommand(dictionary, command); // translate word of user input
            }

            else if (command.startsWith("sound")) {
                soundCommand(dictionary, command); // play sound from text file name user inputted
            }

            else if (command.startsWith("play")) {
                playCommand(dictionary, command); // play music from text file name user inputted
            }

            else if (command.startsWith("say")) {
                sayCommand(dictionary, command); // say word from text file name user inputted
            }

            else if (command.startsWith("show")) {
                showCommand(dictionary, command); // show image (jpg) from text file name user inputted
            }

            else if (command.startsWith("animate")) {
                animateCommand(dictionary, command); // animate image (gif) from text file name user inputted
            }

            else if (command.startsWith("browse")) {
                browseCommand(dictionary, command); // browse website (html) from text file name user inputted
            }

            else if (command.startsWith("delete")) {
                deleteCommand(dictionary, command); // delete record user inputted
            }

            else if (command.startsWith("add")) {
                addCommand(dictionary, command); // add record user inputted
            }

            else if (command.startsWith("list")) {
                listCommand(labelList, command); // list labels starting with the prefix user inputted
            }

            else if (command.startsWith("first")) {
                firstCommand(dictionary); // print all attribute of smallest key in dictionary
            }

            else if (command.startsWith("last")) {
                lastCommand(dictionary); // print all attribute of largest key in dictionary
            }

            else {
                System.out.println("Invalid command."); // if none of the above print message
            }
        }
    }

    /**
     * @param dictionary to get data of record to define
     * @param command    label name to define
     */
    private static void defineCommand(BSTDictionary dictionary, String command) {
        // Extract the word to define
        String word = command.substring(7).trim(); // Skip "define" and trim any leading/trailing spaces

        // Search for the record in the dictionary
        Key key = new Key(word.toLowerCase(), 1); // Type 1 indicates a word definition
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            // Print the data (definition) of the input in lowercase
            System.out.println(record.getDataItem().toLowerCase());
        }

        else {
            // Print a message indicating the word is not in the dictionary
            System.out.println("The word " + word + " is not in the dictionary");
        }
    }

    /**
     * @param dictionary to get data of record to translate
     * @param command    label name to translate
     */
    private static void translateCommand(BSTDictionary dictionary, String command) {
        // Extract the word to translate
        String word = command.substring(9).trim(); // Skip "translate" and trim any leading/trailing spaces

        // Search for the record in the dictionary
        Key key = new Key(word.toLowerCase(), 2); // Type 2 indicates a word translation
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            // Print the data (translation) of the input in lowercase and
            // remove the first character '/' for translation
            System.out.println(record.getDataItem().substring(1).toLowerCase());
        }

        else {
            // if no translation print message that there is no definition for the word
            System.out.println("There is no definition for the word " + word);
        }
    }

    /**
     * @param dictionary to get data sound of label
     * @param command    label name to play sound
     */
    private static void soundCommand(BSTDictionary dictionary, String command) {

        // Extract name of sound file
        String word = command.substring(6).trim(); // Skip "sound" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 3); // Type 3 indicates a sound file
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            String fileName = record.getDataItem().substring(1); // Remove the leading hyphen

            try { // play sound file
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(fileName);
            }

            catch (MultimediaException e) { // exception message if error in playing sound
                System.out.println("Error processing input file " + record.getDataItem());
            }
        }

        else { // message if sound file does not exist
            System.out.println("There is no sound file for " + word);
        }
    }

    /**
     * @param dictionary to get data music of label
     * @param command    label name to play music
     */
    private static void playCommand(BSTDictionary dictionary, String command) {

        String word = command.substring(5).trim(); // Skip "play" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 4); // Type 4 indicates a music file
        Record record = dictionary.get(key); // get record of word inputted

        if (record != null) {

            String fileName = record.getDataItem().substring(1); // Remove the leading plus sign

            try {// play music
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(fileName);
            }

            catch (MultimediaException e) { // print exception if the file cannot be played
                System.out.println("Error processing input file " + record.getDataItem());
            }
        }

        else { // Print a message if there is no music file for the word
            System.out.println("There is no music file for " + word);
        }
    }

    /**
     * @param dictionary to get data sound of label
     * @param command    label name to play word
     */
    private static void sayCommand(BSTDictionary dictionary, String command) {

        String word = command.substring(4).trim(); // Skip "say" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 5); // Type 5 indicates a voice file
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            String fileName = record.getDataItem().substring(1); // Remove the leading * sign

            try { // Play the audio file
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(fileName);
            }

            catch (MultimediaException e) {
                // print exception if the file cannot be played
                System.out.println("Error processing input file " + record.getDataItem());
            }
        }

        else {// Print a message if there is no voice file for the word
            System.out.println("There is no voice file for " + word);
        }
    }

    /**
     * @param dictionary to get data image of label
     * @param command    label name to show image
     */
    private static void showCommand(BSTDictionary dictionary, String command) {

        String word = command.substring(5).trim(); // Skip "show" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 6); // Type 6 indicates an image file
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            String fileName = record.getDataItem(); // get filename

            try {// display image
                PictureViewer pictureViewer = new PictureViewer();
                pictureViewer.show(fileName);
            }

            catch (MultimediaException e) { // print exception if the file cannot be opened
                System.out.println("Error processing input file " + fileName);
            }
        }

        else { // Print a message if there is no image file for the word
            System.out.println("There is no image file for " + word);
        }
    }

    /**
     * @param dictionary to get data animation of label
     * @param command    label name to show animation
     */
    private static void animateCommand(BSTDictionary dictionary, String command) {

        String word = command.substring(8).trim(); // Skip "animate" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 7); // Type 7 indicates an animated image file
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {

            String fileName = record.getDataItem(); // get filename

            try { // display image
                PictureViewer pictureViewer = new PictureViewer();
                pictureViewer.show(fileName);
            }

            catch (MultimediaException e) { // print exception if the file cannot be opened
                System.out.println("Error processing input file " + fileName);
            }
        }

        else { // Print a message if there is no animation file for the word
            System.out.println("There is no animated image file for " + word);
        }
    }

    /**
     * @param dictionary to get data website of label
     * @param command    label name to show website
     */
    private static void browseCommand(BSTDictionary dictionary, String command) {

        String word = command.substring(7).trim(); // Skip "browse" and trim any leading/trailing spaces
        Key key = new Key(word.toLowerCase(), 8); // Type 8 indicates a webpage URL
        Record record = dictionary.get(key); // get record of word inputted

        // Check if the record exists
        if (record != null) {
            String url = record.getDataItem(); // get filename

            // display website
            ShowHTML htmlViewer = new ShowHTML();
            htmlViewer.show(url);
        }

        else { // Print a message if there is no website file for the word
            System.out.println("There is no webpage called " + word);
        }
    }

    /**
     * @param dictionary to get record to delete
     * @param command    label name to delete record
     */
    private static void deleteCommand(BSTDictionary dictionary, String command) throws DictionaryException {
        // Extract the key from the command
        String[] parts = command.split(" ");

        if (parts.length != 3) { // if command does not follow format to delete record
            System.out.println("Invalid delete command. Format: delete w k");
            return; // exit delete method
        }

        String word = parts[1]; // extract label
        String type = parts[2]; // extract type

        Key deleteKey = new Key(word, Integer.parseInt(type)); // get key to delete record

        Record check = dictionary.get(deleteKey); // get record to delete

        // check if record exists
        if (check == null) { // print message is record does not exist
            System.out.println("No record in the ordered dictionary has key (" + word + "," + type + ")");
        }

        else { // remove record from dictionary
            dictionary.remove(deleteKey);
        }
    }

    /**
     * @param dictionary to get record to add
     * @param command    label name to add record
     */
    private static void addCommand(BSTDictionary dictionary, String command) throws DictionaryException {
        // Extract the components from the command
        String[] parts = command.split(" ");

        if (parts.length < 3) { // if command does not follow format to add record
            System.out.println("Invalid add command. Format: add w t c");
            return; // exit add method
        }

        String word = parts[1]; // extract word
        String type = parts[2]; // extract type
        String data = command.substring(command.indexOf(type) + type.length() + 1); // Extracting data from the command

        Key key = new Key(word, Integer.parseInt(type)); // get key to add record

        Record existingRecord = dictionary.get(key); // check if record exists in dictionary

        if (existingRecord != null) { // if record exists print message
            System.out.println(
                    "A record with the given key (" + word + "," + type + ") is already in the ordered dictionary");
        } else {
            // Create a new record and insert it into the dictionary
            Record newRecord = new Record(key, data);
            dictionary.put(newRecord);
        }
    }

    /**
     * @param labelList list of labels in BSTDictionary
     * @param command   //prefix inputted
     * 
     *                  The listCommand method takes a list with all the labels
     *                  of all the items in the BSTDictionary and then given the
     *                  user prefix input, prints all the labels that start with
     *                  that prefix in alphabetical order.
     */
    private static void listCommand(ArrayList<String> labelList, String command) {
        // checks if labels exist in dictionary
        if (labelList == null) {
            return;
        }

        String word = command.substring(5).trim(); // Skip "list" and trim any leading/trailing spaces
        ArrayList<String> prefixLabels = new ArrayList<>(); // new array to store labels starting with prefix

        // Filter labels that start with the specified word
        for (String label : labelList) {
            if (label.startsWith(word)) {
                prefixLabels.add(label); // add words thats start with prefix to list
            }
        }

        // Sort the prefix labels alphabetically
        Collections.sort(prefixLabels);

        // Check if any labels were found
        if (prefixLabels.isEmpty()) { // prints message if no labels starting with prefix exist
            System.out.println("No labels attributes in the ordered dictionary start with prefix " + word);
        } else {
            // Print the sorted labels in the format required
            System.out.println(String.join(", ", prefixLabels));
        }
    }

    /**
     * @param dictionary to find the smallest record in the BSTDictionary
     * @throws DictionaryException
     */
    private static void firstCommand(BSTDictionary dictionary) throws DictionaryException {

        Record smallestRecord = dictionary.smallest(); // Get the record with the smallest key

        if (smallestRecord != null) {// check if record exists, else return nothing

            String label = smallestRecord.getKey().getLabel(); // get the label of the record
            int type = smallestRecord.getKey().getType(); // get the type of the record
            String data; // data of record

            if (1 < type && type < 6) {
                data = smallestRecord.getDataItem().substring(1); // if type 2-5 skip first character in data
            }

            else {
                data = smallestRecord.getDataItem(); // if type 1, 6-8 take full data item
            }

            // print the label, type and data of first record
            System.out.println(label + "," + type + "," + data);
        }
    }

    /**
     * @param dictionary to find the largest record in the BSTDictionary
     * @throws DictionaryException
     */
    private static void lastCommand(BSTDictionary dictionary) throws DictionaryException {

        Record largestRecord = dictionary.largest(); // Get the record with the smallest key

        if (largestRecord != null) { // check if record exists, else return nothing

            String label = largestRecord.getKey().getLabel(); // get the label of the record
            int type = largestRecord.getKey().getType(); // get the type of the record
            String data; // data of record

            if (1 < type && type < 6) {
                data = largestRecord.getDataItem().substring(1); // if type 2-5 skip first character in data
            }

            else {
                data = largestRecord.getDataItem(); // if type 1, 6-8 take full data item
            }

            // print the label, type and data of last record
            System.out.println(label + "," + type + "," + data);
        }
    }
}