package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage{
	
	public MyAccountPage(WebDriver driver) {
		super(driver);		
	}
	
	@FindBy(xpath="//div[@class='col-sm-9']//h2[text()='My Account']")
	WebElement msgMyAccount;
	
	@FindBy(xpath="(//a[text()='Logout'])[2]")
	WebElement btnLogOut;
	
	public void clickLogOutbtn() {
		btnLogOut.click();
	}
	
	public String getTextMsg() {
		String txt=msgMyAccount.getText();
		return txt;
	}
	
	public boolean isMyAccountTextPresent() {
		try {
		return (msgMyAccount.isDisplayed());
		}
		catch(Exception e) {
			return false;
		}
	}

}
