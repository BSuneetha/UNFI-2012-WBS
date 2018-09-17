package com.web.tests.webtestcases.ifusion;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.innominds.team.driverinit.DriverManager;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.utils.PropertyFileUtils;
import com.web.tests.ifusion.MySpaceLoginDP;
import com.web.tests.ifusion.SourceDP;
import com.web.tests.ifusion.MySpaceLogin;
import com.web.tests.ifusion.MyProfile;
import com.web.tests.ifusion.MyProfileDP;;

public class MySpaceTest extends DriverManager{
	
	Logger logger = LogManager.getLogger(MySpaceTest.class.getName());

	private WebDriver driver = null;
	
	/**
	 *
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	@Parameters({ "browser", "os" })
	@BeforeClass(alwaysRun = true)
	public void init(String browser, String osName) throws Exception {
	
		browserName = browser;
		os = osName;
		
		System.out.println("MySpace Test Execution Started"+browser);
		this.driver = getDriver(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "GridExecution"));
		loadURL(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "web.app.url"), driver,browserName);
	}
	
	MySpaceLogin login=new MySpaceLogin();
	MyProfile profile=new MyProfile();
	
	@DataProvider(name = "MySpaceLoginDP")
	public Object[][] MySpaceLoginDP() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return MySpaceLoginDP.createDP("DR1");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return MySpaceLoginDP.createDP("DR1");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return MySpaceLoginDP.createDP("DR1");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return MySpaceLoginDP.createDP("DR1");
		}

		return null;
	}
	
	@DataProvider(name = "MyProfileDP")
	public Object[][] MyProfileDP() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return MyProfileDP.createDP("DR1");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return MyProfileDP.createDP("DR1");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return MyProfileDP.createDP("DR1");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return MyProfileDP.createDP("DR1");
		}

		return null;
	}	
	
	@Test(dataProvider = "MySpaceLoginDP", enabled = true, priority = 1)
	public void loginApplication(MySpaceLoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogin(dp, driver);
	}
	
	@Test(dataProvider = "MyProfileDP", enabled = true, groups = "Smoke", priority = 2)
	public void navigateMyProfile(MyProfileDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		profile.navigateMyProfile(dp, driver);
	}	
	
	@Test(dataProvider = "MySpaceLoginDP", enabled = true, priority = 3)
	public void logOutApplication(MySpaceLoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogout(dp, driver);
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {

		System.out.println("MySpace script execution Completed");
		driver.quit();
	}
	
}
