package com.innominds.team.driverinit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.xml.sax.SAXException;

import com.innominds.team.frameworkengine.CommonUtils;
import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.frameworkengine.WaitForPage;
import com.innominds.team.utils.CommandRunner;
import com.innominds.team.utils.PropertyFileUtils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

/**
 * The Class AppiumServerManager.
 */
public class AppiumServerManager {
	
	public static boolean whitetestscompleted = false;
	public static String whitepath;
	public static boolean appiumStarted = false;
	
	public void runWhiteTests() {
		if(!whitetestscompleted) {
			try {
				PropertyFileUtils props = new PropertyFileUtils(
						CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
				String isWhite = props.getDataFromPropertyFile("TestStackWhite");
				if (Boolean.parseBoolean(isWhite)) {
					whitetestscompleted = runWhiteTestsAndIntegrate();
					System.out.println("White Execution completed");
					Thread.sleep(2000);
				}
					
			} catch (TransformerFactoryConfigurationError | ParserConfigurationException | SAXException | IOException
					| TransformerException | InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private boolean runWhiteTestsAndIntegrate() throws TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException, TransformerException, InterruptedException {		
		whitepath = Constants.ENVIRONMENT_PROPERTIES_PATH+File.separator+"white"+File.separator;
		PropertyFileUtils props1 = new PropertyFileUtils(
				CommonUtils.getFilePath(Constants.ENVIRONMENT_PROPERTIES_PATH, Constants.FEATURES_PROPERTIES_FILE));
		String isWhiteIntegration = props1.getDataFromPropertyFile("TestStackWhite");
		if (Boolean.parseBoolean(isWhiteIntegration)) {			
			String nunitCmd = whitepath+"nunit3/nunit3-console.exe";
			String csproj = whitepath+"Demo/NUnit.OfficeAppsTests/NUnit.OfficeAppsTests/NUnit.OfficeAppsTests.csproj";
			String resultArg = "--result:"+whitepath+"Result.xml";
			CommandRunner c1 = new CommandRunner(nunitCmd, csproj, resultArg);
            Thread t1 = new Thread(c1);
            t1.start();
            long duration = System.currentTimeMillis();
            while (t1.isAlive()) {
            	//Max duration to check if thread has completed (30 minutes)
            	long MAX_DURATION = duration+1800000;
            	if (duration <= MAX_DURATION) {
            		duration = System.currentTimeMillis();
            		//Sleep for a minute and check again
            		Thread.sleep(60000);
            	}
            	else
            		break;
            }
		}
		whitetestscompleted = true;
		return whitetestscompleted;
	}

	AppiumDriverLocalService service = null;

	/**
	 * Instantiates a new appium server manager.
	 */
	public AppiumServerManager() {
		
		String osName = System.getProperty("os.name");
		if (osName.contains("Windows")) {
			service = AppiumDriverLocalService.buildDefaultService();
//			service = AppiumDriverLocalService.buildService(
//					new AppiumServiceBuilder().usingDriverExecutable(new File(Constants.WIN_DRIVER_EXECUTABLE))
//							.withAppiumJS(new File(Constants.WIN_APPIUM_JS_FILE)));
		} else if (osName.contains("Mac")) {
			service = AppiumDriverLocalService
					.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(Constants.MAC_APPIUM_NODE))
							.withAppiumJS(new File(Constants.MAC_APPIUM_JS)));
		} else {
			Assert.fail("Starting appium is not supporting the current OS.");
		}
	}

	/**
	 * Start appium server.
	 *
	 * @param env
	 *            the env
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws ExecuteException
	 *             the execute exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void startAppiumServer(String env) throws InterruptedException, ExecuteException, IOException {
		appiumStarted = true;
		runWhiteTests();
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			service.start();
		} else if (env.equalsIgnoreCase("ios")) {
			CommandLine command = new CommandLine(Constants.MAC_APPIUM_NODE);
			command.addArgument(Constants.MAC_APPIUM_JS, false);
			command.addArgument("--address", false);
			command.addArgument("0.0.0.0");
			command.addArgument("--port", false);
			command.addArgument("4723");
			command.addArgument("--full-reset", true);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
		}
		WaitForPage.waitForPagetoLoad();
		System.out.println("\n Appium Server Started-------------");
	}

	/**
	 * Stop appium server.
	 *
	 * @param env
	 *            the env
	 */
	public void stopAppiumServer(String env) {
		if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
			service.stop();
		} else if (env.equalsIgnoreCase("ios")) {
			String[] command = { "/usr/bin/killall", "-KILL", "node" };
			try {
				Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WaitForPage.waitForPagetoLoad();
		System.out.println("Appium Server Stopped---------------");
	}
}
