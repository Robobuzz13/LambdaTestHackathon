package testScript;

import driver.DriverFactory;
import driver.DriverManager;
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigRead;
import utils.log.Log;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DriverManager driverManager;

    @BeforeMethod
    @Parameters({"environmentValue", "browserValue"})
    public void setUp(String environmentValue, String browserValue) {
        EnvironmentType envType = (environmentValue == null || environmentValue.isEmpty()) ?
                EnvironmentType.LAMBDA_TEST : EnvironmentType.valueOf(environmentValue.toUpperCase());
        DriverType browserType = (browserValue == null || browserValue.isEmpty()) ?
                DriverType.CHROME : DriverType.valueOf(browserValue.toUpperCase());

        driverManager = DriverFactory.getManager(envType, browserType);
        driver = driverManager.getDriver();

        driver.get(ConfigRead.get("loginUrl"));
        loginPage = new LoginPage(driver);
    }

    @Test()
    public void testValidLogin() {
        loginPage.loginAs(ConfigRead.get("loginUser"), System.getProperty("LOGIN_PASSWORD"));
        Log.message("Login attempted with valid credentials.");
        loginPage.assertSuccessMessage(ConfigRead.get("successLoginMessage"));
        loginPage.clickLogout();
    }

    @Test()
    public void testInvalidLogin() {
        loginPage.loginAs(ConfigRead.get("invalidUser"), ConfigRead.get("invalidPassword"));
        Log.message("Login attempted with invalid credentials.");
        loginPage.assertFailureMessage(ConfigRead.get("failLoginMessage"));
    }

    @AfterMethod
    public void tearDown() {
        if (driverManager != null) {
            driverManager.quitDriver();
        }
    }
}