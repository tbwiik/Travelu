package app.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles file reading and writing
 */
public class TraveluHandler {

    /**
     * Return file to use in reading/writing
     * <p>
     * Written for easy scalability
     * 
     * @return File
     */
    private File getFile() {
        return new File(TraveluHandler.class.getResource("data/").getFile() + "persistence.json");
    }

    /**
     * Writes to given file in {@code JSON Format}using {@code Gson}
     * 
     * @param DList destination list
     * @throws IOException
     */
    public void writeJSON(DestinationList DList) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(getFile());
        writer.write(gson.toJson(DList));
        writer.close();
    }

    /**
     * Read from file using {@code Gson}
     * 
     * @return Destination list
     * @throws FileNotFoundException if file not found
     */
    public DestinationList readJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
        DestinationList DList = gson.fromJson(bufferedReader, DestinationList.class);
        return DList;
    }

}
