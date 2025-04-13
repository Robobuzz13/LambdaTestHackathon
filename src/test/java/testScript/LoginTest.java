package testScript;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.log.Log;



public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver(); // uses ThreadLocal
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
    }

    @Test(description = "Valid login test")
    public void testValidLogin() {
        loginPage.loginAs("tomsmith", "SuperSecretPassword!");
        Log.message("Login attempted with valid credentials.");
        loginPage.assertSuccessMessage("You logged into a secure area!");
    }

    @Test(description = "Invalid login test")
    public void testInvalidLogin() {
        loginPage.loginAs("invalidUser", "invalidPass");
        Log.message("Login attempted with invalid credentials.");
        loginPage.assertFailureMessage("Your username is invalid!");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
