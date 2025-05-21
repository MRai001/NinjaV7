package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {
	private WebDriverWait wait;



	//constructor
	
	   public CheckoutPage(WebDriver driver) {
        super(driver);  
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     }
	 
	 @FindBy(xpath = "//strong[normalize-space()='login page']")
     public WebElement loginPagePPLink;
	
	 @FindBy(id= "input-shipping-existing")
     public WebElement shippingAddressRadio;
	
        @FindBy(xpath = "//select[@id='input-shipping-address']")
	     public WebElement shippingAddressDropdown;
     
     @FindBy(id = "button-shipping-methods")
     public WebElement shippingMethodsButton;
     
     @FindBy(id = "button-shipping-method")
     public WebElement confirmShippingButton;
     
     @FindBy(id = "button-payment-methods")
     public WebElement paymentMethodsButton;
     
     
     
     @FindBy(id="button-payment-method")
     public WebElement confirmPaymentButton;
     
     @FindBy(xpath = "//div[@class='text-end']//button[contains(text(),'Confirm')]")
     public WebElement confirmOrderButton;
     
     @FindBy(xpath = "//h1[normalize-space()='Your order has been placed!']")
     public WebElement orderSuccessMessage;
     
    
     
     public void goToLoginPage() {
         loginPagePPLink.click();
     }
     
     public void selectExistingShippingAddress() {
    shippingAddressRadio.click();
     }
     
    
     
     
     public void selectShippingAddress() {
         Select select = new Select(shippingAddressDropdown);
         select.selectByIndex(1);
     }
     
     public void selectShippingMethod() {
    	 
		shippingMethodsButton.click();
         
     }
     
     public void confirmShippingMethod() {
    	
        confirmShippingButton.click();
     }
     
     public void selectPaymentMethod() {
        paymentMethodsButton.click();
        
     }
     public void confirmPaymentMethod() {
        
         confirmPaymentButton.click();
     }
     
     public void confirmOrder() throws InterruptedException {
         ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", confirmOrderButton);
         Thread.sleep(1000);
         
         // Re-locate to avoid stale references
         WebElement confirmButtonAgain = driver.findElement(
                 org.openqa.selenium.By.xpath("//div[@class='text-end']//button[contains(text(),'Confirm')]"));
         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmButtonAgain);
     }
     
     public boolean isOrderSuccessful() {
         return orderSuccessMessage.isDisplayed();
     }
 }
//button[@id='button-shipping-methods']
//*[@id="button-payment-methods"]
//*[@id="button-shipping-method"]
