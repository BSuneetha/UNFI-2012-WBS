package com.web.tests.ifusion;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.frameworkengine.PageActionUtils;

import io.appium.java_client.AppiumDriver;

public class Login extends PageActionUtils {

	

	Logger logger = LogManager.getLogger(Login.class.getName());

	/** Login into Ifusion Application */

	public void validateLogin(LoginDP dp, WebDriver driver) {
		try {
			System.out.println("IfusionTest script execution Started");

			Reporter.log("Ifusion Login URL", true);

			Reporter.log("........Ifusion Login Application........", true);

			enterText(getWebElement(dp.or, "username", driver), dp.td.get("UserName"));
			enterText(getWebElement(dp.or, "password", driver), dp.td.get("Password"));

			selectByVisibleText(getWebElement(dp.or, "selectRole", driver), dp.td.get("SelectRole"));

			click(getWebElement(dp.or, "loginBtn", driver));

			waitForPageLoaded(driver);

			Thread.sleep(5000);

			Assert.assertTrue(getWebElement(dp.or, "loginName", driver).getText().equals(dp.td.get("UserName")));
			

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}

	/** Logout from Ifusion Application successfully */
	public void validateLogout(LoginDP dp, WebDriver driver) {
		try {

			Reporter.log("Ifusion Logout", true);

			Reporter.log("........Logout Ifusion Application successfully ........", true);
			waitForPageLoaded(driver);
			Thread.sleep(2000);
			//jsScrollWindowUp(driver);
			Thread.sleep(2000);
			click(getWebElement(dp.or, "clickAdmin", driver));

			waitForPageLoaded(driver);
			click(getWebElement(dp.or, "logoutBtn", driver));
			Assert.assertTrue(isElementDisplayed(getWebElement(dp.or, "username", driver)));
			waitForPageLoaded(driver);
			System.out.println("Ifusion LogOut is Completed");

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}

}