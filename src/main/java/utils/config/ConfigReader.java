package utils.config;

import utils.log.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    private static final Map<String, Properties> loadedProperties = new HashMap<>();
    private static String currentFileName = null;

    public static synchronized void load(String fileName) {
        String key = fileName.toLowerCase();
        if (loadedProperties.containsKey(key)) return;

        String filePath = "src/test/resources/config/" + key + ".properties";
        try (FileInputStream input = new FileInputStream(filePath)) {
            Properties props = new Properties();
            props.load(input);
            loadedProperties.put(key, props);
            if (currentFileName == null) currentFileName = key; // Set default
            Log.info("[ConfigReader] Loaded config file: " + filePath);
        } catch (IOException e) {
            Log.exception(e);
            throw new RuntimeException("Failed to load config file: " + filePath, e);
        }
    }

    public static synchronized void setCurrent(String fileName) {
        String key = fileName.toLowerCase();
        if (!loadedProperties.containsKey(key)) {
            load(key);
        }
        currentFileName = key;
    }

    public static synchronized void reload(String fileName) {
        String key = fileName.toLowerCase();
        loadedProperties.remove(key);
        load(fileName);
    }

    // --- Default (current) file access ---
    public static String get(String key) {
        return get(currentFileName, key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    // --- Specific file access ---
    public static String get(String fileName, String key) {
        Properties props = loadedProperties.get(fileName.toLowerCase());
        if (props == null) {
            throw new IllegalStateException("Config file not loaded: " + fileName);
        }
        return props.getProperty(key);
    }

    public static boolean getBoolean(String fileName, String key) {
        return Boolean.parseBoolean(get(fileName, key));
    }

    public static int getInt(String fileName, String key) {
        return Integer.parseInt(get(fileName, key));
    }

}
