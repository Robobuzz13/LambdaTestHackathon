package driver;

import enums.DriverType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import utils.config.LambdaTestConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class LambdaTestDriverManager extends DriverManager {

    private final DriverType browser;
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public LambdaTestDriverManager(DriverType browser) {
        this.browser = browser;
    }

    private MutableCapabilities createBrowserOptions() {
        return switch (browser) {
            case CHROME -> new ChromeOptions();
            case FIREFOX -> new FirefoxOptions();
            case EDGE -> new EdgeOptions();
            case SAFARI -> new SafariOptions();
            case IE -> new InternetExplorerOptions();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    @Override
    protected WebDriver createDriver() {
        MutableCapabilities browserOptions = createBrowserOptions();
        browserOptions.setCapability(CapabilityType.PLATFORM_NAME, getEnvOrLambdaConfig("lt.platformName", LambdaTestConfig::getPlatformName));
        browserOptions.setCapability(CapabilityType.BROWSER_VERSION, getEnvOrLambdaConfig("lt.browserVersion", LambdaTestConfig::getBrowserVersion));

        HashMap<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("username", System.getProperty("LT_USERNAME"));
        ltOptions.put("accessKey", System.getProperty("LT_ACCESS_KEY"));
        ltOptions.put("geoLocation", getEnvOrLambdaConfig("lt.geoLocation", LambdaTestConfig::getGeoLocation));
        ltOptions.put("visual", getEnvOrLambdaConfigBoolean("lt.visual", LambdaTestConfig::isVisualEnabled));
        ltOptions.put("video", getEnvOrLambdaConfigBoolean("lt.video", LambdaTestConfig::isVideoEnabled));
        ltOptions.put("headless", getEnvOrLambdaConfigBoolean("lt.headless", LambdaTestConfig::isHeadless));
        ltOptions.put("seCdp", getEnvOrLambdaConfigBoolean("lt.seCdp", LambdaTestConfig::isSeCdpEnabled));
        ltOptions.put("network", getEnvOrLambdaConfigBoolean("lt.network", LambdaTestConfig::isNetworkEnabled));
        ltOptions.put("timezone", getEnvOrLambdaConfig("lt.timezone", LambdaTestConfig::getTimeZone));
        ltOptions.put("build", getEnvOrLambdaConfig("lt.build", LambdaTestConfig::getBuildName));
        ltOptions.put("project", getEnvOrLambdaConfig("lt.project", LambdaTestConfig::getProjectName));
        ltOptions.put("buildTags", getEnvOrListConfig("lt.buildTags", LambdaTestConfig::getBuildTags));
        ltOptions.put("name", getEnvOrLambdaConfig("lt.name", LambdaTestConfig::getTestName));
        ltOptions.put("tags", getEnvOrListConfig("lt.tags", LambdaTestConfig::getTags));
        ltOptions.put("tunnel", getEnvOrLambdaConfigBoolean("lt.tunnel", LambdaTestConfig::isTunnelEnabled));
        ltOptions.put("console", getEnvOrLambdaConfig("lt.console", LambdaTestConfig::getConsoleLogLevel));
        ltOptions.put("networkThrottling", getEnvOrLambdaConfig("lt.networkThrottling", LambdaTestConfig::getNetworkThrottling));
        ltOptions.put("w3c", getEnvOrLambdaConfigBoolean("lt.w3c", LambdaTestConfig::isW3CEnabled));

        browserOptions.setCapability("LT:Options", ltOptions);

        try {
            return new RemoteWebDriver(
                    new URL("https://hub.lambdatest.com/wd/hub"),
                    browserOptions
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid LambdaTest URL", e);
        }
    }

    private String getEnvOrLambdaConfig(String key, Supplier<String> fallback) {
        String env = System.getProperty(key);
        return (env != null && !env.isEmpty()) ? env : fallback.get();
    }

    private Boolean getEnvOrLambdaConfigBoolean(String key, Supplier<Boolean> fallback) {
        String env = System.getProperty(key);
        return (env != null && !env.isEmpty()) ? Boolean.parseBoolean(env) : fallback.get();
    }

    private String[] getEnvOrListConfig(String envKey, Supplier<List<String>> fallbackSupplier) {
        String value = System.getProperty(envKey);
        return (value != null && !value.isEmpty())
                ? value.split(",")
                : fallbackSupplier.get().toArray(new String[0]);
    }
}
