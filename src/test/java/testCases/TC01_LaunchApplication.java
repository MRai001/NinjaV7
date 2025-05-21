package testCases;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import testBase.BaseClass;
import utilities.RetryAnalyzer;

public class TC01_LaunchApplication extends BaseClass {
    
    private static final Logger logger = LogManager.getLogger(TC01_LaunchApplication.class);
    
    @Test(groups = {"sanity", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testLaunchApplication() {
        logger.info("Starting testLaunchApplication test execution");
        HomePage hp = null;
        String title = null;
        
        try {
            logger.debug("Creating HomePage object");
            hp = new HomePage(getDriver());
            logger.debug("HomePage object created successfully");
            
            logger.debug("Getting page title from browser");
            title = getDriver().getTitle();
            logger.info("Current page title: {}", title);
            
            logger.debug("Performing assertion on page title");
            try {
                Assert.assertEquals(title, "Your store of fun", "Page title verification failed");
                logger.info("Page title assertion passed successfully");
            } catch (AssertionError e) {
                logger.error("Page title assertion failed. Expected: 'Your store of fun', Actual: '{}'", title);
                // Capture screenshot on assertion failure
                String screenshotPath = captureScreen("titleAssertionFailure");
                logger.debug("Screenshot captured at: {}", screenshotPath);
                throw e; // Re-throw the assertion error after logging
            }
            
        } catch (Exception e) {
            logger.error("Exception occurred during test execution: {}", e.getMessage(), e);
            // Capture screenshot on exception
            try {
                String screenshotPath = captureScreen("testLaunchApplicationFailure");
                logger.debug("Screenshot captured at: {}", screenshotPath);
            } catch (Exception ex) {
                logger.error("Failed to capture screenshot: {}", ex.getMessage(), ex);
            }
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
        
        logger.info("Completed testLaunchApplication test execution");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.debug("Executing @AfterMethod");
        
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.warn("Test method failed: {}", result.getName());
                try {
                    String screenshotPath = captureScreen(result.getName());
                    logger.info("Screenshot captured at: {}", screenshotPath);
                } catch (Exception e) {
                    logger.error("Failed to capture screenshot in @AfterMethod: {}", e.getMessage(), e);
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                logger.info("Test method passed: {}", result.getName());
            } else if (result.getStatus() == ITestResult.SKIP) {
                logger.info("Test method skipped: {}", result.getName());
            }
        } catch (Exception e) {
            logger.error("Exception in @AfterMethod tearDown: {}", e.getMessage(), e);
        }
    }
}


