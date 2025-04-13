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
    public WebElement waitForVisibility(WebElement element) {
        Log.info("Waiting for visibility of element: " + element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForClickable(WebElement element) {
        Log.info("Waiting for element to be clickable: " + element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean waitForInvisibility(WebElement element) {
        Log.info("Waiting for invisibility of element: " + element);
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Keyboard actions
    public void pressEnter(WebElement element) {
        Log.info("Pressing ENTER on element: " + element);
        waitForVisibility(element).sendKeys(Keys.ENTER);
    }

    public void typeText(WebElement element, String text) {
        Log.info("Typing text '" + text + "' into element: " + element);
        WebElement visibleElement = waitForVisibility(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    // Mouse actions
    public void hoverOverElement(WebElement element) {
        Log.info("Hovering over element: " + element);
        actions.moveToElement(waitForVisibility(element)).perform();
    }

    public void rightClick(WebElement element) {
        Log.info("Right-clicking on element: " + element);
        actions.contextClick(waitForVisibility(element)).perform();
    }

    public void doubleClick(WebElement element) {
        Log.info("Double-clicking on element: " + element);
        actions.doubleClick(waitForVisibility(element)).perform();
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
    public void switchToFrame(WebElement frame) {
        Log.info("Switching to frame: " + frame);
        driver.switchTo().frame(waitForVisibility(frame));
    }

    public void switchToDefaultContent() {
        Log.info("Switching to default content");
        driver.switchTo().defaultContent();
    }

    // Drag and drop
    public void dragAndDrop(WebElement source, WebElement target) {
        Log.info("Dragging element from " + source + " to " + target);
        actions.dragAndDrop(waitForVisibility(source), waitForVisibility(target)).perform();
    }

    public void dragAndDropByOffset(WebElement element, int xOffset, int yOffset) {
        Log.info("Dragging element " + element + " by offset X: " + xOffset + ", Y: " + yOffset);
        actions.dragAndDropBy(waitForVisibility(element), xOffset, yOffset).perform();
    }
}
