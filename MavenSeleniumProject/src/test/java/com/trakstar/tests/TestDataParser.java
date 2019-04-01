package com.trakstar.tests;

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
import org.testng.ITest;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.ImmutableList;
import com.trakstar.testdata.model.TestDocument;

public class TestDataParser implements ITest {
	
	private static ObjectMapper mapper = new ObjectMapper();

	private Queue<TestDocument> testDocQueue = new LinkedList<TestDocument>();
	
	private static final Logger Log = LogManager.getLogger(TestDataParser.class);
	
	private static final String BASE_DIRECTORY = "TestData";
	

	@Override
	public String getTestName() {
		TestDocument testDoc = testDocQueue.poll();
		return testDoc == null ? "null" : testDoc.testCase;
	}


	private Collection<File> getTestDataFiles(String testDirectory) throws IOException {
		String path = new File(".").getCanonicalPath();
		File directory = new File(path + IOUtils.DIR_SEPARATOR + BASE_DIRECTORY + IOUtils.DIR_SEPARATOR + testDirectory);
		Log.info("Looking for test files at {}", directory);
		if(directory.exists() && directory.isDirectory()) {
			return FileUtils.listFiles(directory, null, true);
		}
		throw new SkipException("Invalid test data location. " + directory.getAbsolutePath());
		
	}
	
	@DataProvider
	@JsonDeserialize
	public Iterator<Object[]> testDataProvider(Method m) throws IOException {
		//String testDir = "testEmployeeManagementSurveyPrice";
		Collection<File> testDataFiles = getTestDataFiles(m.getName());
		Log.info("Test Data Directory: "+ m.getName());
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

	@DataProvider
	@JsonDeserialize
	public Iterator<Object[]> testDataProvider1() throws IOException {
		String testDir = "testEmployeeManagementSurveyPrice";
		Collection<File> testDataFiles = getTestDataFiles(testDir);
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
