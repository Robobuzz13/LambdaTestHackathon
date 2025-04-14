package utils.action;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.log.Log;

import java.time.Duration;

public class WebDriverUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public WebDriverUtils(WebDriver driver, long timeoutInSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        this.actions = new Actions(driver);
    }

    // Wait utilities
    public WebElement waitForVisibility(WebElement element, String elementName) {
        Log.info("Waiting for visibility of element: " + elementName);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element, String elementName) {
        Log.info("Waiting for element to be clickable: " + elementName);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForInvisibility(WebElement element, String elementName) {
        Log.info("Waiting for invisibility of element: " + elementName);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Keyboard actions
    public void pressEnter(WebElement element, String elementName) {
        Log.info("Pressing ENTER on element: " + elementName);
        waitForVisibility(element, elementName).sendKeys(Keys.ENTER);
    }

    public void typeText(WebElement element, String text, String elementName) {
        Log.info("Typing text '" + text + "' into element: " + elementName);
        WebElement visibleElement = waitForVisibility(element, elementName);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    // Mouse actions
    public void hoverOverElement(WebElement element, String elementName) {
        Log.info("Hovering over element: " + elementName);
        actions.moveToElement(waitForVisibility(element, elementName)).perform();
    }

    public void rightClick(WebElement element, String elementName) {
        Log.info("Right-clicking on element: " + elementName);
        actions.contextClick(waitForVisibility(element, elementName)).perform();
    }

    public void doubleClick(WebElement element, String elementName) {
        Log.info("Double-clicking on element: " + elementName);
        actions.doubleClick(waitForVisibility(element, elementName)).perform();
    }

    // Alert handling
    public void acceptAlert() {
        Log.info("Accepting alert");
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        Log.info("Dismissing alert");
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    public String getAlertText() {
        Log.info("Getting alert text");
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }

    public void sendTextToAlert(String text) {
        Log.info("Sending text to alert: " + text);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }

    // Frame actions
    public void switchToFrame(WebElement frame, String elementName) {
        Log.info("Switching to frame: " + frame);
        driver.switchTo().frame(waitForVisibility(frame, elementName));
    }

    public void switchToDefaultContent() {
        Log.info("Switching to default content");
        driver.switchTo().defaultContent();
    }
}
