package listeners;

import org.testng.*;
import utils.log.Log;

public class TestListener implements ITestListener, ISuiteListener {

    /**
     *Method to executed while starting test suite run
     */
    @Override
    public void onStart(ISuite suite) {
        Log.info("Starting Mobile Suite" + suite.getName());
    }

    /**
     *Method to executed while starting test case run
     */
    @Override
    public void onTestStart(ITestResult result) {
        Log.testStart(result);

    }

    /**
     *Method to executed after test run success
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        Log.pass(result);
    }

    /**
     *Method to executed after test run skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        Log.skip(result);
    }

    /**
     *Method to executed after test run fail
     */
    @Override
    public void onTestFailure(ITestResult result) {
        Log.fail(result);
    }

    /**
     *Method to executed after test suit completes
     */
    @Override
    public void onFinish(ITestContext context) { // Isuite
        Log.testEnd();
    }

}
