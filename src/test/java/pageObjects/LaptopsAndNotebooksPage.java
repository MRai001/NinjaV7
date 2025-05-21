package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LaptopsAndNotebooksPage extends BasePage {
	//constructor
		
		public LaptopsAndNotebooksPage(WebDriver driver)
		{
			super(driver);
		}
		
		
			//locators
		//Link - SelectLaptop
		@FindBy(xpath="//div[@class='description']//a[contains(text(),'HP LP3065')]")
		public
		WebElement link_SelectLaptop;

		public void goToSelectLaptop()
		{
			link_SelectLaptop.click();
		}
		
}
