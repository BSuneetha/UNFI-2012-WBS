package com.innominds.team.driverinit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import com.innominds.team.frameworkengine.CommonUtils;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.utils.PropertyFileUtils;

/**
 * The Class Driver Manager.
 * 
 * @author Innominds
 */
public class DriverManager {

	public String browserName = null;
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;
	public static final Logger logger = LogManager.getLogger(DriverManager.class.getName());
	private String browser = null;
	public String os = null;
	//public static ConcurrentHashMap<String, String> BrowserAndOSUsed = new ConcurrentHashMap<String, String>();
	private DesiredCapabilities dc;
	protected WebDriver driver;
	public static int thr_count = 0;
	public static int dr_count = 0;
	public static boolean screenshotprocessing = false;
	public static Boolean isGrid = null;
	
	private WebDriver rwd = driver;

	public WebDriver getCurrentDriver() {
		return rwd;
	}
	
	public WebDriver getDriver(String executionType, String br, String osname11) throws Exception {
		System.out.println("Get Driver called");
		browserName = br;
		os = osname11;
		dc = new DesiredCapabilities();
		if (executionType.equalsIgnoreCase("true")) {
			isGrid = true;
			//BrowserAndOSUsed.put(browserName, os);
			initDriver();
			thr_count++;
			rwd = threadDriver.get();
			return rwd;
		} else {
			isGrid = false;
			dr_count++;
			//BrowserAndOSUsed.put(browserName, os);
			setDC_Capabilities(browserName, os, true);
			//System.out.println("After set DC capapabilities: "+getCurrentDriver().toString()+" "+browserName+" "+os);
			rwd = driver;
			//System.out.println("$$Driver value here: "+driver.toString());
			return driver;
			
		}

	}
	
	public WebDriver getDriver(String executionType) throws Exception {
		dc = new DesiredCapabilities();
		if (executionType.equalsIgnoreCase("true")) {
			isGrid = true;
			//BrowserAndOSUsed.put(browserName, os);
			initDriver();
			thr_count++;
			rwd = threadDriver.get();
			return rwd;
		} else {
			isGrid = false;
			dr_count++;
			//BrowserAndOSUsed.put(browserName, os);
			setDC_Capabilities(browserName, os, true);			
			rwd = driver;
			String drvnullchk = driver== null ? "Driver null ": "Driver not null";
			//System.out.println("Returning getDriver "+drvnullchk);
			return driver;
		}

	}
	
	public void setDC_Capabilities(String browser, String OSname, boolean isLocalDriver) throws IOException, InterruptedException {
		//System.out.println("Called set DC caps: islocal "+isLocalDriver+" "+browser+" "+os);
		dc = new DesiredCapabilities();
		
		dc.setCapability("ignoreZoomSetting", true);
		dc.setCapability("ignoreProtectedModeSettings", true);
		
		switch(browser.toLowerCase()) {
		
			case "firefox": dc = DesiredCapabilities.firefox();
							
							if(isLocalDriver) {		
								if ("win10".equalsIgnoreCase(OSname)) {
									System.setProperty("webdriver.gecko.driver", getCurrentProjectDirecotry()
										+ "/src/test/resources/Drivers/firefox/firefox_64/geckodriver.exe");
								} else if ("mac".equalsIgnoreCase(os)) {
									System.setProperty("webdriver.gecko.driver", getCurrentProjectDirecotry()
											+ "/src/test/resources/Drivers/firefox/mac/geckodriver");
								} else if (("linux").equalsIgnoreCase(os)) {
									System.setProperty("webdriver.gecko.driver", getCurrentProjectDirecotry()
											+ "/src/test/resources/Drivers/firefox/linux64/geckodriver");
								}
								Thread.sleep(3000);
								driver = new FirefoxDriver(dc);								
								Thread.sleep(3000);								
								if (driver == null) {
									//System.out.println("############Driver null after long wait");
								} else {
									//System.out.println("############Driver not null after long wait");
								}
								//System.out.println("Firefox section: "+getCurrentDriver().toString()+" "+browserName+" "+os);
							}
							else
								dc.setPlatform(Platform.extractFromSysProperty(OSname)); 
							
							break;
			case "chrome": dc = DesiredCapabilities.chrome();
							
							if(isLocalDriver) {
								if ("win10".equalsIgnoreCase(OSname)) {
									System.setProperty("webdriver.chrome.driver", getCurrentProjectDirecotry()
										+ "/src/test/resources/Drivers/chrome/chromedriver_win32/chromedriver.exe");			
										dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
								} else if ("mac".equalsIgnoreCase(os)) {
									System.setProperty("webdriver.chrome.driver", getCurrentProjectDirecotry()
											+ "/src/test/resources/Drivers/chrome/chromedriver_mac64/chromedriver");
								} else if (("linux").equalsIgnoreCase(os)) {
									System.setProperty("webdriver.chrome.driver", getCurrentProjectDirecotry()
											+ "/src/test/resources/Drivers/chrome/chromedriver_linux64/chromedriver");
								}
								//System.out.println("Chrome section before init: "+browserName+" "+os);
								Thread.sleep(3000);
								driver = new ChromeDriver(dc);
								
								Thread.sleep(3000);
								
								if (driver == null) {
									//System.out.println("############Driver null after long wait");
								} else {
									//System.out.println("############Driver not null after long wait");
								}
								//System.out.println("Chrome section: "+getCurrentDriver().toString()+" "+browserName+" "+os);
								//System.out.println("Driver values: driver "+driver+" getcurrentdriver "+getCurrentDriver());
							}
							else
								dc.setPlatform(Platform.extractFromSysProperty(OSname)); 
							break;	
			case "ie": dc = DesiredCapabilities.internetExplorer();
							dc.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
							
							if(isLocalDriver) {
								System.setProperty("webdriver.ie.driver",
										getCurrentProjectDirecotry() + "/src/test/resources/Drivers/iexplore32/IEDriverServer.exe");
								driver = new InternetExplorerDriver(dc);								
							}
							else
								dc.setPlatform(Platform.extractFromSysProperty(OSname)); 
							break;
			case "edge": dc = DesiredCapabilities.edge();
			   				dc.setBrowserName(DesiredCapabilities.edge().getBrowserName());
			   				
						    if(isLocalDriver) {
						    	System.setProperty("webdriver.edge.driver",
										getCurrentProjectDirecotry() + "/src/test/resources/Drivers/Edge/MicrosoftWebDriver.exe");
							    driver = new EdgeDriver(dc);							    
						    }
							else 
								dc.setPlatform(Platform.extractFromSysProperty(OSname)); 
						    break;
			case "safari": dc = DesiredCapabilities.safari();
							dc.setBrowserName(DesiredCapabilities.safari().getBrowserName());
						    
							if(isLocalDriver) 
							    driver = new SafariDriver(dc);
							else
								dc.setPlatform(Platform.extractFromSysProperty(OSname));
						    break;
			case "phantomjs": dc = DesiredCapabilities.phantomjs();
							dc.setBrowserName(DesiredCapabilities.phantomjs().getBrowserName());
							if (isLocalDriver) {
								if ("win10".equalsIgnoreCase(OSname)) {
									dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
											getCurrentProjectDirecotry()
													+ "/src/test/resources/Drivers/phantomjs/windows/bin/phantomjs");
								} else if ("mac".equalsIgnoreCase(os)) {
									dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
											getCurrentProjectDirecotry() + "/src/test/resources/Drivers/phantomjs/mac/bin/phantomjs");
								} else if (("linux").equalsIgnoreCase(os)) {
									dc.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
											getCurrentProjectDirecotry()
													+ "/src/test/resources/Drivers/phantomjs/linux_64/bin/phantomjs");
								}
								driver = new PhantomJSDriver(dc);
							}
							else
								dc.setPlatform(Platform.extractFromSysProperty(OSname));
						    break;
		}
		String nullchk = (driver==null)? "Driver null here":"Driver not null here";
		//System.out.println("Before return of set DC "+nullchk);
		
	}

	public void initDriver() throws IOException {
		PropertyFileUtils props = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.DOCKER_PROPERTIES_FILE));
		String dock = props.getDataFromPropertyFile("docker");
		boolean isDocker = ( (dock == null) || (dock.isEmpty()) ) ? false : true; 
		if (isDocker) {
			//Sample Hub url: <machine-name/IP:<port>>, like 12.23.24.56:4444
			String hurl = props.getDataFromPropertyFile("dockerhuburl");
			String huburl = (hurl.isEmpty()||hurl==null) ? "localhost:4444" : hurl;
			threadDriver = new ThreadLocal<RemoteWebDriver>();
			try {
				setDC_Capabilities(browserName, os, false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			URL url = new URL("http://"+huburl+"/wd/hub");
			threadDriver.set(new RemoteWebDriver(url, dc));			
		}
		else {
			//not docker means local selenium grid 
			threadDriver = new ThreadLocal<RemoteWebDriver>();
			try {
				setDC_Capabilities(browserName, os, false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threadDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
		}
	}

	/**
	 * Gets the current browser type.
	 * 
	 * @return the current browser type
	 */
	public String getCurrentBrowserType() {
		return browser;
	}
	
	/**
	 * Load url.
	 * 
	 * @param url
	 *            the url
	 * @throws Exception
	 *             the exception
	 */
	public void loadURL(String url, WebDriver driver) throws Exception {
		//this.driver = new Augmenter().augment(driver);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	/**
	 * Load url.
	 * 
	 * @param url
	 *            the url
	 * @throws Exception
	 *             the exception
	 */
	public void loadURL(String url, WebDriver driver2, String browser) throws Exception {
		//this.driver = new Augmenter().augment(driver);
		//System.out.println("loadurl called: "+url+" driver "+getCurrentDriver().toString()+" browser "+browser);
		driver.get(url);
		if ("firefox".equalsIgnoreCase(browser)) {

		} else {
			driver2.manage().window().maximize();
		}

		driver2.manage().timeouts().implicitlyWait(Constants.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		rwd = driver2;
	}

	/**
	 * Quit browser.
	 *
	 * @return the string
	 */
	public boolean quitBrowser(WebDriver driver) {
		boolean isQuit = false;
		try {
			driver.quit();
			isQuit = true;

		} catch (Exception e) {
			throw new RuntimeException("Failed: to quit the browser " + e.getMessage());
		}
		return isQuit;

	}

	/**
	 * Close browser.
	 *
	 * @return true, if successful
	 */
	public boolean closeBrowser(WebDriver driver) {
		boolean isClose = false;
		try {
			driver.close();
			isClose = true;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to close the browser " + e.getMessage());
		}
		return isClose;

	}

	public String captureScreeshot(String fileName, WebDriver driver) throws Exception {

		String dest = System.getProperty("user.dir") + "/ScreenShots/" + fileName + ".png";

		File destination = new File(dest);
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(source, destination);

		return dest;

	}

	public String getCurrentProjectDirecotry() throws IOException {
		return new java.io.File(".").getCanonicalPath();
	}

	@AfterMethod(groups = { "Regression", "Smoke", "Sanity" })
	public void takeScreenShot(ITestResult result) {
		Object objscreenshot = new Object();
		synchronized(objscreenshot) {
			screenshotprocessing = true;
			if (result.getStatus() == ITestResult.FAILURE) {
				String browserNam = result.getTestContext().getCurrentXmlTest().getParameter("browser");
				String osName = result.getTestContext().getCurrentXmlTest().getParameter("os");
	
				String testName = result.getMethod().getMethodName();
				String senarioName = result.getInstanceName();
				String[] classNameExtractorFromPack = senarioName.split("\\.");
				for (String className : classNameExtractorFromPack) {
					senarioName = className;
				}
	
				try {
					captureScreeshot(senarioName + "_" + testName + "_" + osName + "_" + browserNam, driver);
					
				} catch (Exception e) {
					Assert.assertTrue(false, "Test case failed due to exception "+e.getMessage());
					
				}
			}
			screenshotprocessing = false;
		}
	}

}