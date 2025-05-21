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

import pageObjects.AccountPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC02_Login_Ng extends BaseClass {
    //WebDriver driver; // Launch browser using Web Driver Interface
    private static final Logger logger = LogManager.getLogger(TC02_Login_Ng.class);
    
    @Test(groups = {"sanity", "regression", "datadriven"}, dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    void testLogin(String email, String pwd) {
        logger.info("Starting login test with email: {}", email);
        logger.debug("Password length: {}", pwd.length());
        
        HomePage hp = null;
        LoginPage lp = null;
        AccountPage ap = null;
        boolean status = false;
        
        try {
            // Navigate to login page
            logger.debug("Creating HomePage object");
            hp = new HomePage(getDriver());
            
            logger.debug("Clicking on MyAccount menu");
            try {
                hp.clickMyAccount();
                logger.debug("Successfully clicked on MyAccount menu");
            } catch (Exception e) {
                logger.error("Failed to click on MyAccount menu: {}", e.getMessage(), e);
                throw e;
            }
            
            logger.debug("Navigating to login page");
            try {
                hp.goToLogin();
                logger.debug("Successfully navigated to login page");
            } catch (Exception e) {
                logger.error("Failed to navigate to login page: {}", e.getMessage(), e);
                throw e;
            }
            
            // Login process
            logger.debug("Creating LoginPage object");
            lp = new LoginPage(getDriver());
            
            logger.debug("Setting email address");
            try {
                lp.setEmail(email);
                logger.debug("Email address set successfully");
            } catch (Exception e) {
                logger.error("Failed to set email address: {}", e.getMessage(), e);
                throw e;
            }
            
            logger.debug("Setting password");
            try {
                lp.setPwd(pwd);
                logger.debug("Password set successfully");
            } catch (Exception e) {
                logger.error("Failed to set password: {}", e.getMessage(), e);
                throw e;
            }
            
            logger.debug("Clicking login button");
            try {
                lp.clickLogin();
                logger.debug("Login button clicked successfully");
            } catch (Exception e) {
                logger.error("Failed to click login button: {}", e.getMessage(), e);
                throw e;
            }
            
            // Verify login success
            logger.debug("Creating AccountPage object");
            ap = new AccountPage(getDriver());
            
            logger.debug("Verifying if MyAccount section is displayed");
            try {
                status = ap.getMyAccountConfirmation().isDisplayed();
                logger.info("MyAccount confirmation status: {}", status);
            } catch (Exception e) {
                logger.error("Failed to verify MyAccount confirmation: {}", e.getMessage(), e);
                throw e;
            }
            
            // Conditional logout process
            if (status) {
                logger.info("Login successful, proceeding to logout");
                
                logger.debug("Clicking on MyAccount dropdown");
                try {
                    ap.clickMyAccountDropDown();
                    logger.debug("MyAccount dropdown clicked successfully");
                } catch (Exception e) {
                    logger.error("Failed to click MyAccount dropdown: {}", e.getMessage(), e);
                    throw e;
                }
                
                logger.debug("Clicking logout option");
                try {
                    ap.clickLogout();
                    logger.info("Successfully logged out");
                } catch (Exception e) {
                    logger.error("Failed to click logout option: {}", e.getMessage(), e);
                    throw e;
                }
                
                // Final assertion for successful login/logout flow
                try {
                    Assert.assertTrue(status);
                    logger.info("Assertion passed: Login test successful");
                } catch (AssertionError e) {
                    logger.error("Assertion failed despite visible MyAccount section");
                    captureAndLogFailure("loginAssertionFailure", e);
                    throw e;
                }
            } else {
                logger.warn("Login failed - MyAccount section not displayed");
                
                try {
                    Assert.assertTrue(false, "Login failed - MyAccount section not visible");
                    // This line won't execute due to assertion failure
                } catch (AssertionError e) {
                    logger.error("Assertion failed as expected: Login unsuccessful");
                    captureAndLogFailure("loginFailure", e);
                    throw e;
                }
            }
            
        } catch (Exception e) {
            logger.error("Exception occurred during login test: {}", e.getMessage(), e);
            captureAndLogFailure("unexpectedLoginFailure", e);
            Assert.fail("Login test failed with exception: " + e.getMessage());
        }
        
        logger.info("Completed login test with email: {}", email);
    }
    
    /**
     * Helper method to capture screenshot and log failure details
     */
    private void captureAndLogFailure(String screenshotPrefix, Throwable t) {
        try {
            String screenshotPath = captureScreen(screenshotPrefix);
            logger.debug("Screenshot captured at: {}", screenshotPath);
        } catch (Exception e) {
            logger.error("Failed to capture failure screenshot: {}", e.getMessage(), e);
        }
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.debug("Executing @AfterMethod for test: {}", result.getName());
        
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                logger.warn("Test method failed: {} with data: {}", result.getName(), 
                            result.getParameters().length > 0 ? result.getParameters()[0] : "No data");
                
                try {
                    String screenshotPath = captureScreen(result.getName());
                    logger.info("Screenshot captured at: {}", screenshotPath);
                } catch (Exception e) {
                    logger.error("Failed to capture screenshot in @AfterMethod: {}", e.getMessage(), e);
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                logger.info("Test method passed: {} with data: {}", result.getName(),
                           result.getParameters().length > 0 ? result.getParameters()[0] : "No data");
            } else if (result.getStatus() == ITestResult.SKIP) {
                logger.info("Test method skipped: {}", result.getName());
            }
        } catch (Exception e) {
            logger.error("Exception in @AfterMethod tearDown: {}", e.getMessage(), e);
        }
    }
}