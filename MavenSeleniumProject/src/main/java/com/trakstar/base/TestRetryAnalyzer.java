package com.trakstar.base;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

	private static final int DEFAULT_MAX_RETRIES = 2;
	AtomicInteger count = new AtomicInteger(0);

	private int maxRetries;
	private int currentRetryCount;

	/**
	 * Create an instance of TestRetryAnalyzer
	 */

	public TestRetryAnalyzer() {
		currentRetryCount = 0;
		initMaxRetries();
	}

	private void initMaxRetries() {
		final String retriesOrNull = System.getProperty("maxRetries");
		if (StringUtils.isBlank(retriesOrNull)) {
			maxRetries = DEFAULT_MAX_RETRIES;
		} else {
			maxRetries = Integer.parseInt(retriesOrNull);
		}
	}

	public boolean isRetryAvailable() {
		return (count.intValue() / 2 < maxRetries);
	}

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		Boolean metricsEnabled = (Boolean) result.getAttribute("metricsEnabled");
		final boolean shouldRetry = currentRetryCount < maxRetries;
		if (shouldRetry && !BooleanUtils.isTrue(metricsEnabled)) {
			currentRetryCount++;
		}
		return shouldRetry;
	}

	public int getRetryCount() {
		return count.intValue() / 2;
	}

	public int getMaxRetryCount() {
		return maxRetries;
	}

}
