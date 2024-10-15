import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JsonWriter {
    public static void writeJsonToFile(String filePath, LinkedHashMap<String, Object> data) {
        String jsonSpace = ", " + "\n";

        try (FileWriter file = new FileWriter(filePath, true)) {
            file.write(mapToJson(data) + jsonSpace);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String mapToJson(LinkedHashMap<String, Object> data) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        int size = data.size();
        int count = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            json.append("  \"").append(entry.getKey()).append("\": ");
            if (entry.getValue() instanceof String) {
                json.append("\"").append(entry.getValue()).append("\"");
            } else {
                json.append(entry.getValue());
            }
            count++;
            if (count < size) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("}");
        return json.toString();
    }
}

