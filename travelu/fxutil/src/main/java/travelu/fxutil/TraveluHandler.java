package travelu.fxutil;

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
 * Handles file reading and writing
 */
public class TraveluHandler {
    
    /**
     * @param filename
     * @return the filepath for the given filename 
     */
    private static String getFilePath(String filename) {
        Path path = Paths.get("../fxutil/src/main/resources/travelu/fxutil/data");
        return (path.toAbsolutePath() + "/" + filename);
    }

    /**
     * Used for reading and writing
     * <p>
     * Written for easy scalability
     * 
     * @return File
     */
    private File getFile(String filename) {
        return new File(getFilePath(filename));
    }

    /**
     * Writes to given file in {@code JSON Format}using {@code Gson}
     * 
     * @param DList destination list
     * @throws IOException
     */
    public void writeJSON(Object object, String filename) throws IOException {
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
     * Read from file using {@code Gson}, where it reads from DestinationList object
     * <p>
     * Used in testing and have therefore an own filename input
     * 
     * @param filename input
     * @return {@linkplain DestinationList} with destinations
     * @throws FileNotFoundException
     * @throws IOException
     */
    public DestinationList readDestinationListJSON(String filename) throws FileNotFoundException, IOException {
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
    public DestinationList readDestinationListJSON() throws FileNotFoundException, IOException {
        return readDestinationListJSON("DestinationList.json");
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
    public String readCurrentDestinationNameJSON(String filename) throws FileNotFoundException, IOException {
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
    public String readCurrentDestinationNameJSON() throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(getFile("CurrentDestinationName.json"), Charset.defaultCharset()));
        String currentDestinationName = gson.fromJson(bufferedReader, String.class);
        return currentDestinationName;
    }

}
