package testScript;

import driver.DriverFactory;
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
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
    @Parameters({"environmentValue", "browserValue"})
    public void setUp(String environmentValue, String browserValue) {
        EnvironmentType envType = (environmentValue == null || environmentValue.isEmpty()) ? EnvironmentType.LAMBDA_TEST : EnvironmentType.valueOf(environmentValue.toUpperCase());
        DriverType browserType = (browserValue == null || browserValue.isEmpty()) ? DriverType.CHROME : DriverType.valueOf(browserValue.toUpperCase());

        driverManager = DriverFactory.getManager(envType, browserType);
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
