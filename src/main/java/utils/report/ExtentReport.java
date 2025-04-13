package utils.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import utils.log.Log;

import java.io.File;
import java.sql.DriverManager;
import java.util.Objects;

public class ExtentReport {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Method to set initial setup for extent report
     */
    public static void initReports() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/ExtentReports/AutomationReport.html");
            extent.attachReporter(spark);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("HeroKuApp -TestRun");
            spark.config().setReportName("HeroKuApp -TestRun");
            spark.config().setEncoding("utf-8");
        }
    }

    /**
     * Method to publish extent report
     */
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
        extentTest.remove();
    }

    /**
     * Method to create test report for extent
     */
    public static void createTest(String testCaseName) {
        extentTest.set(extent.createTest(testCaseName));
    }

    /**
     * To log the given message to the reporter at INFO level
     */
    public static void info(String message) {
        extentTest.get().log(Status.INFO, message);
    }

    /**
     * To log the given message to the reporter at PASS level
     */
    public static void pass(Markup m) {
        extentTest.get().log(Status.PASS, m);
    }

    /**
     * To log the given message to the reporter at FAIL level
     */
    public static void fail(Markup m) {
        extentTest.get().log(Status.FAIL, m);
    }

    /**
     * To log the given message to the reporter at FAIL level
     */
    public static void fail(String m) {
        extentTest.get().log(Status.FAIL, m);
    }

    /**
     * To log the given message to the reporter at FAIL level
     */
    public static void fail(Markup m, WebDriver driver) {
        ExtentReport report = new ExtentReport();
        report.takeScreenShot(driver);
        extentTest.get().log(Status.FAIL, m);
    }

    /**
     * To log the given message to the reporter at SKIP level
     */
    public static void skip(Markup m) {
        extentTest.get().log(Status.SKIP, m);
    }

    /**
     * To log the given message to the reporter at SKIP level
     */
    public static void skip(String m) {
        extentTest.get().log(Status.SKIP, m);
    }

    /**
     * To print the stack trace of the given error/exception
     */
    public static void logStackTrace(Throwable t) {
        if (t instanceof SkipException) {
            extentTest.get().log(Status.SKIP, "<div class=\"stacktrace\">" + t.getLocalizedMessage() + "</div>");
        } else {
            extentTest.get().log(Status.WARNING, "<div class=\"stacktrace\">" + t.getLocalizedMessage() + "</div>");
        }
    }

    /**
     * Method to start capturing extent report
     */
    public static void extentTestStart(ITestResult result) {
        initReports();
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " :: " +
                result.getMethod().getMethodName());
        extentTest.set(test);
    }

    /**
     * Method to capture screenshot with result method name
     */
    private void takeScreenShot(WebDriver driver) {
        try {
            if(null != driver){
                File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String filepath = System.getProperty("user.dir") + "/TestReport/FailuresScreens/" + driver.getClass().getName()
                        + "/" + driver.getClass().getCanonicalName() + ".png";
                FileUtils.copyFile(source, new File(filepath));
                extentTest.get().addScreenCaptureFromPath(filepath);
            }
        } catch (Exception e) {
            Log.exception(e);
        }
    }
}
