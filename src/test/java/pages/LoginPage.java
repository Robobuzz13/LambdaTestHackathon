package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.action.WebDriverUtils;
import utils.log.Log;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverUtils utils;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.utils = new WebDriverUtils(driver, 10);
        PageFactory.initElements(driver, this);
    }

    // Elements on the page
    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    @FindBy(xpath = "//i[text()=' Logout']")
    private WebElement logOutButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    // Actions
    public void enterUsername(String username) {
        utils.waitForVisibility(usernameInput, "username field").clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        utils.waitForVisibility(passwordInput, "password field").clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        utils.waitForClickable(loginButton, "login button").click();
    }

    public void clickLogout() {
        utils.waitForClickable(logOutButton, "logout button").click();
    }

    public void loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void assertSuccessMessage(String successMessage) {
        Log.logAssertTrue(utils.waitForVisibility(flashMessage, "Flash message").getText().trim()
                .contains(successMessage), "Success message is displayed",
                "Success message is not displayed", driver);
    }

    public void assertFailureMessage(String failMessage) {
        Log.logAssertTrue(utils.waitForVisibility(flashMessage, "flash message").getText().trim()
                        .contains(failMessage), "Fail message is displayed",
                "Fail message is not displayed", driver);
    }

}
