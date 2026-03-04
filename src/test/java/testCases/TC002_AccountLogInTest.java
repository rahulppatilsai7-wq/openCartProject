package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LogInPage;
import pageObjects.MyAccountPage;

public class TC002_AccountLogInTest extends BaseClass{
	
	@Test(groups={"Sanity","Master"} )
	public void accountLogInValidation() {
		try {
		logger.info("****** accountLogInValidation test started ******");
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogIn();
		
		logger.info("Passing Email and Password................");
		LogInPage lp=new LogInPage(driver);
		lp.setEmail(prop.getProperty("email"));
		lp.setPassword(prop.getProperty("password"));
		lp.clickLogInbtn();
		
		logger.info("Clicked on login button....................");
		logger.info("Getting the \"My Account\" text....................");
		
		MyAccountPage ma=new MyAccountPage(driver);
		boolean targetmsg=ma.isMyAccountTextPresent();
		Assert.assertTrue(targetmsg);
		String msg=ma.getTextMsg();
		//System.out.println(msg);
		Assert.assertEquals(msg, "My Account");
		}
		
		catch(AssertionError e) {
			System.out.println(e.getMessage());
			Assert.fail();
		}
		logger.info("accountLogInValidation test completed....................");
	}

}
