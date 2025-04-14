package driver;

import enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

public class GridDriverManager extends DriverManager {

    private final DriverType browser;
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public GridDriverManager(DriverType browser) {
        this.browser = browser;
    }

    @Override
    protected WebDriver createDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.getDriverName());
        try {
            return new RemoteWebDriver(new URL(ConfigReader.get("grid.url")), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium Grid URL", e);
        }
    }
}
