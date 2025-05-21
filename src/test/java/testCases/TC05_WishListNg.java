package testCases;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LaptopsAndNotebooksPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer; // Make sure this exists

public class TC05_WishListNg extends BaseClass {

	private static final Logger logger = LogManager.getLogger(TC05_WishListNg.class);

   

    @Test(groups = {"regression"}, retryAnalyzer = RetryAnalyzer.class)
    void testAddToWishList() throws InterruptedException {
        logger.debug("Starting test: Add product to Wishlist");

        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            HomePage hp = new HomePage(getDriver());

            logger.debug("Clicking My Account");
            hp.clickMyAccount();

            logger.debug("Navigating to Laptops & Notebooks section");
            hp.link_Laptops.click();
            hp.link_ShowAll.click();

            LaptopsAndNotebooksPage ln = new LaptopsAndNotebooksPage(getDriver());

            logger.debug("Scrolling to select laptop");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", ln.link_SelectLaptop);
            Thread.sleep(1000);

            logger.debug("Clicking on selected laptop");
            ln.link_SelectLaptop.click();

            ProductPage pp = new ProductPage(getDriver());

            logger.debug("Scrolling to Add to Wishlist button");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", pp.addToWishlistButton);
            Thread.sleep(2000);

            logger.debug("Clicking Add to Wishlist");
            pp.clickAddToWishlist();

            String actualMessage = pp.successAlert.getText();
            logger.debug("Success alert message: " + actualMessage);

            try {
                logger.debug("Asserting wishlist success message contains 'Success'");
                Assert.assertTrue(actualMessage.contains("Success"), "Wishlist addition failed!");
                logger.info("Product successfully added to wishlist.");
            } catch (AssertionError ae) {
                logger.error("Assertion failed: Wishlist addition unsuccessful.", ae);
                throw ae; // To trigger RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during test execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("Test completed: Add product to Wishlist");
    }
}

