package com.trakstar.testdata.model;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TestDocument {
  public String testCase;
  public int noOfEmployees;
  public boolean enablePerformanceManagement;
  public boolean enableEmployeeEngagementSurvey;
  public boolean enableApplicantTracking;
  public String expected_AnnualSubscriptionQuote;

  @Override
  public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
