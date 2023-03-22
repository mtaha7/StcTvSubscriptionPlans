package com.stc.tv.automation.constants;

public class GeneralConstants {

// *************************    General constants used allover the app   ********************************

	public static final String FAILED = "Failed";
	public static final String TRUE = "true";
	public static final String FILE_DELIMITER = "/";
	public static final String STRING_DELIMITER = "#";
	public static final String MISMATCH_ERR_MSG = " Mismatched the VALUE that was entered in screen";
	public static final String POM_EXCEPTION_ERR_MSG = "Test Failed due to an exception occurred in POM method";
// **********************************************************************************************************

//  **********************   Test Data config file and its properties key names ***************************

	//Test data configs file and its properties key names
	public static final String TEST_DATA_CONFIG_FILE_NAME = "configFiles//TestDataConfig.properties";

	// Test data strategy to get test data source type and implementing classes
	public static final String TEST_DATA_TYPE = "TestDataType";
	public static final String TEST_DATA_TYPE_CLASS_PATH = "TestDataStrategyClassPath_";
// **********************************************************************************************************

//  **********************   General config file and its properties key names ***************************
	public static final String GENERAL_CONFIG_FILE_NAME = "configFiles//GeneralConfigs.properties";

	public static final String SMOKE_TEST_FLAG = "isSmockTestScopeEnabled";

	// Extent report configs
	public static final String SCREENSHOT_PASSED_TESTS_PATH = "screenshotsOfPassedTestsPath";
	public static final String EXTENT_REPORT_FILE_PATH = "extentReportFilepath";
	public static final String EXTENT_REPORT_TITLE = "extentReportTitle";
	public static final String EXTENT_REPORT_NAME = "extentReportName";
	public static final String ADD_LOG_TO_EXTENT_REPORT = "addLogToExtentReport";

// **********************************************************************************************************
}
