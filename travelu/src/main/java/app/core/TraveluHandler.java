package app.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TraveluHandler {

    private File getFile() {
        return new File(TraveluHandler.class.getResource("data/").getFile() + "persistence.json");
    }

    public void writeJSON(DestinationList log) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter(getFile());
        writer.write(gson.toJson(log));
        writer.close();
    }

    public DestinationList readJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
        DestinationList log = gson.fromJson(bufferedReader, DestinationList.class);
        return log;
    }

}
