package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage{

	
	//constructor

	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	
		//locators
	
	//Link - My Account
		@FindBy(xpath="//i[@class='fa-solid fa-user']")
		public WebElement link_MyAccount;

		//Link - Login
		@FindBy(xpath="//a[normalize-space()='Login']")
		public WebElement link_Login;
		
		//Link -LaptopsAndNotebooks
		@FindBy(xpath="//a[normalize-space()='Laptops & Notebooks']")
		public
		WebElement link_Laptops;
		
		//Link -ShowAll
		@FindBy(xpath="//a[normalize-space()='Show All Laptops & Notebooks']")
		public
		WebElement link_ShowAll;
		
		//Link - SelectLaptop
				@FindBy(xpath="a[normalize-space()='HP LP3065']")
				public WebElement link_SelectLaptop;
		//Link Affiliate
				@FindBy(xpath="//a[normalize-space()='Affiliate']")
				public
				WebElement link_Affiliate;		
	//
	
	//Action Methods
		
		public void clickMyAccount()
		{
			 link_MyAccount.click();
		}
		
		public void goToLogin()
		{
			link_Login.click();
		}
		public void goToLaptop()
		{
			link_Laptops.click();
		}
		public void goToShowAll()
		{
			link_ShowAll.click();
		}
		public void goToAffiliate()
		{
			link_Affiliate.click();
		}
		
		
		
}
