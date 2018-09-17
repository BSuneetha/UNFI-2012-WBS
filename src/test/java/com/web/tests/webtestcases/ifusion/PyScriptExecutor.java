///****/
//package com.web.tests.webtestcases.ifusion;
//
//import java.io.FileNotFoundException;
//import java.util.Properties;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.python.util.PythonInterpreter;
//import org.testng.ITestContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Parameters;
//import org.testng.annotations.Test;
//
//import com.innominds.team.driverinit.DriverManager;
//import com.innominds.team.frameworkengine.Constants;
//import com.innominds.team.utils.PropertyFileUtils;
//import com.web.tests.ifusion.Login;
//import com.web.tests.ifusion.LoginDP;
//import com.web.tests.ifusion.Source;
//import com.web.tests.ifusion.SourceDP;
//
//public class PyScriptExecutor extends DriverManager {
//
//	Logger logger = LogManager.getLogger(PyScriptExecutor.class.getName());
//
//	/**
//	 * Inits the
//	 *
//	 * @throws Exception
//	 * @throws FileNotFoundException
//	 */
////	//@Parameters({ "browser", "os" })
////	@BeforeClass(alwaysRun = true, groups = { "Regression", "Sanity" })
////	public void init(String browser, String osName) throws Exception {
////		
//////		browserName = browser;
//////		os = osName;
////		
////	}
//
//	/*************************************************************************
//	 *
//	 * TEST SUITE TESTS
//	 *
//	 *************************************************************************/
//
//	/*
//	 * * Data Providers list for Modules
//	 * ****************************************
//	 */
//
////	@DataProvider(name = "LoginDP")
////	public Object[][] LoginDP() {
////		if ("firefox".equalsIgnoreCase(browserName)) {
////			return LoginDP.createDP("DR1");
////		} else if ("chrome".equalsIgnoreCase(browserName)) {
////			return LoginDP.createDP("DR1");
////		} else if ("ie".equalsIgnoreCase(browserName)) {
////			return LoginDP.createDP("DR1");
////		} else if ("phanomjs".equalsIgnoreCase(browserName)) {
////			return LoginDP.createDP("DR1");
////		}
////
////		return null;
////	}
//	@Test(groups = "Regression", priority = 1)
//	public void runScript(ITestContext context) {
//		Properties props = new Properties();
//		props.put("python.home","D:/poc/jython/jython2.7.0/Lib");
//		props.put("python.console.encoding", "UTF-8"); // Used to prevent: console: Failed to install '': java.nio.charset.UnsupportedCharsetException: cp0.
//		props.put("python.security.respectJavaAccessibility", "false"); //don't respect java accessibility, so that we can access protected members on subclasses
//		props.put("python.import.site","false");
//
//		//Properties preprops = System.getProperties();
//		PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
//		PythonInterpreter interpreter = new PythonInterpreter();
//		String script_dir = System.getProperty("user.dir");
//		interpreter.execfile(script_dir+"/src/test/resources/pyscripts/ping2_test.py");
//		interpreter.execfile(script_dir+"/src/test/resources/pyscripts/tracert.py");
//	}
//
//
//
//	@AfterClass(alwaysRun = true)
//	public void tearDown() {
//
//		System.out.println("***Python script execution Completed***");
//
//		/*try {
//
//			if (driver != null) {
//				driver.quit();
//
//			}
//		} catch (Exception e) {
//			//throw new RuntimeException("teardown() method failed to execute " + e);
//		}*/
//	}	
//
//}