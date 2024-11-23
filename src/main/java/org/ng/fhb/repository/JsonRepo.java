package org.ng.fhb;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

    public class JsonRepo {
        private static final String FILE_PATH = "data.json";
        private BotData data;
        private final ObjectMapper mapper = new ObjectMapper();

        public JsonRepo() {
            try {
                File file = new File(FILE_PATH);
                if (file.exists()) {
                    data = mapper.readValue(file, BotData.class);
                } else {
                    data = new BotData();
                    saveData(); // Initialize file if not present
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public BotData getData() {
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

