package org.ng.fhb.service;

import org.ng.fhb.model.BotSupplyData;
import org.ng.fhb.repository.JsonRepo;

import java.util.HashMap;

public class BotService {
    private final JsonRepo jsonManager;

    public BotService(JsonRepo jsonManager) {
        this.jsonManager = jsonManager;
    }

//    public void updateContribution(String user, String material, int amount) {
//        BotSupplyData data = jsonManager.getData();
//
//        // Update contribution
//        data.getContributions()
//                .computeIfAbsent(user, k -> new HashMap<>())
//                .merge(material, amount, Integer::sum);
//
//        // Save updated data
//        jsonManager.saveData();
//    }

    public void displayProgress() {
        BotSupplyData data = jsonManager.getData();
    }
}
