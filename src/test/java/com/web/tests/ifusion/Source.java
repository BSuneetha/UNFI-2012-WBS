package com.web.tests.ifusion;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.frameworkengine.PageActionUtils;

import io.appium.java_client.AppiumDriver;

public class Source extends PageActionUtils {

	

	Logger logger = LogManager.getLogger(Source.class.getName());

	/** Login into Ifusion Application */

	public void createSource(SourceDP dp, WebDriver driver) {
		try {

			Reporter.log("Creation of Source ", true);

			String dsnName = "DSNName" + System.currentTimeMillis();
			Thread.sleep(3000);
			waitForPageLoaded(driver);
			click(getWebElement(dp.or, "clickSourceTab", driver));
			waitForPageLoaded(driver);

			Thread.sleep(15000);

			waitForElementPresent(getWebElement(dp.or, "clickAddSource", driver), 5000);

			click(getWebElement(dp.or, "clickAddSource", driver));
			waitForPageLoaded(driver);

			Thread.sleep(3000);

			click(getWebElement(dp.or, "clickElement", driver));
			waitForPageLoaded(driver);

			click(getWebElement(dp.or, "selectHIVE", driver));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "dsnName", driver), dsnName);
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "description", driver), dp.td.get("Description"));
			waitForPageLoaded(driver);

			click(getWebElement(dp.or, "dropDown", driver));
			waitForPageLoaded(driver);

			click(getWebElement(dp.or, "selectIndirectory", driver));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "sendURL", driver), dp.td.get("URL"));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "sendDomain", driver), dp.td.get("Domain"));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "sendDatabase", driver), dp.td.get("DataBase"));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "sendUsername", driver), dp.td.get("UserName"));
			waitForPageLoaded(driver);

			enterText(getWebElement(dp.or, "sendPassword", driver), dp.td.get("Password"));
			waitForPageLoaded(driver);

			jsScrollWindow(driver);

			Thread.sleep(5000);

			//click(getWebElement(dp.or, "saveBtn", driver));
			jsClick(getWebElement(dp.or, "saveBtn", driver),driver);
			waitForPageLoaded(driver);

			Thread.sleep(7000);

			Assert.assertTrue(isElementDisplayed(driver.findElement(By.xpath("//span[text()='" + dsnName + "']"))),
					"Datasource is not created");

		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

	}

}