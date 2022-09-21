package gr2219.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;

public class TraveluHandler {

    public void writeJSON(DestinationList log) throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter("persistence.json");
        writer.write(gson.toJson(log));
        writer.close();
    }

    public DestinationList readJSON() throws FileNotFoundException {
        Gson gson = new Gson();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("persistence.json"));
        DestinationList log = gson.fromJson(bufferedReader, DestinationList.class);
        return log;
    }

}
