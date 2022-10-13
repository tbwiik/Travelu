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
// Update ignore.xml if change in file
public class TraveluHandler {

    private static String getFilePath(String filename) {
        Path path = Paths.get("../fxutil/src/main/resources/travelu/fxutil/data");
        return (path.toAbsolutePath() + "/" + filename);
    }

    /**
     * Return file to use in reading/writing
     * <p>
     * Written for easy scalability
     * 
     * @return File
     */
    private File getFile(String filename) {
        return new File(getFilePath(filename));
        // return new File(TraveluHandler.class.getResource("data/").getFile() +
        // "persistence.json");
        // return new
        // File("C:/Users/johnh/Documents/ITP/Prosjekt/gr2219/travelu/fxutil/src/main/resources/travelu/fxutil/data/persistence.json");
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
        // Need charset to ensure safe writing
        FileWriter writer = new FileWriter(getFile(filename), Charset.defaultCharset());
        try {
            writer.write(gson.toJson(object));
        } finally {
            writer.close();
        }
    }

    /**
     * Read from file using {@code Gson}
     * 
     * @return Destination list
     * @throws FileNotFoundException if file not found
     */
    public DestinationList readDestinationListJSON(String filename) throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));
        DestinationList DList = gson.fromJson(bufferedReader, DestinationList.class);
        return DList;
    }

    public DestinationList readDestinationListJSON() throws FileNotFoundException, IOException {
        return readDestinationListJSON("DestinationList.json");
    }

    public String readCurrentDestinationNameJSON(String filename) throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile(filename), Charset.defaultCharset()));
        String currentDestinationName = gson.fromJson(bufferedReader, String.class);
        return currentDestinationName;
    }

    public String readCurrentDestinationNameJSON() throws FileNotFoundException, IOException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile("CurrentDestinationName.json")));
        String currentDestinationName = gson.fromJson(bufferedReader, String.class);
        return currentDestinationName;
    }

}
