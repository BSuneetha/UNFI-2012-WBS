package com.web.tests.unfi;

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

	public void validateLogin(LoginDP dp, WebDriver driver) {
		try {
			System.out.println("WM Login");

			enterText(getWebElement(dp.or, "username", driver), dp.td.get("UserName"));
			Thread.sleep(2000);
			enterText(getWebElement(dp.or, "password", driver), "123123");
			Thread.sleep(2000);
			click(getWebElement(dp.or, "loginBtn", driver));

			waitForPageLoaded(driver);

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}

	/** Logout from WM Application successfully 
	public void validateLogout(LoginDP dp, WebDriver driver) {
		try {

			Reporter.log("WM Logout", true);

			Reporter.log("........Logout WM Application successfully ........", true);
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
*/
}