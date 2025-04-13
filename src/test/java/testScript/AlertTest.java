package testScript;

import driver.DriverFactory;
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AlertPage;
import pages.LoginPage;
import utils.ConfigRead;
import utils.log.Log;

import java.sql.DriverManager;

public class AlertTest {

    private WebDriver driver;
    private AlertPage alertsPage;
    private driver.DriverManager driverManager;

    @BeforeMethod
    public void setUp() {
        EnvironmentType environment = EnvironmentType.LAMBDA_TEST;
        DriverType browser = DriverType.CHROME;

        driverManager = DriverFactory.getManager(environment, browser);
        driver = driverManager.getDriver();

        driver.get(ConfigRead.get("alertUrl"));
        alertsPage = new AlertPage(driver);
    }

    @Test()
    public void testJSAlert() {
        alertsPage.triggerJSAlertAndAccept();
        alertsPage.assertSuccessMessage(ConfigRead.get("successAlertMessage"));
    }

    @Test()
    public void testJSConfirmAccept() {
        alertsPage.triggerJSConfirmAndAccept();
        alertsPage.assertSuccessMessage(ConfigRead.get("successAlertConfirmMessage"));
    }

    @Test()
    public void testJSConfirmDismiss() {
        alertsPage.triggerJSConfirmAndDismiss();
        alertsPage.assertSuccessMessage(ConfigRead.get("successAlertDismissMessage"));
    }

    @Test()
    public void testJSPrompt() {
        alertsPage.triggerJSPromptAndSendText(ConfigRead.get("successAlertPromptInput"));
        alertsPage.assertSuccessMessage(ConfigRead.get("successAlertPromptMessage"));
    }

    @AfterMethod
    public void tearDown() {
        if (driverManager != null) {
            driverManager.quitDriver();
        }
    }
}
