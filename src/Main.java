import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        switch (args[0]) {
            case "add" -> add(args[1]);
            case "update" -> updateTask(Integer.parseInt(args[1]), args[2]);
            case "delete" -> deleteTask(Integer.parseInt(args[1]));
            case "list" -> listTasks(args.length == 1 ? null : args[1]);
            case "mark-in-progress" -> markIn(Integer.parseInt(args[1]), "in-progress");
            case "mark-done" -> markIn(Integer.parseInt(args[1]), "done");
        }
    }
    private static void add(String name) {
        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
        data.put("id", JsonReader.find());
        data.put("description", name);
        data.put("status", "todo");
        data.put("createdAt", getTime());
        data.put("updatedAt", getTime());
        JsonWriter.writeJsonToFile("tasks.json", data);
    }
    private static void updateTask(int id, String description) {
        LinkedHashMap<Integer, LinkedHashMap<String, Object>> Task = JsonReader.jsonToMap(JsonReader.readJsonFromFileStrings("tasks.json"));
        assert Task != null;
        Task.get(id).put("description", description);
        Task.get(id).put("updatedAt", getTime());
        File temp = new File("tasks.json");
        temp.delete();
        for (Map.Entry<Integer, LinkedHashMap<String, Object>> map : Task.entrySet()) {
            JsonWriter.writeJsonToFile("tasks.json", map.getValue());
        }
    }
    private static void deleteTask(int id) {
        LinkedHashMap<Integer, LinkedHashMap<String, Object>>  Task = JsonReader.jsonToMap(JsonReader.readJsonFromFileStrings("tasks.json"));
        assert  Task != null;
        File temp = new File("tasks.json");
        temp.delete();
        Task.remove(id);
        for(Map.Entry<Integer, LinkedHashMap<String, Object>> map : Task.entrySet()){
            JsonWriter.writeJsonToFile("tasks.json", map.getValue());
        }
    }
    private static void markIn(int id, String status)
    {
        LinkedHashMap<Integer, LinkedHashMap<String, Object>>  Task = JsonReader.jsonToMap(JsonReader.readJsonFromFileStrings("tasks.json"));
        assert  Task != null;
        File temp = new File("tasks.json");
        temp.delete();
        Task.get(id).put("status", status);
        Task.get(id).put("updatedAt", getTime());
        for(Map.Entry<Integer, LinkedHashMap<String, Object>> map : Task.entrySet()){
            JsonWriter.writeJsonToFile("tasks.json", map.getValue());
        }
    }
    private static void listTasks(String status)
    {
        LinkedHashMap<Integer, LinkedHashMap<String, Object>>  Task = JsonReader.jsonToMap(JsonReader.readJsonFromFileStrings("tasks.json"));
        assert  Task != null;
        for(Map.Entry<Integer, LinkedHashMap<String, Object>> map : Task.entrySet()){
            if(!map.getValue().get("status").equals(status) && status != null){continue;}
            System.out.println(map.getValue().get("description"));
        }
    }
    public static String getTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return currentDateTime.format(formatter);
    }

}
