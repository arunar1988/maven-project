package com.trakstar.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

public class TestListener implements ITestListener {

	private static final Logger log = LogManager.getLogger(TestListener.class);
	private static int passedCounter = 0;
	private static int failedCounter = 0;

	@Override
	public void onStart(ITestContext context) {
		log.info("*** Test Suite " + context.getName() + " started ***");
	}

	@Override
	public void onFinish(ITestContext context) {
		log.info(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		log.info("The test has started: TEST NAME: {}", result.getName());
		log.info(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		log.info("The test has ended: TEST NAME: {}\t TEST RESULT: PASSED", result.getName());
		++passedCounter;
		log.info("Running Results Counter: PASSED: {}\t FAILED: {}", passedCounter, failedCounter);
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		if (result.getMethod().getRetryAnalyzer() != null) {
			TestRetryAnalyzer retryAnalyzer = (TestRetryAnalyzer) result.getMethod().getRetryAnalyzer();
			if (retryAnalyzer.isRetryAvailable()) {
				log.info("Test attempt: {}\t Result: FAILED\tRetries remaining: {}", retryAnalyzer.getRetryCount() + 1,
						retryAnalyzer.getMaxRetryCount() - retryAnalyzer.getRetryCount());
				log.info("Test will be retried: TEST NAME: {}", result.getName());
			} else {
				++failedCounter;
				log.info("The test has ended: TEST NAME: {}\t FINAL TEST RESULT: FAILED", result.getName());
				log.info("Running Results Counter: PASSED: {}\t FAILED: {}", passedCounter, failedCounter);
				ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}

}
