import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigParser {
    private Map<String, String> configMap;

    public ConfigParser(String fileName) {
        this.configMap = new HashMap<>();
        String env = System.getProperty("env", "production");
        String filePath = "production".equals(env) ? fileName + ".txt" : fileName + ".txt." + env;

        try(InputStream input = getClass().getResourceAsStream(filePath);
        BufferedReader buffered = new BufferedReader(new InputStreamReader(input))){
            String line;
            String section = "";

            while ((line = buffered.readLine()) != null){
                line.trim();

                if(line.isBlank() || line.startsWith("#") || line.startsWith("//") || line.startsWith(";")){
                    continue;
                }

                if(line.startsWith("[") && line.endsWith("]")){
                    section = line.substring(1, (line.length() - 1));
                    continue;
                }

                if(line.contains("=")){
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    String fullKey;
                    if(section.isEmpty()){
                        fullKey = key;
                    } else {
                        fullKey = section + "." + key;
                    }

                    if(!configMap.containsKey(fullKey)){
                        configMap.put(fullKey, value);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private void parse(String filePath) {
//
//        try(BufferedReader buffered = new BufferedReader(new FileReader(filePath))) {
//
//
//
//        } catch (IOException e) {
//            throw new RuntimeException("Could not read config file: " + filePath, e);
//        }
//    }

    public String get(String key) {
        String value = configMap.get(key);
        return value;
    }
}
