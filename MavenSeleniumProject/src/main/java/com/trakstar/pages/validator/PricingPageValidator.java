package com.trakstar.pages.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.trakstar.pages.PricingPage;


public class PricingPageValidator extends PricingPage {
	
	private static final Logger log = LogManager.getLogger(PricingPageValidator.class);

	public PricingPageValidator(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Verify if the pricing page is displayed.
	 * 
	 */
	public void verifyPricingPageisDisplayed() {
		log.info("validating if pricing page is displayed....");
		String expectedPageTitle = "Pricing - Trakstar";
		WebElement employeeCountTxtBox = driver.findElement(noOfEmployeesTextBox);
		Assert.assertTrue(getPageTitle().contains(expectedPageTitle) && employeeCountTxtBox.isDisplayed(),
				"Pricing page is not displayed");
	}

	/**
	 * Check if the PerformanceManagement check box is enabled.
	 * 
	 */
	public void verifyPerformanceManagementisSelected() {
		log.info("validating if performance management checkbox is selected....");
		WebElement performanceMgmtCheckBox = driver.findElement(performanceManagementCheckBox);
		Assert.assertTrue(performanceMgmtCheckBox.isSelected(), "Performance Management Check box is not selected");

	}

	/**
	 * Check if the EmployeeEngagementSurverys check box is enabled.
	 * 
	 */
	public void verifyEmployeeEngagementisSelected() {
		log.info("validating if employee emgagement suvery checkbox is selected....");
		WebElement employeeEngagementCheckbox = driver.findElement(employeeEngagementSurveysCheckBox);
		Assert.assertTrue(employeeEngagementCheckbox.isSelected(), "Employee engagement checkbox is not selected");
	}

	/**
	 * Check if applicant tracking option is enabled.
	 * 
	 */
	public void verifyApplicantTrackingisSelected() {
		log.info("validating if applicant tracking checkbox is selected....");
		WebElement applicantTrackingChckbox = driver.findElement(applicantTrackingCheckBox);
		Assert.assertTrue(applicantTrackingChckbox.isSelected(), "Applicant tracking checkbox is not selected");
	}

	/**
	 * Verify actual Annual Price with the expected value
	 * @param expectedAnnualPrice
	 */
	public void verifyAnnualSubscriptionQuote(String expectedAnnualPrice) {
		log.info("Validating the actual Annual Price Quote with the expected value....");
		String annualPrice = getAnnualSubscriptionPrice();
		Assert.assertEquals(annualPrice, expectedAnnualPrice, "Annual Price doesn't match the expected value");
	}

}
