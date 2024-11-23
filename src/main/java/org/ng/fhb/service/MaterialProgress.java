package org.ng.fhb.service;
import java.util.HashMap;
import java.util.Map;

public class MaterialProgress {
    // Map to store material progress (key: material name, value: {current progress, target})
    private final Map<String, Integer[]> materials = new HashMap<>();

    // Add a new material with a target
    public void addMaterial(String name, int target) {
        if (!materials.containsKey(name)) {
            materials.put(name, new Integer[]{0, target});
        } else {
            throw new IllegalArgumentException("Material already exists.");
        }
    }

    // Update progress for a material
    public void updateProgress(String name, int amount) {
        Integer[] progress = materials.get(name);
        if (progress != null) {
            progress[0] += amount;
        } else {
            throw new IllegalArgumentException("Material does not exist.");
        }
    }

    // Get the current progress and target for a material
    public Integer[] getProgress(String name) {
        return materials.get(name);
    }

    // Get all materials (for debugging or listing)
    public Map<String, Integer[]> getAllMaterials() {
        return new HashMap<>(materials);
    }
}

