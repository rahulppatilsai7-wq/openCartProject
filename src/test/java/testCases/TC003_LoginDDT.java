package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LogInPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Master")
	public void verify_LoginDDT(String email, String psw, String sts) {
		
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
        hp.clickLogIn();
		
		logger.info("Passing Email and Password................");
		LogInPage lp=new LogInPage(driver);
		lp.setEmail(email);
		lp.setPassword(psw);
		lp.clickLogInbtn();
		
		MyAccountPage map=new MyAccountPage(driver);
		boolean status=map.isMyAccountTextPresent();
		
		/*Valid data --> login success -> test pass -- logout
		 *               login fail -> test fail
		 *               
		 *Invalid data --> login success -> test fail -- logout
		 *                 login fail -> test pass                
		 */

		if (sts.equalsIgnoreCase("valid")) {

			if (status == true) {
				map.clickLogOutbtn();
				Assert.assertTrue(true);
			} else
				Assert.assertTrue(false);
		}

		else {
			if (status == true) {
				map.clickLogOutbtn();
				Assert.assertTrue(false);
			} else
				Assert.assertTrue(true);
		}
	} 
		catch (Exception e) {
		Assert.fail();
	}
		logger.info("******* TC_003LoginDDT finished *******");
}
}
