package com.trakstar.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITest;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import com.trakstar.testdata.model.TestDocument;



public class TestBase implements ITest{

	private static ObjectMapper mapper = new ObjectMapper();

	private Queue<TestDocument> testDocQueue = new LinkedList<TestDocument>();
	
	private static final Logger Log = LogManager.getLogger(TestBase.class);
	private static final String TEST_DIRECTORY = "TestData";
	
	private WebDriver driver;
	private static String driverPath = "/Users/arunarajendran/Desktop/Drivers/";

	public WebDriver getDriver() {
		return driver;
	}

	private void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
	}

	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver");
		System.out.println("Successfully set chrome driver property");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		System.out.println("AppUrl: "+appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	@Parameters({ "browserType", "appURL" })
	@BeforeTest
	public void initializeTestBaseSetup(String browserType, String appURL) {
		try {
			setDriver(browserType, appURL);

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}


	@Override
	public String getTestName() {
		TestDocument testDoc = testDocQueue.poll();
		return testDoc == null ? "null" : testDoc.testCase;
	}


	private Collection<File> getTestDataFiles(String testDir) throws IOException {
		String path = new File(".").getCanonicalPath();
		File directory = new File(path + IOUtils.DIR_SEPARATOR + TEST_DIRECTORY + IOUtils.DIR_SEPARATOR + testDir);
		Log.info("Looking for test files at {}", directory);
		if(directory.exists() && directory.isDirectory()) {
			return FileUtils.listFiles(directory, null, true);
		}
		throw new SkipException("Invalid test data location. " + directory.getAbsolutePath());
		
	}
	
	@DataProvider
	@JsonDeserialize
	public Iterator<Object[]> testDataProvider(Method m) throws IOException {
		Collection<File> testDataFiles = getTestDataFiles(m.getName());
		Log.info("Found the following testFiles: {}", testDataFiles);
		ImmutableList.Builder<Object[]> dataBuilder = ImmutableList.builder();
		for(File testFile : testDataFiles) {
			try {
				TestDocument testDocument =  mapper.readValue(testFile, TestDocument.class);
				testDocQueue.add(testDocument);
				dataBuilder.add(new Object[] { testDocument });
				
			} catch (Exception e) {
				Log.error("", e);
			}
			
		}
		return dataBuilder.build().iterator();
	}

}