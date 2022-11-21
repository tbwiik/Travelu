package travelu.localpersistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import travelu.core.DestinationList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles file reading and writing.
 */
public class TraveluHandler {

    /**
     * Default filename for destination-list.
     */
    private static final String DEFAULT_FILENAME_DLIST = "DestinationList.json";

    /**
     * Default filename for name of current destination.
     */
    private static final String DEFAULT_FILENAME_CURRENTD = "CurrentDestinationName.json";

    /**
     * Get file path for stored files.
     *
     * @param filename to add to path
     * @return the filepath for the given filename
     */
    private static String getFilePath(String filename) {
        Path path = Paths.get("../localpersistence/src/main/resources/travelu/localpersistence/data");
        return (path.toAbsolutePath() + "/" + filename);
    }

    /**
     * Get file using {@link #getFilePath(String)}.
     *
     * @param filename of wanted file
     * @return file if any
     */
    private static File getFile(final String filename) {
        return new File(getFilePath(filename));
    }

    /**
     * Writes to given file in {@code JSON Format}using {@link Gson}.
     *
     * @param object   usually destination list
     * @param filename writing to
     * @throws IOException if I/O fail
     */
    public static void writeJSON(final Object object, final String filename) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(getFile(filename), Charset.defaultCharset());
        try {
            writer.write(gson.toJson(object));
        } finally {
            writer.close();
        }
    }

    /**
     * Writes {@link DestinationList} to default file in {@code JSON Format}using
     * {@code Gson}.
     *
     * @param destinationList
     * @throws IOException if I/O fail
     */
    public static void save(final DestinationList destinationList) throws IOException {
        writeJSON(destinationList, DEFAULT_FILENAME_DLIST);
    }

    /**
     * Writes name of {@link Destination} to default file using {@code GSON}.
     *
     * @param destinationName to write to file
     * @throws IOException if I/O fail
     */
    public static void saveDestinationName(final String destinationName) throws IOException {
        writeJSON(destinationName, DEFAULT_FILENAME_CURRENTD);
    }

    /**
     * Load destination-list from file.
     * <p>
     * If no file exists, or are corrupted: empty destination-list will be written
     * to new file
     * <p>
     * Field for filename input due to use in testing
     *
     * @param filename to read destination from
     * @return {@link DestinationList} - with no elements if blank, corrupted or no
     *         file
     * @throws IOException if I/O fail
     */
    public static DestinationList readDestinationListJSON(final String filename) throws IOException {

        // Init fields
        Gson gson = new Gson();
        DestinationList destinationList;
        BufferedReader bufferedReader;

        try {
            // Read from file
            bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));

            // Convert destination-list from file
            destinationList = gson.fromJson(bufferedReader, DestinationList.class);

        } catch (FileNotFoundException e) {
            // If no file exists, or format of data is corrupted, creates new file,
            // write and return empty list
            destinationList = new DestinationList();
            writeJSON(destinationList, filename);
            return destinationList;
        }

        // If file is blank, create destinationlist
        if (destinationList == null) {
            destinationList = new DestinationList();
        }

        return destinationList;
    }

    /**
     * Load destination from default file using
     * {@link #readDestinationListJSON(String)}.
     *
     * @return {@link DestinationList} - empty if failures
     * @throws IOException if I/O fail
     */
    public static DestinationList readDestinationListJSON() throws IOException {
        return readDestinationListJSON(DEFAULT_FILENAME_DLIST);
    }

    /**
     * Load current destination-name.
     * <p>
     * If no file exists, or are corrupted: empty string will be written to new file
     * <p>
     * Field for filename input due to use in testing
     *
     * @param filename destination file
     * @return name of current destination - empty string if none
     * @throws IOException if I/O fail
     */
    public static String readCurrentDestinationNameJSON(final String filename) throws IOException {

        // Init fields
        Gson gson = new Gson();
        String currentDestinationName = "";
        BufferedReader bufferedReader;

        try {
            // Read from file
            bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));

            // Convert to correct format
            currentDestinationName = gson.fromJson(bufferedReader, String.class);

        } catch (FileNotFoundException e) {
            // If no file exists, or format of data is corrupted, write empty string to file
            writeJSON("", filename);
            return currentDestinationName;
        }

        return currentDestinationName;
    }

    /**
     * Load current destination-name from default file using
     * {@link #readCurrentDestinationNameJSON(String)}.
     *
     * @return name of destination - empty string if failures
     * @throws IOException
     */
    public static String readCurrentDestinationNameJSON() throws IOException {
        return readCurrentDestinationNameJSON(DEFAULT_FILENAME_CURRENTD);
    }

    /**
     * Clear destination name from default file.
     *
     * @throws IOException if I/O fail
     */
    public static void clearDestinationName() throws IOException {
        saveDestinationName("");
    }

    /**
     * Clear destinations from default file.
     * <p>
     * This is done by writing empty {@link DestinationList} to file
     *
     * @throws IOException if I/O fail
     */
    public static void clearDestinationList() throws IOException {
        save(new DestinationList());
    }

}
