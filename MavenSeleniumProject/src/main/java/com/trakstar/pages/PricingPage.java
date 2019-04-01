package com.trakstar.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PricingPage {
	protected WebDriver driver;

	protected By noOfEmployeesTextBox = By.id("num_employees_input");

	protected By performanceManagementCheckBox = By.id("check-performance-mgt");
	protected By employeeEngagementSurveysCheckBox = By.id("check-employee-engagement");
	protected By applicantTrackingCheckBox = By.id("check-trakstar-team");

	private By annualPrice = By.id("annual_charge_str");

	public PricingPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getPageTitle() {
		String title = driver.getTitle();
		return title;
	}

	public void enterEmployeesCount(int employeeCount) {
		WebElement employeeCountTxtBox = driver.findElement(noOfEmployeesTextBox);
		if (employeeCountTxtBox.isDisplayed()) {
			employeeCountTxtBox.sendKeys(String.valueOf(employeeCount));
		}
	}

	public void enablePerformanceManagementPackage() {
		WebElement performanceMgmtCheckBox = driver.findElement(performanceManagementCheckBox);
		if (!performanceMgmtCheckBox.isSelected()) {
			performanceMgmtCheckBox.click();
		}
	}

	public void disablePerformanceManagementPackage() {
		WebElement performanceMgmtCheckBox = driver.findElement(performanceManagementCheckBox);
		if (performanceMgmtCheckBox.isSelected()) {
			performanceMgmtCheckBox.click();
		}
	}

	public void enableEmployeeEngagementPackage() {
		WebElement employeeEngagementCheckBox = driver.findElement(employeeEngagementSurveysCheckBox);
		if (!employeeEngagementCheckBox.isSelected()) {
			employeeEngagementCheckBox.click();
		}
	}

	public void disableEmployeeEngagementPackage() {
		WebElement employeeEngagementCheckBox = driver.findElement(employeeEngagementSurveysCheckBox);
		if (employeeEngagementCheckBox.isSelected()) {
			employeeEngagementCheckBox.click();
		}
	}

	public void enableApplicantTrackingPackage() {
		WebElement applicantTrackingChkBox = driver.findElement(applicantTrackingCheckBox);
		if (!applicantTrackingChkBox.isSelected()) {
			applicantTrackingChkBox.click();
		}
	}

	public void disableApplicantTrackingPackage() {
		WebElement applicantTrackingChkBox = driver.findElement(applicantTrackingCheckBox);
		if (applicantTrackingChkBox.isSelected()) {
			applicantTrackingChkBox.click();
		}
	}

	public String getAnnualSubscriptionPrice() {
		WebElement annualSubscriptionPrice = driver.findElement(annualPrice);
		return annualSubscriptionPrice.getText();

	}

}
