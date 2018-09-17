package com.web.tests.unfi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.frameworkengine.PageActionUtils;

public class WBSLogin extends PageActionUtils {

	Logger logger = LogManager.getLogger(ItemSearch.class.getName());

	/** Click on MyProfile menu */

	public void navigateMyProfile(ItemSearchDP dp, WebDriver driver) {
		try {


			click(getWebElement(dp.or, "mpLinkName", driver));
			waitForPageLoaded(driver);

			Assert.assertTrue(getWebElement(dp.or, "epText", driver).getText().equals(dp.td.get("EPText")));
			
			click(getWebElement(dp.or, "clickLinkHR", driver));
			waitForPageLoaded(driver);
			
			enterText(getWebElement(dp.or, "enterAccNo", driver), dp.td.get("EnterAccNo"));
			
			click(getWebElement(dp.or, "clickSaveBtn", driver));
			
			Assert.assertTrue(getTextFromJSAlert(true, driver).equals(dp.td.get("AlertText")));
			
		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}
	
}
