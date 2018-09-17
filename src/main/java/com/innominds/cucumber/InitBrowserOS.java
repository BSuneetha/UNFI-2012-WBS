package com.innominds.cucumber;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.utils.PropertyFileUtils;

public class InitBrowserOS {
	
	//public static ArrayList<WebDriver> driverList = new  ArrayList<WebDriver>();
	public static ArrayList<String> browserList = new ArrayList<String>();
	public static ArrayList<String> osList = new ArrayList<String>();
	
	public static void init() throws Exception {
		int threads = 0;
		try {
			threads = Integer.parseInt(PropertyFileUtils.getPropValuesFromConfig(Constants.CUCUMBER_PROPERTIES_FILE, "nodes"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String browservalues = "";
		String osvalues = "";
		try {
			browservalues = PropertyFileUtils.getPropValuesFromConfig(Constants.CUCUMBER_PROPERTIES_FILE, "browsers");
			osvalues = PropertyFileUtils.getPropValuesFromConfig(Constants.CUCUMBER_PROPERTIES_FILE, "os");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String br[] = browservalues.split(",");
		String os[] = osvalues.split(",");
		WebDriver dd;
		for (int i=0; i<threads; i++) {
			browserList.add(br[i]);
			
			//getDriver(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "GridExecution"));
//			loadURL(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "web.app.url"), driver,
//					browserName);
			osList.add(os[i]);
		}
		System.out.println("Init called## browsers: "+browserList.toString()+" os: "+osList.toString());
	}
}
