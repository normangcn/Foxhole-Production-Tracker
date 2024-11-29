package org.ng.fhb.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.ng.fhb.model.BotSupplyData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonRepo {
    private static final String FILE_PATH = System.getProperty("data.file.path", "data.json");
    BotSupplyData botSupplyData = new BotSupplyData();
    private BotSupplyData data;
    private final ObjectMapper mapper = new ObjectMapper();

    public JsonRepo() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                // Deserialize JSON into BotSupplyData
                data = mapper.readValue(file, BotSupplyData.class);
            } else {
                System.out.println("Json file doesn't exist");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BotSupplyData getData() {
        return data;
    }

    public void saveData() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), data);
        } catch (IOException e) {
            System.err.println("Failed to save data to JSON file.");
            e.printStackTrace();
            // Optionally, throw a custom exception
            throw new RuntimeException("Error saving data", e);
        }
    }
}
