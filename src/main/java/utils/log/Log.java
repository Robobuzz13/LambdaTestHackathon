package utils.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;

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
    }

    /**
     * message print the test case custom message in the log (level=info)
     *
     * @param description test case
     */
    public static void info(String description) {
        logger.info(description);
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
    }

    /**
     * fail print test case status as Fail with custom message (level=error)
     *
     * @param message custom message in the test case
     */
    public static void fail(String message) {
        logger.error(message);
    }

    /**
     * exception prints the exception message as fail/skip in the log (level=fatal)
     *
     * @param e exception message
     */
    public static void exception(Exception e) {
        logger.error(e.getMessage());
    }

    public static void logAssertTrue(boolean condition, String message, String failMessage) {
        if(condition) {
            Assert.assertTrue(condition, message);
            pass(message);
        }else {
            Assert.assertTrue(condition, failMessage);
            fail(failMessage);
        }

    }

    public static void logAssertFalse(boolean condition, String message, String failMessage) {
        if(!condition) {
            Assert.assertFalse(condition, message);
            pass(message);
        }else {
            Assert.assertFalse(condition, failMessage);
            fail(failMessage);
        }

    }

    public static void logAssertEqual(boolean actual, boolean expected, String message, String failMessage) {
        if(Boolean.valueOf(actual).equals(Boolean.valueOf(expected))) {
            Assert.assertEquals(actual, expected, message);
            pass(message);
        }else {
            Assert.fail(message);
            fail(failMessage);
        }

    }

    public static void logAssertEqual(String actual, String expected, String message, String failMessage) {
        if(actual.equals(expected)) {
            Assert.assertEquals(actual, expected, message);
            pass(message);
        }else {
            Assert.fail(message);
            fail(failMessage);
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
    }

    /**
     * message prints the test case end
     * message print the test case custom message in the log
     */
    public static void testEnd() {
        logger.info("****             " + "-Test--Execution--Ended--" + "             *****");
    }
}
