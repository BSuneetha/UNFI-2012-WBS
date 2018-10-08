package com.web.tests.unfi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.frameworkengine.PageActionUtils;

public class ItemSearch extends PageActionUtils {

	Logger logger = LogManager.getLogger(ItemSearch.class.getName());

	/** Click on Items(Configuration) */

	public void navigateToItem(ItemSearchDP dp, WebDriver driver) {
		try {

			Thread.sleep(10000);
			click(getWebElement(dp.or, "clickMenu", driver));
			moveToElement(getWebElement(dp.or, "clickMenu", driver), getWebElement(dp.or, "clickItemLink", driver), driver);
			
			switchToWindowHandle(driver);
			
			Thread.sleep(6000);
			
			verifyTextPresent(getWebElement(dp.or, "verifyItemPageTitle",driver),dp.td.get("ItemTitleText"));
			
			//click(getWebElement(dp.or, "enterItemId", driver));
			enterText(getWebElement(dp.or, "enterItemId", driver), dp.td.get("ItemId"));
			
			click(getWebElement(dp.or, "clicswitchToItemFramekApplyBtn", driver));
			
			verifyTextPresent(getWebElement(dp.or, "verifyItemDescrptn",driver),dp.td.get("ItemDescription"));
			
			
		} catch (Exception e) {

			Assert.assertTrue(false, "Test case failed due to exception " + e.getMessage());
		}

		
	}
	
	//Load log file and search for string.
	public String SearchItemInLogFile(String filepath, String searchItem) {
		File file = new File(filepath);
		String ItemFound=null;
		try {
		    Scanner scanner = new Scanner(file);

		    while (scanner.hasNextLine()) {
		    	
		        String line = scanner.nextLine();
		    
		        String Words[]=line.split(" ");
		        for (int i=0; i< Words.length; i++) { 
		        	if(Words[i].equalsIgnoreCase(searchItem)) {
		        		System.out.println("string found ");
		        		ItemFound=searchItem;
		        	}
		        	else
		        		System.out.println("string not found ");
		        }
		    }
		    
		   scanner.close();
		} catch(FileNotFoundException e) { 
		    
		}
		return ItemFound;
	}
}
