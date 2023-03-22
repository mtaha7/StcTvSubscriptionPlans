package com.stc.tv.automation.tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import com.stc.tv.automation.pages.HomePage;
import com.stc.tv.automation.strategy.testData.TestDataStrategy;
import com.stc.tv.automation.utils.Log;
import com.stc.tv.automation.utils.PropertiesFilesHandler;
import com.stc.tv.automation.constants.GeneralConstants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class BaseTest {

    //Selenium and Angular webDrivers
    public WebDriver driver;
    JavascriptExecutor jsDriver;

    //Extent report objects
    public ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public ExtentTest test;


    //Initialize instances of properties files to be used in all tests
    PropertiesFilesHandler propHandler = new PropertiesFilesHandler();
    Properties generalConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.GENERAL_CONFIG_FILE_NAME);
    Properties testDataConfigsProps = propHandler.loadPropertiesFile(GeneralConstants.TEST_DATA_CONFIG_FILE_NAME);
    HomePage homepage;

    String dateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

    @BeforeTest(description = "Setting up extent report", alwaysRun = true)
    @Parameters("browserType")
    public void setExtent(String browserType) {
        try {
            Log.info("Setting up extent report before test on browser: " + browserType);
            // get report file path
            String extentReportFilePath = generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_FILE_PATH);
            // specify location of the report
            htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + extentReportFilePath + GeneralConstants.STRING_DELIMITER + dateTime + ".html");

            htmlReporter.config().setDocumentTitle(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_TITLE)); // Tile of report
            htmlReporter.config().setReportName(generalConfigsProps.getProperty(GeneralConstants.EXTENT_REPORT_NAME)); // Name of the report
            htmlReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Passing General information
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Browser", browserType);
        } catch (Exception e) {
            Log.error("Error occurred while setting up extent reports on " + browserType, e);
        }
    }

    @Parameters({"url", "browserType"})
    @BeforeClass(description = "Setting up selenium webDriver before each class run", alwaysRun = true)
    public void loadConfiguration(String url, String browserType) {
        try {
            Log.info("Initialize Selenium webDriver before tests' Class");

            // initialize selenium driver that is set as a config in testng.xml
            switch (browserType) {
                case ("Chrome"):
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(setChromeOption());
                    break;
                case ("Firefox"):
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver(setFireFoxOption());
                    break;
                case ("IE"):
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    break;
                case ("Edge"):
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case ("Opera"):
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;
            }

            // initialize angular webDriver
            jsDriver = (JavascriptExecutor) driver;

            driver.manage().window().maximize();
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            Log.info("Selenium webDriver was initialized successfully");
        } catch (Exception e) {
            Log.error("Error occurred while initializing selenium web driver", e);
        }
    }

    private ChromeOptions setChromeOption() {
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> ChromePrefs = new HashMap<>();
        ChromePrefs.put("profile.default.content_settings.popups", 0);
        options.setExperimentalOption("prefs", ChromePrefs);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setAcceptInsecureCerts(true);
        return options;
    }

    private FirefoxOptions setFireFoxOption() {
        FirefoxOptions option = new FirefoxOptions();
        option.addPreference("browser.download.folderlist", 2);
        option.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        option.addPreference("browser.download.manager.showWhenStarting", false);
        return option;
    }

    @BeforeClass()
    public void navigateToHomeScreen() {
        Log.info("Validate App is Up and We're in Home Screen");
        homepage = new HomePage(driver);
        homepage.waitForHomePageToLoad();
    }


    @AfterMethod(description = "Logging test status to log file and extent report", alwaysRun = true)
    public void logTestStatusForReport(ITestResult result) {
        try {
            String screenshotPath;
            if (result.getStatus() == ITestResult.FAILURE) {
                Log.info("logging Testcase FAILED " + result.getName() + " in Extent Report");
                test.log(Status.FAIL, "Test Case Name FAILED is " + result.getName()); // to add name in extent report
                test.log(Status.FAIL, "EXCEPTION Thrown is " + result.getThrowable()); // to add error/exception in extent report

            } else if (result.getStatus() == ITestResult.SKIP) {
                Log.info("logging Testcase SKIPPED " + result.getName() + " in Extent Report");
                test.log(Status.SKIP, "Test Case SKIPPED is " + result.getName());
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                Log.info("logging Testcase SUCCESS " + result.getName() + " in Extent Report");
                test.log(Status.PASS, "Test Case PASSED is " + result.getName());

                // adding screenshot.
                screenshotPath = getScreenshot(driver, result.getName());
                test.addScreenCaptureFromPath(screenshotPath);
            }
            // log that test case has ended
            Log.endTestCase(result.getName());
        } catch (Exception e) {
            Log.warn("Error occurred while logging testcase " + result.getName() + " result to extent report", e);
            e.printStackTrace();
        }
    }

    private String getScreenshot(WebDriver driver, String screenshotName) throws IOException {

        Log.info("Taking Screenshot for the Passed Testcase");

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        //get the path of passed tests screenshots
        String screenShotsPath = generalConfigsProps.getProperty(GeneralConstants.SCREENSHOT_PASSED_TESTS_PATH);

        // after execution, you could see a folder "PassedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + screenShotsPath + date + GeneralConstants.FILE_DELIMITER + screenshotName + dateTime + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }

    @AfterClass(description = "Quitting selenium driver after each class run", alwaysRun = true)
    public void closeDriver() {
        Log.info("Closing selenium webDriver after Class");
        if (driver != null)
            driver.quit();
    }

    @AfterTest(description = "Closing extent report after running Test", alwaysRun = true)
    public void endReport() {
        Log.info("Closing Extent report after Test");
        if (extent != null)
            extent.flush();
    }

    /// Check test data type and retrieve it from its source accordingly
    protected ArrayList<ArrayList<Object>> provideTestData(String methodName) {
        Log.info("Retrieving Test data of testcase " + methodName);
        String connectionProperties = testDataConfigsProps.getProperty(methodName);
        ArrayList<ArrayList<Object>> result = null;
        TestDataStrategy testData;
        String testDataType;
        String testDataTypeClassPath;
        try {
            //get test data type to connect to the proper test data source accordingly
            testDataType = testDataConfigsProps.getProperty(GeneralConstants.TEST_DATA_TYPE);

            //get class path of the class that implements methods of proper class path
            testDataTypeClassPath = testDataConfigsProps.getProperty(GeneralConstants.TEST_DATA_TYPE_CLASS_PATH + testDataType);
            //create instance from the proper class of specified data source
            testData = (TestDataStrategy) Class.forName(testDataTypeClassPath).newInstance();

            //load test data from the proper source
            result = testData.loadTestData(connectionProperties);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            Log.error("Error occurred while retrieving test data for Test: " + methodName, e);
        }
        return result;
    }
}
