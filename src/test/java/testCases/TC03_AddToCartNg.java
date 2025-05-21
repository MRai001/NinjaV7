
package testCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import testBase.BaseClass;
import pageObjects.HomePage;
import pageObjects.LaptopsAndNotebooksPage;
import pageObjects.ProductPage;
import utilities.RetryAnalyzer; // Ensure this class exists

public class TC03_AddToCartNg extends BaseClass {

	private static final Logger logger = LogManager.getLogger(TC03_AddToCartNg.class);

  
    @Test(groups = {"sanity", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    void testAddToCart() throws InterruptedException {
        logger.debug("Starting testAddToCart");

        try {
            HomePage hp = new HomePage(getDriver());
            logger.debug("Navigating to Laptops & Notebooks section");
            hp.link_Laptops.click();

            hp.link_ShowAll.click();

            LaptopsAndNotebooksPage ln = new LaptopsAndNotebooksPage(getDriver());

            logger.debug("Scrolling to select laptop");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", ln.link_SelectLaptop);

            Thread.sleep(1000);

            logger.debug("Clicking on selected laptop");
            ln.link_SelectLaptop.click();

            ProductPage lp = new ProductPage(getDriver());

            logger.debug("Scrolling to delivery date input");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", lp.txt_DeliveryDate);

            Thread.sleep(1000);

            LocalDate today = LocalDate.now();
            LocalDate deliveryDate = today.plusDays(5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDeliveryDate = deliveryDate.format(formatter);

            logger.debug("Setting delivery date: " + formattedDeliveryDate);

            lp.txt_DeliveryDate.clear();
            Thread.sleep(2000);
            lp.txt_DeliveryDate.sendKeys("05/30/2025"); // Hardcoded for demonstration

            Thread.sleep(2000);

            logger.debug("Clicking Add to Cart");
            lp.btn_AddCart.click();

            Thread.sleep(1700);

            String actualMessage = lp.txt_message.getText();
            logger.debug("Actual success message: " + actualMessage);

            try {
                logger.debug("Validating if item was added to cart successfully");
                Assert.assertTrue(actualMessage.contains("Success"), "Item was not added to cart successfully!");
                logger.info("Item added to cart successfully.");
            } catch (AssertionError ae) {
                logger.error("Assertion failed: Item not added to cart. Message: " + actualMessage, ae);
                throw ae; // Ensure test fails and retryAnalyzer is triggered
            }

        } catch (Exception e) {
            logger.error("Exception occurred during test execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("testAddToCart completed");
    }
}