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

import travelu.core.Destination;
import travelu.core.DestinationList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles file reading and writing
 */
public class TraveluHandler {

    private final static String DEFAULT_FILENAME_DLIST = "DestinationList.json";
    private final static String DEFAULT_FILENAME_CURRENTD = "CurrentDestinationName.json";

    /**
     * @param filename
     * @return the filepath for the given filename
     */
    private static String getFilePath(String filename) {
        Path path = Paths.get("../localpersistence/src/main/resources/travelu/localpersistence/data");
        return (path.toAbsolutePath() + "/" + filename);
    }

    /**
     * Used for reading and writing
     * <p>
     * Written for easy scalability
     * 
     * @return File
     */
    private static File getFile(String filename) {
        return new File(getFilePath(filename));
    }

    /**
     * Writes to given file in {@code JSON Format}using {@code Gson}
     * 
     * @param object   usually destination list
     * @param filename writing to
     * @throws IOException
     */
    public static void writeJSON(Object object, String filename) throws IOException {
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
     * {@code Gson}
     * 
     * @param destinationList
     * @throws IOException
     */
    public static void save(DestinationList destinationList) throws IOException {
        writeJSON(destinationList, DEFAULT_FILENAME_DLIST);
    }

    /**
     * Writes {@link Destination} to default file using {@code GSON}
     * 
     * @param destination
     * @throws IOException
     */
    public static void saveDestination(Destination destination) throws IOException {
        writeJSON(destination, DEFAULT_FILENAME_CURRENTD);
    }

    /**
     * Read from file using {@code Gson}, where it reads from DestinationList object
     * <p>
     * Used in testing and have therefore an own filename input
     * 
     * @param filename input
     * @return {@linkplain DestinationList} with destinations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static DestinationList readDestinationListJSON(String filename) throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));
        DestinationList DList = gson.fromJson(bufferedReader, DestinationList.class);
        return DList;
    }

    /**
     * Read from file using {@code Gson}, where it reads from DestinationList object
     * <p>
     * Reading from standard file
     * 
     * @return {@linkplain DestinationList} with destinations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static DestinationList readDestinationListJSON() throws FileNotFoundException, IOException {
        return readDestinationListJSON(DEFAULT_FILENAME_DLIST);
    }

    /**
     * Read from file using {@code Gson}, where it reads from DestinationName
     * <p>
     * Used in testing and have therefore an own filename input
     * 
     * @param filename input
     * @return {@linkplain DestinationList} with destinations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readCurrentDestinationNameJSON(String filename) throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));
        String currentDestinationName = gson.fromJson(bufferedReader, String.class);
        return currentDestinationName;
    }

    /**
     * Read from file using {@code Gson}, where it reads from DestinationName
     * <p>
     * Reading from standard file
     * 
     * @return {@linkplain DestinationList} with destinations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readCurrentDestinationNameJSON() throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(getFile(DEFAULT_FILENAME_CURRENTD), Charset.defaultCharset()));
        String currentDestinationName = gson.fromJson(bufferedReader, String.class);
        return currentDestinationName;
    }

}
