package com.innominds.cucumber;
//package step_definitions;
//
//import java.io.FileNotFoundException;
//
//import org.junit.After;
//import org.junit.Before;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverException;
//
//import cucumber.api.Scenario;
//import main.Constants;
//import main.DriverManager;
//import main.PropertyFileUtils;
//import parallel.InitBrowserOS;
//
//
////enum Environment {
////	DEV("https://dev.burst.com/"), STAGE("https://stage.burst.com/"), PROD(
////			"https://burst.com");
////
////	private String url;
////
////	Environment(String envUrl) {
////		this.url = envUrl;
////	}
////
////	public String getUrl() {
////		return url;
////	}
////}
//
//public class Hooks extends DriverManager {
//	public WebDriver driver;
//	//private static DriverType selectedDriverType;
//
//	//private static final DriverType defaultDriverType = CHROME;
//	//private static final String browser = System.getProperty("browser")
//		//	.toUpperCase();
//
//	//private static final Environment defaultEnvType = Environment.DEV;
//	//private static final String env = System.getProperty("env").toUpperCase();
//
//	//private static final String operatingSystem = System.getProperty("os.name")
//		//	.toUpperCase();
//	//private static final String systemArchitecture = System
//		//	.getProperty("os.arch");
//	
//	
//
////	public static Environment determineEnvironment() {
////		Environment envType = defaultEnvType;
////
////		try {
////			envType = Environment.valueOf(env);
////		} catch (IllegalArgumentException ignored) {
////			System.err.println("Unknown env specified,defaulting to '"
////					+ envType + "'...");
////		} catch (NullPointerException ignored) {
////			System.err.println("No env specified, defaulting to '" + envType
////					+ "'...");
////		}
////		return envType;
////
////	}
//
////	private static DriverType determineEffectiveDriverType() {
////		DriverType driverType = defaultDriverType;
////
////		try {
////			driverType = valueOf(browser);
////		} catch (IllegalArgumentException ignored) {
////			System.err.println("Unknown driver specified,defaulting to '"
////					+ driverType + "'...");
////		} catch (NullPointerException ignored) {
////			System.err.println("No driver specified, defaulting to '"
////					+ driverType + "'...");
////		}
////		return driverType;
////	}
//	
//	
//
//	/**
//	 * Calls the function at start of the initiation.
//	 * 
//	 */
////	static {
////		System.out.println(" ");
////		selectedDriverType = determineEffectiveDriverType();
////		System.out.println("Current Operating System: " + operatingSystem);
////		System.out.println("Current Architecture: " + systemArchitecture);
////		System.out.println("Current Browser Selection: " + selectedDriverType);
////		System.out.println("Current Environment Selection: "
////				+ determineEnvironment());
////		System.out.println(" ");
////		
////		
////	}
//	
//	public static int th_count = 0;
//
//	private void instantiateWebDriver()
//			throws FileNotFoundException, Exception {
//		System.out.println("You are here in instantiate driver with th count "+th_count);
//		
//		//selectedDriverType.setBrowserDriversBasedOnOS(operatingSystem);
//		//driver = selectedDriverType.getWebDriverObject(desiredCapabilities);
//		browserName = InitBrowserOS.browserList.get(th_count);
//		os = InitBrowserOS.osList.get(th_count);
//		driver = getDriver(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "GridExecution"), browserName, os);
//		loadURL(PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, "web.app.url"), driver,
//				browserName);
//		th_count++;
//		
//	}
//
//	@Before
//	/**
//	 * Delete all cookies at the start of each scenario to avoid
//	 * shared state between tests
//	 */
//	public void getBDDDriver() throws Exception {
//		
//		/**removed if (null == driver) logic which caused lot of mess while running the multiple scenarios.Getting,
//		 * org.openqa.selenium.remote.SessionNotFoundException: Session ID is null. Using WebDriver after calling quit()?.
//		 *  As we are maintaining static instance of driver,it never becomes null through out the scenario execution.
//		 * 
//		 */
//		
//
//		
//
////		DesiredCapabilities desiredCapabilities = selectedDriverType
////				.getDesiredCapabilities();
//
//		instantiateWebDriver();
//
//		driver.manage().deleteAllCookies();
//		// return driver;
//
//	}
//
//	// public void openBrowser() throws MalformedURLException {
//	// System.out.println("Called openBrowser");
//	// //driver = new ChromeDriver();
//	// // System.setProperty("webdriver.firefox.bin",
//	// "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//	// // //driver = new FirefoxDriver();
//	// // DesiredCapabilities capabilities = new DesiredCapabilities();
//	// //// capabilities.setCapability("ignoreZoomSetting", true);
//	// //// capabilities.setCapability("ignoreProtectedModeSettings", true);
//	// // driver = new FirefoxDriver(capabilities);
//	// // //driver.manage().deleteAllCookies();
//	// // driver.manage().window().maximize();
//	//
//	// System.setProperty("webdriver.chrome.driver",
//	// System.getProperty("user.dir") +
//	// "\\src\\test\\resources\\drivers\\"+"chromedriver.exe");
//	// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//	// ChromeOptions options = new ChromeOptions();
//	// //options.addArguments("test-type");
//	// options.addArguments("start-maximized");
//	// //options.addArguments("user-data-dir=D:/temp/");
//	// capabilities.setCapability("chrome.binary",System.getProperty("user.dir")
//	// + "\\src\\test\\resources\\drivers\\"+"chromedriver.exe");
//	// capabilities.setCapability(ChromeOptions.CAPABILITY,options);
//	// driver = new ChromeDriver(capabilities);
//	// //
//	//
//	//
//	// }
//
//	
//}
