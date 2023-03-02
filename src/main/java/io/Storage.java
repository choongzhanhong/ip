package io;

import task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saved storage.
 * Use this to save, write, and parse files.
 */
public final class Storage {

    private static final String DEFAULT_FILE_PATH = "data/papatask.txt";
    public static final String FILE_DELIMITER = "|";
    private String filePath; // To store custom file path and for methods to reference.

    /**
     * Constructor with custom FilePath defined
     * @param filePath Where the saved files are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        openFile();
    }

    /**
     * Constructor without filepath defined.
     * Uses default file path ({@value DEFAULT_FILE_PATH}) and runs constructor as usual.
     */
    public Storage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Open the saved tasks file upon startup of PAPA.<br>
     * Checks if the directory and text file exist, and writes to the file.
     */
    private void openFile() {
        File file = new File(this.filePath);

        // Create the directories required (if not exist)
        if (!file.getParentFile().exists()) {
            // Make directories, return true if successful.
            if (file.getParentFile().mkdirs()) {
                System.out.println("Successfully created directory in " + file.getAbsolutePath());
            } else {
                System.out.println("Directory exists or unable to create.");
            }
        }

        // Check if file exists.
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred in creating a new save file.");
            }
            System.out.println("New save file created in " + file.getAbsolutePath());
        } else {
            System.out.println("Loaded your saved tasks.");
        }
    }

    /**
     * Loads and parses each line of data in the save file.
     * @return ArrayList ({@link java.util.ArrayList}) of String (Task in parseable string format).
     */
    public ArrayList<String> load() {
        File f = new File(this.filePath);
        Scanner s;
        ArrayList<String> output = new ArrayList<String>();
        try {
            s = new Scanner(f);
            while (s.hasNext()) {
                output.add(readFileLine(s.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found >_<: " + e.getMessage());
        }
        return output;
    }

    /**
     * Read individual lines of the save file.
     * @param line each line of the file.
     * @return String representation of Task.
     */
    private String readFileLine(String line) {
        String output = "";

        // Delimiter and any amount of whitespace on left/right.
        // Backslashes are also to escape regex \\.
        String[] linesSplit = line.split("\\s+" + "\\" + FILE_DELIMITER + "\\s+");

        // Currently assumes the file has not been tampered with.
        // Very simplistic checks.
        switch (linesSplit[0]) {
        case "T":
            // Has to contain T, isDone, and Description
            if (linesSplit.length == 3) {
                output = formatTaskAsString(linesSplit);
            }
            // FALLTHROUGH
        case "D":
            // Has to contain D, isdone, description, by
            if (linesSplit.length == 4) {
                output = formatTaskAsString(linesSplit);
            }
            // FALLTHROUGH
        case "E":
            // Has to contain E, isdone, description, from, to
            if (linesSplit.length == 5) {
                output = formatTaskAsString(linesSplit);
            }
            // FALLTHROUGH
        default:
            System.out.println("I think there's an error with the file.");
        }
        return output;
    }

    /**
     * Method to format Save File Task to TaskList parseable format.
     * Simply just concatenate the strings together.
     * @param linesSplit String[] of Task properties.
     * @return One whole String
     */
    private String formatTaskAsString(String[] linesSplit) {
        String output = "";
        for (String s : linesSplit) {
            output += s;
            output += " ";
        }
        // Just to remove the last trailing whitespace (or any other whitespace in input).
        return output.trim();
    }

    /**
     * Writes input text to file.
     * This will overwrite existing contents (Intended, for updating)
     * @param textToAdd Everything to be written into the file.
     * @throws IOException Unable to write successfully.
     */
    public void writeToFile(String textToAdd) {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            writer.write(textToAdd);
            // Add newline
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.out.println("Something wrong: " + e.getMessage());
        }
    }
}
