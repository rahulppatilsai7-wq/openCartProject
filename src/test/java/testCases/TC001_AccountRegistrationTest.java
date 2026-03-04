package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegistrationPage;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups={"Regression","Master"} )
	public void verifyAccountRegistration() {
		try {
			logger.info("****** Starting TC001_AccountRegistrationTest ******");
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			logger.info("clicked on My account");
			hp.clickRegister();
			logger.info("clicked on Register");

			RegistrationPage rp = new RegistrationPage(driver);
			rp.setFirstName(generateString().toUpperCase());
			rp.setLastName(generateString().toUpperCase());
			rp.setEmail(generateString() + "@gmail.com");
			rp.setTelePhone(generateNumber());

			String psw = generateAlphaNumeric();
			rp.setPassword(psw);
			rp.setConfirmPassword(psw);
			rp.clickPolicyCheckBox();
			rp.clickContinue();

			logger.info("Validating confirmation message");
			String actualmsg = rp.getConfirmationMsg();
			Assert.assertEquals(actualmsg, "Your Account Has Been Created!"
					+ "");
		} catch (AssertionError e) {
			logger.error("test failed"+e.getMessage());
			logger.debug("debug logs..");
			Assert.fail();
		}

		logger.info("****** Finished TC001_AccountRegistrationTest ******");

	}

}
