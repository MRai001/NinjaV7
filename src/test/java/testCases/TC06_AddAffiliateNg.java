package testCases;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AffiliatePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer; // Ensure this exists in your project

public class TC06_AddAffiliateNg extends BaseClass {

	private static final Logger logger = LogManager.getLogger(TC06_AddAffiliateNg.class);

    @Test(groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    void testAddAffiliate() throws InterruptedException {
        // Configure Log4j (if not done globally in BaseClass)
        

        logger.debug("Starting test: testAddAffiliate");

        HomePage hp = new HomePage(getDriver());
        logger.debug("HomePage object created");

        Thread.sleep(500);
        logger.debug("Waited for 500ms");

        hp.clickMyAccount();
        logger.debug("Clicked on My Account");

        hp.goToLogin();
        logger.debug("Navigated to Login page");

        LoginPage lp = new LoginPage(getDriver());
        logger.debug("LoginPage object created");

        lp.setEmail("sid@cloudberry.services");
        logger.debug("Set email address");

        lp.setPwd("Test123");
        logger.debug("Set password");

        lp.clickLogin();
        logger.debug("Clicked Login button");

        Thread.sleep(1000);
        logger.debug("Waited for 1000ms after login");

        // Scroll into view
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", hp.link_Affiliate);
        logger.debug("Scrolled to Affiliate link");

        Thread.sleep(1000);
        logger.debug("Waited for 1000ms after scrolling");

        hp.goToAffiliate();
        logger.debug("Navigated to Affiliate page");

        AffiliatePage ap = new AffiliatePage(getDriver());
        logger.debug("AffiliatePage object created");

        // Update account information
        ap.updateAccountInformation("CloudBerry", "cloudberry.services", "123456", "Shadab Siddiqui");
        logger.debug("Updated account information");

        // Verify success
        boolean status = false;
        try {
            status = ap.isSuccessMessageDisplayed();
            logger.debug("Checked for success message: " + status);
            Assert.assertTrue(status, "Account information update failed!");
            logger.info("Account information updated successfully and assertion passed.");
        } catch (AssertionError e) {
            logger.error("Assertion failed: Account information update failed!", e);
            throw e; // Rethrow to let TestNG handle the failure and trigger RetryAnalyzer
        } catch (Exception e) {
            logger.error("Exception occurred during assertion or verification.", e);
            throw e;
        }

        logger.debug("Finished test: testAddAffiliate");
    }
}