package com.web.tests.unfi;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
//import org.apache.log4j.spi.Configurator;
//import org.apache.logging.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.python.jline.internal.Log;
import com.innominds.team.driverinit.DriverManager;


public class LogsDemo extends DriverManager {
	ItemSearch is=new ItemSearch();
	Logger logger = Logger.getLogger(Log.class.getName());
	String logfile="logfile.log";
	//DOMConfigurator.configure("log4j.xml");
	
  @BeforeClass(alwaysRun = true)
	public void init() throws Exception {
	  	
	    DOMConfigurator.configure("log4j.xml");
		System.setProperty("webdriver.chrome.driver","D:\\harmonycoreifusion\\src\\test\\resources\\Drivers\\chrome\\chromedriver_win32\\chromedriver.exe");
		driver= new ChromeDriver();
		logger.warn("Browser opened");
		driver.get("https://www.google.com");
		logger.info("URL entered");
  }
  @Test(priority=1)
  public void TestGoogle() {
	  //PropertyConfigurator.configure("D:\\harmonycoreifusion\\src\\main\\java\\com\\innominds\\team\\loggers\\log4j.properties");
	  
	  logger.info("Navigated to google");
	  logger.debug("debugg -- google");
	  logger.info("Info ---- google");
	  logger.error("error ---- google");
	  
  }
  
  @Test(priority=2)
  public void SearchItemInLog() {
	  String item = is.SearchItemInLogFile(logfile, "opened");
	  System.out.println("Searched item is ********************* "+ item);
  }
  @AfterClass(alwaysRun = true)
	public void tearDown() {

		driver.quit();
		logger.warn("Browser closed");
	}
}
