package utils.log;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import utils.report.ExtentReport;

import java.util.Arrays;

public class Log {

    private static final Logger logger = LogManager.getLogger(Thread.currentThread().getName());

    /**
     * message print the test case custom message in the log (level=info) depends on
     * message print the test case custom message in the log
     *
     * @param description test case
     */
    public static void message(String description) {
        logger.info(description);
        ExtentReport.info(description);
    }

    /**
     * message print the test case custom message in the log (level=info)
     *
     * @param description test case
     */
    public static void info(String description) {
        logger.info(description);
        ExtentReport.info(description);
    }

    /**
     * message print the test case custom message in the log (level=debug) depends on
     * message print the test case custom message in the log
     *
     * @param description test case
     */
    public static void debug(String description) {
        logger.debug(description);
    }

    /**
     * pass print test case status as Pass with custom message (level=info)
     *
     * @param message custom message in the test case
     */
    public static void pass(String message) {
        logger.info(message);
        Markup m = MarkupHelper.createLabel("<b><i>"+message+"</i></b>", ExtentColor.GREEN);
        ExtentReport.pass(m);
    }

    public static void pass(ITestResult result) {
        String logText = "Test Method "+ result.getMethod().getMethodName() + " Passed";
        Markup m = MarkupHelper.createLabel("<b><i>"+logText+"</i></b>", ExtentColor.GREEN);
        ExtentReport.pass(m);
        logger.info(logText);
        logger.info("****             " + "-Assert Passed Test--case--Execution--Ended--" + "             *****");
    }

    public static void skip(ITestResult result) {

        String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
        String skipExceptionMessage = "Exception Occured, click to see details:";
        Markup m = MarkupHelper.createLabel("<details><summary><b><font color=orange>\" + \n"
                + "				\"Exception Occured, click to see details:\" + \"</font></b></summary>\" + \n"
                + "				exceptionMessage.replaceAll(\",\", \"<br>\") + \"</details> \\n", ExtentColor.YELLOW);
        ExtentReport.skip(m);
        logger.info("****             " + "-Assert Skipped Test--case--Execution--Ended--" + "             *****");
    }

    /**
     * fail print test case status as Fail with custom message (level=error)
     *
     * @param message custom message in the test case
     */
    public static void fail(String message, WebDriver driver) {
        Markup m = MarkupHelper.createLabel("<b><i>"+message+"</i></b>", ExtentColor.RED);
        ExtentReport.fail(m, driver);
        logger.error(message);
    }

    public static void fail(ITestResult result) {
        String exceptionMessage = result.getThrowable().getMessage();
        String skipExceptionMessage = "Exception Occured, click to see details:";
        String exceptionTrace = Arrays.toString(result.getThrowable().getStackTrace());
        Markup m = MarkupHelper.createLabel("<details><summary><b><font color=orange>" + "Exception Occured, click to see details:" + "</font></b></summary>" + exceptionMessage.replaceAll(",", "<br>") + "<br>" + exceptionTrace.replaceAll(",", "<br>") + "</details> \n", ExtentColor.RED);
        ExtentReport.fail(m);
        logger.error(skipExceptionMessage+" "+exceptionMessage);
        logger.info("****             " + "-Assert Failed Test--case--Execution--Ended--" + "             *****");
    }

    /**
     * exception prints the exception message as fail/skip in the log (level=fatal)
     *
     * @param e exception message
     */
    public static void exception(Exception e) {
        logger.error(e.getMessage());
        ExtentReport.skip(e.getMessage());
    }

    public static void logAssertTrue(boolean condition, String message, String failMessage, WebDriver driver) {
        if(condition) {
            Assert.assertTrue(condition, message);
            pass(message);
        }else {
            Assert.assertTrue(condition, failMessage);
            fail(failMessage, driver);
        }

    }

    public static void logAssertFalse(boolean condition, String message, String failMessage, WebDriver driver) {
        if(!condition) {
            Assert.assertFalse(condition, message);
            pass(message);
        }else {
            Assert.assertFalse(condition, failMessage);
            fail(failMessage, driver);
        }

    }

    public static void logAssertEqual(boolean actual, boolean expected, String message, String failMessage, WebDriver driver) {
        if(Boolean.valueOf(actual).equals(Boolean.valueOf(expected))) {
            Assert.assertEquals(actual, expected, message);
            pass(message);
        }else {
            Assert.fail(message);
            fail(failMessage, driver);
        }

    }

    public static void logAssertEqual(String actual, String expected, String message, String failMessage, WebDriver driver) {
        if(actual.equals(expected)) {
            Assert.assertEquals(actual, expected, message);
            pass(message);
        }else {
            Assert.fail(message);
            fail(failMessage, driver);
        }

    }

    /**
     * message print the test case started
     * message print the test case custom message in the log
     */
    public static void testStart(ITestResult result) {
        logger.info("****             " + "-Test--Case--Started--" + "             *****");
        logger.info("Test Method Name :"+result.getMethod().getMethodName());
        logger.info("Test Description :"+result.getMethod().getDescription());
        ExtentReport.extentTestStart(result);
    }

    /**
     * message prints the test case end
     * message print the test case custom message in the log
     */
    public static void testEnd() {
        logger.info("****             " + "-Test--Execution--Ended--" + "             *****");
        ExtentReport.flushReports();
    }
}
