package persistence;

import org.json.JSONObject;

// Writable interface
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
