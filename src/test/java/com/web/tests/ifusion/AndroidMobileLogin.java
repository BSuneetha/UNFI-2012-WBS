package com.web.tests.ifusion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.innominds.team.frameworkengine.PageActionUtils;

public class AndroidMobileLogin extends PageActionUtils{
	
	Logger logger = LogManager.getLogger(AndroidMobileLogin.class.getName());

	/** Click on MyProfile menu */

	public void navigateMyProfile(AndroidMobileDP dp, WebDriver driver) {
		try {

			click(getMobileElement(dp.or, "mpLinkName"));
			waitForPageLoaded(driver);

			Assert.assertTrue(getMobileElement(dp.or, "epText").getText().equals(dp.td.get("EPText")));
			
			click(getMobileElement(dp.or, "clickLinkHR"));
			waitForPageLoaded(driver);
			
			enterText(getMobileElement(dp.or, "enterAccNo"), dp.td.get("EnterAccNo"));
			
			click(getMobileElement(dp.or, "clickSaveBtn"));
			
			Assert.assertTrue(getTextFromJSAlert(true, driver).equals(dp.td.get("AlertText")));
			
		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}
	
}
