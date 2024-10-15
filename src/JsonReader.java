import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.max;

public class JsonReader {
    public static String readJsonFromFileStrings(String filePath) {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line.trim());
            }
        } catch (IOException e) {
            return null;
        }
        return jsonContent.toString();
    }
    public static int find()
    {
        try{
            LinkedHashMap<Integer, LinkedHashMap<String, Object>> read = jsonToMap(JsonReader.readJsonFromFileStrings("tasks.json"));
            assert read != null;
            return max(read.keySet()) +1;
        }catch(NullPointerException e){
            return 1;
        }
    }
    public static LinkedHashMap<Integer, LinkedHashMap<String, Object>> jsonToMap(String json) {
        try{
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            LinkedHashMap<Integer, LinkedHashMap<String, Object>> allData = new LinkedHashMap<>();
            json = json.replace("{", "").replace("}", "").trim();
            String[] pairs = json.split(",");
            int line = 0;
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                String key = keyValue[0].trim().replace("\"", "");
                String value = keyValue[1].trim();
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);  // Remove quotes for strings
                    data.put(key, value);
                } else if (value.equals("true") || value.equals("false")) {
                    data.put(key, Boolean.parseBoolean(value));
                } else {
                    try {
                        data.put(key, Integer.parseInt(value));
                    } catch (NumberFormatException e) {
                        data.put(key, value);
                    }
                }
                line++;
                if(line == 5)
                {
                    allData.put((Integer) data.get("id"), new LinkedHashMap<>(data));
                    data.clear();
                    line = 0;
                }
            }
            return allData;
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }

}
