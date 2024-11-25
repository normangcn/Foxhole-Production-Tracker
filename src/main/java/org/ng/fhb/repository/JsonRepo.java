package org.ng.fhb.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.ng.fhb.model.BotSupplyData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JsonRepo {
    private static final String FILE_PATH = "src/main/resources/data.json";
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
                // Initialize new data if the file doesn't exist
                data = new BotSupplyData();
                data.setItems(new HashMap<>()); // Initialize empty items map
                saveData(); // Save initial structure
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
            e.printStackTrace();
        }
    }
}
