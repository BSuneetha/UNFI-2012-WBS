package com.web.tests.wmtestcases.unfi;

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
import com.web.tests.unfi.Login;
import com.web.tests.unfi.LoginDP;
import com.web.tests.unfi.ItemSearch;
import com.web.tests.unfi.ItemSearchDP;

public class ItemSearchTest extends DriverManager{
	
	Logger logger = LogManager.getLogger(ItemSearchTest.class.getName());

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
		
		System.out.println("SamplePO Test Execution Started"+browser);
		this.driver = getDriver(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "GridExecution"));
		loadURL(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "web.app.url"), driver,browserName);
	}
	
	Login login=new Login();
	ItemSearch profile=new ItemSearch();
	
	@DataProvider(name = "LoginDP")
	public Object[][] LoginDP() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR1");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR1");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR1");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR1");
		}

		return null;
	}
	
	@DataProvider(name = "ItemSearchDP")
	public Object[][] ItemSearchDP() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return ItemSearchDP.createDP("DR1");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return ItemSearchDP.createDP("DR1");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return ItemSearchDP.createDP("DR1");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return ItemSearchDP.createDP("DR1");
		}

		return null;
	}	
	
	@Test(dataProvider = "LoginDP", enabled = true, priority = 1)
	public void loginApplication(LoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogin(dp, driver);
	}
	
	@Test(dataProvider = "ItemSearchDP", enabled = true, groups = "Smoke", priority = 2)
	public void navigateToItem(ItemSearchDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		profile.navigateToItem(dp, driver);
	}	
	
/*	@Test(dataProvider = "LoginDP", enabled = true, priority = 3)
	public void logOutApplication(LoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogout(dp, driver);
	}
*/
	@AfterClass(alwaysRun = true)
	public void tearDown() {

	//	driver.quit();
	}
	
}
