package pageObjects;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {

	
	//constructor
		
		public ProductPage(WebDriver driver)
		
	{
		super(driver);
		
	}
	
	
			//locators
		
		//Input Delivery Date
			@FindBy(xpath="//input[@id='input-option-225']")
			public
			WebElement txt_DeliveryDate;
			
			@FindBy(xpath="//button[@id='button-cart']")
			public
			WebElement btn_AddCart;
			
			@FindBy(xpath="//div[@class='alert alert-success alert-dismissible']")
			public
			WebElement txt_message;
			
			 @FindBy(xpath = "//a[@title='Checkout']//i[@class='fa-solid fa-share']")
			public WebElement checkoutButton;
			
			 @FindBy(xpath = "//div//button//i[@class='fa-solid fa-heart']")
			  public WebElement addToWishlistButton;

			    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
			   public WebElement successAlert;
			  
			    @FindBy(xpath = "//strong[normalize-space()='login page']")
			     public WebElement loginPageLink;
				
				 @FindBy(xpath ="//a[@class='dropdown-item'][normalize-space()='My Account']")
						 public WebElement accountPageLink;
				
			
		//Action methods	
			
			public void setDeliveryDate(String date)
			{
				txt_DeliveryDate.sendKeys(date);
			}
			public void clickAddCart()
			{
				btn_AddCart.click();
			}
			public WebElement getMessageConfirmation()
			{
				return txt_message;
			}
			
			public void clickCheckOut()
			{
				 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutButton);
		         				
				checkoutButton.click();;
			}
			 public void clickAddToWishlist() {
			        addToWishlistButton.click();
			    }

			    public boolean verifySuccessMessage() {
			        String actualMessage = successAlert.getText();
			        return actualMessage.contains("Success");
			    }

			    public void clickLoginLink() {
			         loginPageLink.click();
			     }
			     public void goToAccountLink() {
			       accountPageLink.click();
			     }
			     
	}			

