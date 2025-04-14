package utils.config;

import java.util.Arrays;
import java.util.List;

public class LambdaTestConfig {

    private static final String FILE_NAME = "lambdatest";

    static {
        ConfigReader.load(FILE_NAME);
    }

    public static String getPlatformName() {
        return get("platformName");
    }

    public static String getBrowserVersion() {
        return get("browserVersion");
    }

    public static String getGeoLocation() {
        return get("lt.geoLocation");
    }

    public static boolean isVisualEnabled() {
        return getBoolean("lt.visual");
    }

    public static boolean isVideoEnabled() {
        return getBoolean("lt.video");
    }

    public static boolean isHeadless() {
        return getBoolean("lt.headless");
    }

    public static boolean isSeCdpEnabled() {
        return getBoolean("lt.seCdp");
    }

    public static boolean isNetworkEnabled() {
        return getBoolean("lt.network");
    }

    public static String getTimeZone() {
        return get("lt.timezone");
    }

    public static String getBuildName() {
        return get("lt.build");
    }

    public static String getProjectName() {
        return get("lt.project");
    }

    public static List<String> getBuildTags() {
        return Arrays.asList(get("lt.buildTags").split(","));
    }

    public static String getTestName() {
        return get("lt.name");
    }

    public static List<String> getTags() {
        return Arrays.asList(get("lt.tags").split(","));
    }

    public static boolean isTunnelEnabled() {
        return getBoolean("lt.tunnel");
    }

    public static String getConsoleLogLevel() {
        return get("lt.console");
    }

    public static String getNetworkThrottling() {
        return get("lt.networkThrottling");
    }

    public static boolean isW3CEnabled() {
        return getBoolean("lt.w3c");
    }

    private static String get(String key) {
        return ConfigReader.get(FILE_NAME, key);
    }

    private static boolean getBoolean(String key) {
        return ConfigReader.getBoolean(FILE_NAME, key);
    }
}
