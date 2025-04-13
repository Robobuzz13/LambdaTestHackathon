package testScript;

import driver.DriverFactory;
import driver.DriverManager;
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigRead;
import utils.log.Log;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private DriverManager driverManager;

    @BeforeMethod
    public void setUp() {
        EnvironmentType environment = EnvironmentType.LAMBDA_TEST;
        DriverType browser = DriverType.CHROME;

        driverManager = DriverFactory.getManager(environment, browser);
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