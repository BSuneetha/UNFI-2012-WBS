package com.web.tests.ifusion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.frameworkengine.PageActionUtils;

public class MySpaceLogin extends PageActionUtils{

	Logger logger = LogManager.getLogger(MySpaceLogin.class.getName());
	
	public void validateLogin(MySpaceLoginDP dp, WebDriver driver) {
		try {
			
			Reporter.log("MySpace Login URL", true);

			enterText(getWebElement(dp.or, "username", driver), dp.td.get("UserName"));
			
			enterText(getWebElement(dp.or, "password", driver), dp.td.get("Password"));
			
			click(getWebElement(dp.or, "loginBtn", driver));
			
			waitForPageLoaded(driver);
			
			Thread.sleep(5000);

			Assert.assertTrue(getWebElement(dp.or, "displayName", driver).getText().equals(dp.td.get("DisplayName")));
			

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}
	
	public void validateLogout(MySpaceLoginDP dp, WebDriver driver) {
		try {

			Reporter.log("Outlook Logout", true);

			Reporter.log("........Logout MySpace Application successfully ........", true);
			waitForPageLoaded(driver);

			click(getWebElement(dp.or, "logoutLink", driver));
			
			waitForPageLoaded(driver);
			
			isElementDisplayed(getWebElement(dp.or, "loginBtn", driver));
			
			System.out.println("MySpace LogOut is Completed");

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}

	
}