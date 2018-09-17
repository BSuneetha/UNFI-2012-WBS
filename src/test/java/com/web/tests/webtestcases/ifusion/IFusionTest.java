/****/
package com.web.tests.webtestcases.ifusion;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.innominds.team.driverinit.DriverManager;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.nunit.WhiteTestRunner;
import com.innominds.team.utils.PropertyFileUtils;
import com.web.tests.ifusion.Login;
import com.web.tests.ifusion.LoginDP;
import com.web.tests.ifusion.Source;
import com.web.tests.ifusion.SourceDP;

public class IFusionTest extends DriverManager {

	Logger logger = LogManager.getLogger(IFusionTest.class.getName());

	private WebDriver driver = null;
	
	

	/**
	 * Inits the
	 *
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	@Parameters({ "browser", "os" })
	@BeforeClass(alwaysRun = true, groups = { "Smoke", "Sanity" })
	public void init(String browser, String osName) throws Exception {
	
		browserName = browser;
		os = osName;
		
		System.out.println("***IFusionTest script execution Started***: "+browser);
		this.driver = getDriver(
				PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "GridExecution"));
		loadURL(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "web.app.url"), driver,
				browserName);
	}

	/*************************************************************************
	 *
	 * TEST SUITE TESTS
	 *
	 *************************************************************************/

	/*
	 * list of Modules Objects Creation ****************************************
	 */
	Login login = new Login();
	Source source = new Source();

	/*
	 * * Data Providers list for Modules
	 * ****************************************
	 */

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

	@DataProvider(name = "LoginDP2")
	public Object[][] LoginDP2() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR2");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR2");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR2");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return LoginDP.createDP("DR2");
		}

		return null;
	}

	@DataProvider(name = "SourceDP")
	public Object[][] SourceDP() {
		if ("firefox".equalsIgnoreCase(browserName)) {
			return SourceDP.createDP("DR1");
		} else if ("chrome".equalsIgnoreCase(browserName)) {
			return SourceDP.createDP("DR1");
		} else if ("ie".equalsIgnoreCase(browserName)) {
			return SourceDP.createDP("DR1");
		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
			return SourceDP.createDP("DR1");
		}

		return null;
	}


	@Test(dataProvider = "LoginDP", enabled = true, groups = "Smoke", priority = 1)
	public void loginApplication(LoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogin(dp, driver);
	}

//	@Test(dataProvider = "SourceDP", enabled = true, groups = "Smoke", priority = 2)
//	public void updateSource(SourceDP dp, ITestContext context) {
//		context.setAttribute("dpName", dp.td.get("DataRow"));
//	}

	@Test(dataProvider = "SourceDP", enabled = true, groups = "Smoke", priority = 3)
	public void createSource(SourceDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		source.createSource(dp, driver);
	}

//	@Test(dataProvider = "SourceDP", enabled = true, groups = "Smoke", priority = 4)
//	public void dsnSource(SourceDP dp, ITestContext context) {
//		context.setAttribute("dpName", dp.td.get("DataRow"));
//	}
//
//	@Test(dataProvider = "SourceDP", enabled = true, groups = "Smoke", priority = 5)
//	public void editDSN(SourceDP dp, ITestContext context) {
//		context.setAttribute("dpName", dp.td.get("DataRow"));
//	}
//
//	@Test(dataProvider = "SourceDP", enabled = true, groups = "Smoke", priority = 6)
//	public void editSource(SourceDP dp, ITestContext context) {
//		context.setAttribute("dpName", dp.td.get("DataRow"));
//	}

	@Test(dataProvider = "LoginDP", enabled = true, groups = "Smoke", priority = 7)
	public void logOutApplication(LoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogout(dp, driver);
	}
	
	@Test(dataProvider = "LoginDP2", enabled = true, groups = "Smoke", priority = 8)
	public void loginApplicationFailure(LoginDP dp, ITestContext context) {
		context.setAttribute("dpName", dp.td.get("DataRow"));
		login.validateLogin(dp, driver);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {

		System.out.println("***IfusionTest script execution Completed***");
		driver.quit();
		/*try {

			if (driver != null) {
				driver.quit();

			}
		} catch (Exception e) {
			//throw new RuntimeException("teardown() method failed to execute " + e);
		}*/
	}	

}