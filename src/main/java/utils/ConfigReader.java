package utils;

import utils.log.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static volatile boolean isLoaded = false;

    /**
     * Thread-safe lazy loading of config
     */
    public static synchronized void load(String fileName) {
        if (isLoaded) return;
        String filePath = "src/test/resources/" + fileName.toLowerCase() + ".properties";
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            isLoaded = true;
            Log.info("[ConfigReader] Loaded config file: " + filePath);
        } catch (IOException e) {
            Log.exception(e);
            throw new RuntimeException("Failed to load config file: " + filePath, e);
        }
    }

    public static synchronized void reload(String fileName) {
        isLoaded = false;
        load(fileName);
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
