package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.action.WebDriverUtils;
import utils.log.Log;

public class AlertPage {

    private final WebDriver driver;
    private final WebDriverUtils utils;

    public AlertPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new WebDriverUtils(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Page Elements
    @FindBy(xpath = "//button[text()='Click for JS Alert']")
    private WebElement jsAlertButton;

    @FindBy(xpath = "//button[text()='Click for JS Confirm']")
    private WebElement jsConfirmButton;

    @FindBy(xpath = "//button[text()='Click for JS Prompt']")
    private WebElement jsPromptButton;

    @FindBy(id = "result")
    private WebElement resultText;

    // Actions
    public void triggerJSAlertAndAccept() {
        utils.waitForClickable(jsAlertButton).click();
        utils.acceptAlert();
    }

    public void triggerJSConfirmAndAccept() {
        utils.waitForClickable(jsConfirmButton).click();
        utils.acceptAlert();
    }

    public void triggerJSConfirmAndDismiss() {
        utils.waitForClickable(jsConfirmButton).click();
        utils.dismissAlert();
    }

    public void triggerJSPromptAndSendText(String input) {
        utils.waitForClickable(jsPromptButton).click();
        utils.sendTextToAlert(input);
        utils.acceptAlert();
    }

    public String getResultText() {
        return utils.waitForVisibility(resultText).getText();
    }

    public void assertSuccessMessage(String resultMessage) {
        Log.logAssertTrue(utils.waitForVisibility(resultText).getText().trim()
                        .contains(resultMessage), "Result message is displayed",
                "Result message is not displayed");
    }
}
