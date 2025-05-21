package testCases;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountPage;
import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LaptopsAndNotebooksPage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import testBase.BaseClass;
import utilities.RetryAnalyzer; // Ensure this exists

public class TC04_CompletePurchaseNg extends BaseClass {

	private static final Logger logger = LogManager.getLogger(TC04_CompletePurchaseNg.class);

  

    @Test(groups = {"sanity", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    void addToCart() throws InterruptedException {
        logger.debug("Starting Complete Purchase test (TC04_CompletePurchaseNg)");

        try {
            AccountPage ap = new AccountPage(getDriver());
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

            ProductPage pp = new ProductPage(getDriver());
            logger.debug("Scrolling to delivery date input");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", pp.txt_DeliveryDate);
            Thread.sleep(1000);

            LocalDate today = LocalDate.now();
            LocalDate deliveryDate = today.plusDays(5);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDeliveryDate = deliveryDate.format(formatter);

            logger.debug("Setting delivery date: " + formattedDeliveryDate);
            pp.txt_DeliveryDate.clear();
            Thread.sleep(1000);
            pp.txt_DeliveryDate.sendKeys("05/30/2025"); // Hardcoded date for demonstration

            Thread.sleep(2100);
            logger.debug("Clicking Add to Cart");
            pp.btn_AddCart.click();
            Thread.sleep(2000);

            logger.debug("Scrolling to Checkout button");
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", pp.checkoutButton);
            Thread.sleep(2500);

            logger.debug("Clicking Checkout");
            pp.clickCheckOut();
            Thread.sleep(1000);

            logger.debug("Clicking Login link on checkout");
            pp.clickLoginLink();
            Thread.sleep(1000);

            LoginPage lp = new LoginPage(getDriver());
            logger.debug("Entering login credentials");
            lp.setEmail("sid@cloudberry.services");
            lp.setPwd("Test123");
            lp.clickLogin();

            logger.debug("Clicking Checkout after login");
            pp.clickCheckOut();
            Thread.sleep(1000);

            CheckoutPage cp = new CheckoutPage(getDriver());
            logger.debug("Selecting existing shipping address");
            cp.selectExistingShippingAddress();
            Thread.sleep(500);

            logger.debug("Selecting shipping address");
            cp.selectShippingAddress();
            Thread.sleep(1500);

            logger.debug("Selecting shipping method");
            cp.selectShippingMethod();
            Thread.sleep(1500);

            logger.debug("Confirming shipping method");
            cp.confirmShippingMethod();
            Thread.sleep(1500);

            logger.debug("Selecting payment method");
            cp.selectPaymentMethod();
            cp.confirmPaymentMethod();
            Thread.sleep(1000);

            logger.debug("Confirming order");
            cp.confirmOrder();
            Thread.sleep(1000);

            logger.debug("Verifying if order was successful");
            try {
                Assert.assertTrue(cp.isOrderSuccessful(), "Order was not successful!");
                logger.info("Order placed successfully!");
            } catch (AssertionError ae) {
                logger.error("Assertion failed: Order was not successful.", ae);
                throw ae; // Rethrow to trigger RetryAnalyzer
            }

        } catch (Exception e) {
            logger.error("Exception occurred during test execution", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.debug("Complete Purchase test finished");
    }
}
