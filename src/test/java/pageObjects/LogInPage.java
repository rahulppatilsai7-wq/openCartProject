package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage extends BasePage{

	public LogInPage(WebDriver driver){
		super(driver);
	}
	
	@FindBy(id="input-email") 
	WebElement txtEmail;
	
	@FindBy(id="input-password")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogIn;
	
	
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void setPassword(String psw) {
		txtPassword.sendKeys(psw);
	}
	
	public void clickLogInbtn() {
		btnLogIn.click();
	}
	
}
