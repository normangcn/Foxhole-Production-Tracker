package org.ng.fhb;
import java.util.HashMap;
import org.ng.fhb.FoxholeBot.*;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    // Map to store contributions per material (key: material name, value: {user -> contribution})
    private final Map<String, Map<String, Integer>> leaderboards = new HashMap<>();

    // Add a user's contribution to a material
    public void addContribution(String material, String user, int amount) {
        leaderboards.computeIfAbsent(material, k -> new HashMap<>())
                .merge(user, amount, Integer::sum);
    }

    // Get leaderboard for a specific material
    public Map<String, Integer> getLeaderboard(String material) {
        return leaderboards.getOrDefault(material, new HashMap<>());
    }

    // Get all leaderboards (for debugging or listing)
    public Map<String, Map<String, Integer>> getAllLeaderboards() {
        return new HashMap<>(leaderboards);
    }
}

