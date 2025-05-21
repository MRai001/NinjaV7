package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AffiliatePage extends BasePage {
	//constructor
	
	
	public AffiliatePage(WebDriver driver)
	{
		super(driver);
	}
	
	
	
	
	//locators
	 // Page elements using @FindBy annotations
    @FindBy(xpath = "//input[@id='input-company']")
    private WebElement companyField;
    
    @FindBy(xpath = "//input[@id='input-website']")
    private WebElement websiteField;
    
    @FindBy(xpath = "//input[@id='input-tax']")
    private WebElement taxField;
    
    @FindBy(xpath = "//input[@id='input-cheque']")
    private WebElement chequeField;
  
    @FindBy(xpath = "//button[normalize-space()='Continue']")
    private WebElement continueButton;
    
    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    private WebElement successAlert;
    
   
	
	//Action methods
    public void enterCompanyInfo(String companyName) {
        companyField.clear();
        companyField.sendKeys(companyName);
    }
    
    public void enterWebsite(String website) {
        websiteField.clear();
        websiteField.sendKeys(website);
    }
    
    public void enterTaxId(String taxId) {
        taxField.clear();
        taxField.sendKeys(taxId);
    }
    
    public void enterChequePayeeName(String payeeName) throws InterruptedException {
        // Scroll to the cheque field
    	((JavascriptExecutor)
				  driver).executeScript("arguments[0].scrollIntoView(true);",
				  chequeField);
        Thread.sleep(1000);
        // Enter the payee name
        chequeField.clear();
        chequeField.sendKeys(payeeName);
    }
    
    public void clickContinue() throws InterruptedException {
        // Scroll to the continue button
    	((JavascriptExecutor)
				  driver).executeScript("arguments[0].scrollIntoView(true);",
				  continueButton);
        Thread.sleep(1000);
        // Click the continue button
        continueButton.click();
    }
    
    public boolean isSuccessMessageDisplayed() {
        return successAlert.isDisplayed();
    }
    
  
    
    // Method to update account information
    public void updateAccountInformation(String company, String website, String taxId, String payeeName) throws InterruptedException {
        enterCompanyInfo(company);
        Thread.sleep(1000);
        enterWebsite(website);
        Thread.sleep(1000);
        enterTaxId(taxId);
        Thread.sleep(1000);
        enterChequePayeeName(payeeName);
        Thread.sleep(1000);
        
        clickContinue();
        Thread.sleep(1000);
    }
    
    // Method to verify success
   // public void verifySuccess() {
       // boolean status = isSuccessMessageDisplayed();
       // Assert.assertTrue(status, "Account information update failed!");
    //}
}

