package driver;

import enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class LocalDriverManager extends DriverManager {

    private final DriverType browser;
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public LocalDriverManager(DriverType browser) {
        this.browser = browser;
    }

    @Override
    protected WebDriver createDriver() {
        switch (browser) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-notifications");
                return new ChromeDriver(chromeOptions);
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.notification.enabled", false);
                return new FirefoxDriver(firefoxOptions);
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                return new EdgeDriver(edgeOptions);
            case SAFARI:
                SafariOptions safariOptions = new SafariOptions();
                return new SafariDriver(safariOptions);
            case IE:
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                ieOptions.ignoreZoomSettings();
                return new InternetExplorerDriver(ieOptions);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
