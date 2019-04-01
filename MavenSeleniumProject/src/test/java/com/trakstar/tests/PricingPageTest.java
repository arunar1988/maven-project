package com.trakstar.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.trakstar.base.TestBase;
import com.trakstar.pages.PricingPage;
import com.trakstar.pages.validator.PricingPageValidator;
import com.trakstar.testdata.model.TestDocument;

public class PricingPageTest extends TestBase{
	TestDataParser testDataParser;

	private WebDriver driver;
	private static final Logger log = LogManager.getLogger(PricingPageTest.class);

	@BeforeTest
	public void setUp() {
		driver = getDriver();
	}

	@Test(dataProvider = "testDataProvider")
	public void testPricingPageForPerformanceMgmt(final TestDocument testDocument) throws InterruptedException {
		log.info("Test Price Quote For Performance Management Component...");
		log.info("Executing the test case: " + testDocument.testCase);
		
		driver.navigate().refresh();
		PricingPage basePage = new PricingPage(driver);
		PricingPageValidator validator = new PricingPageValidator(driver);
		
		Thread.sleep(5000);
		validator.verifyPricingPageisDisplayed();
		basePage.enterEmployeesCount(testDocument.noOfEmployees);
		
		Thread.sleep(2000);
		basePage.enablePerformanceManagementPackage();
		validator.verifyPerformanceManagementisSelected();
		validator.verifyAnnualSubscriptionQuote(testDocument.expected_AnnualSubscriptionQuote);		
	}

	@Test(dataProvider = "testDataProvider")
	public void testEmployeeManagementSurveyPrice(final TestDocument testDocument) throws InterruptedException {
		log.info("Test Price Quote For Employee Management Survey Component...");
		log.info("Executing the test case: " + testDocument.testCase);
		
		driver.navigate().refresh();
		PricingPage basePage = new PricingPage(driver);
		PricingPageValidator validator = new PricingPageValidator(driver);
		
		Thread.sleep(3000);
		validator.verifyPricingPageisDisplayed();
		basePage.enterEmployeesCount(testDocument.noOfEmployees);
		
		Thread.sleep(3000);
		basePage.disablePerformanceManagementPackage();
		basePage.disableApplicantTrackingPackage();
		basePage.enableEmployeeEngagementPackage();
		validator.verifyEmployeeEngagementisSelected();
		validator.verifyAnnualSubscriptionQuote(testDocument.expected_AnnualSubscriptionQuote);		
	}

	@Test(dataProvider = "testDataProvider")
	public void testApplicantTrackingPrice(final TestDocument testDocument) throws InterruptedException {
		log.info("Test Price Quote For Applicant Tracking Component...");
		log.info("Executing the test case: " + testDocument.testCase);
		
		driver.navigate().refresh();
		PricingPage basePage = new PricingPage(driver);
		PricingPageValidator validator = new PricingPageValidator(driver);
		
		Thread.sleep(3000);
		validator.verifyPricingPageisDisplayed();
		basePage.enterEmployeesCount(testDocument.noOfEmployees);
		
		Thread.sleep(3000);
		basePage.disablePerformanceManagementPackage();
		basePage.disableEmployeeEngagementPackage();
		basePage.enableApplicantTrackingPackage();
		validator.verifyApplicantTrackingisSelected();
		validator.verifyAnnualSubscriptionQuote(testDocument.expected_AnnualSubscriptionQuote);		
	}
}
