package com.innominds.team.frameworkengine;

import java.io.File;

/**
 * The Class Constants.
 *
 * @author Chaya Venkateswarlu, Kiran Kumar Cherukuri
 */

public class Constants {

	/*
	 * ===================== Project Resources folder
	 * path=======================
	 */

	public static final String PROJ_RESOURCE_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "test" + File.separator + "resources" + File.separator;
	public static final String ENVIRONMENT_PROPERTIES_PATH = PROJ_RESOURCE_PATH + "Properties" + File.separator
			+ "Environments" + File.separator;
	public static final String PROPERTIES_PATH = PROJ_RESOURCE_PATH + "Properties" + File.separator;

	/*
	 * ===================== Implicit & Explicit timeout=======================
	 */

	// Seconds
	public static final int IMPLICIT_TIMEOUT = 30;
	public static final int EXPLICIT_TIMEOUT = 60;
	public static final int THREAD_SLEEP = 500;

	/* =====================For Web========================== */
	// Test Suite File Path
	public static final String TESTSUITE_FILE_PATH = PROJ_RESOURCE_PATH + "TestSuites" + File.separator
			+ "WebSuiteRunner.xls";
	public static final String MOBILE_TESTSUITE_FILE_PATH = PROJ_RESOURCE_PATH + "TestSuites" + File.separator
			+ "MobileSuiteRunner.xls";
	public static final String TESTSUITE_FILE_SHEETNAME = "TestSuites";
	public static final String SCREENSHOT_FOLDER_PATH = PROJ_RESOURCE_PATH + "TestResults" + File.separator
			+ "Screenshots";
	public static final String REPORT_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "Reports";
	public static final String TESTDATA_FILE_PATH = PROJ_RESOURCE_PATH + "TestData" + File.separator;
	public static final String RUNTIME_DATA = ENVIRONMENT_PROPERTIES_PATH+"RuntimeData.properties";
	
	public static final String API_PROPERTIES_FILE = PROJ_RESOURCE_PATH+"APIs"+File.separator+"api.properties";
	public static final String API_RUNTIME_DATA_PROPERTIES_FILE = PROJ_RESOURCE_PATH+"APIs"+File.separator+"apiRuntimeData.properties";
	public static final String WEB_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "web.properties";
	public static final String PYTHONJYTHON_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "PythonJython.properties";
	public static final String CUCUMBER_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "cucumber.properties";
	/*
	 * =====================Reports/Extent Config/Attachments File
	 * path==========================
	 */

	// Reports download location for our purpose (desired capabilities)
	public static final String APP_FILE_DEFAULT_DOWNLOAD_LOCATION = System.getProperty("user.dir") +"C:\\ReportDownloads";

	// Extent Config file path
	public static final String EXTENT_CONFIG_PATH = PROJ_RESOURCE_PATH + "ExtentConfig" + File.separator
			+ "extent-config.xml";

	// Reports file path
	public static final String REPORTS_FILE_PATH = System.getProperty("user.dir") + File.separator + "Reports"
			+ File.separator + "Web_Automation_Reports.html";
	// Attachments file path
	public static final String ATTACHMENTS_FILE_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "Attachments" + File.separator;

	/* ===================== Drivers file path========================== */

	public static final String CHROME_DRIVER_WIN_32_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator
			+ "chrome" + File.separator + "chromedriver_win32" + File.separator + "chromedriver.exe";
	public static final String CHROME_DRIVER_LINUX_32_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator
			+ "chrome" + File.separator + "chromedriver_linux32" + File.separator + "chromedriver.exe";
	public static final String CHROME_DRIVER_LINUX_64_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator
			+ "chrome" + File.separator + "chromedriver_linux64" + File.separator + "chromedriver.exe";
	public static final String CHROME_DRIVER_MAC_32_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator
			+ "chrome" + File.separator + "chromedriver_mac32" + File.separator + "chromedriver.exe";
	public static final String FIREFOX_DRIVER_32_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "firefox"
			+ File.separator + "firefox_32" + File.separator + "geckodriver.exe";
	public static final String FIREFOX_DRIVER_64_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "firefox"
			+ File.separator + "firefox_64" + File.separator + "geckodriver.exe";
	public static final String IE_DRIVER_32_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "iexplore"
			+ File.separator + "IEDriverServer_win_32.exe";
	public static final String IE_DRIVER_64_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "iexplore"
			+ File.separator + "IEDriverServer_win_64.exe";
	public static final String IE_MicrosoftWebDriver_EXE_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator
			+ "iexplore" + File.separator + "MicrosoftWebDriver.exe";

	/* =====================For Mobile App Path========================== */
	public static final String IPAs_INSTALL_FILE = "ontrack-2.3.6.1.ipa";
	public static final String APK_FILE = "OnTrack-2.3.5.2.apk";
	public static final String APK_PACKAGE = "com.hilti.mobile.ontrack";
	public static final String APKs_DRIVER_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "APKs"
			+ File.separator;
	public static final String IPAs_DRIVER_PATH = PROJ_RESOURCE_PATH + "Drivers" + File.separator + "IPAs"
			+ File.separator;

	public static final String ANDROID_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "android.properties";
	public static final String IOS_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "ios.properties";

	public static final String WIN_APPIUM_DIR = "D:/n";
	public static final String WIN_DRIVER_EXECUTABLE = WIN_APPIUM_DIR + File.separator
			+ "node.exe";
	public static final String WIN_APPIUM_JS_FILE = WIN_APPIUM_DIR +File.separator
			+ "node_modules" + File.separator + "appium" + File.separator + "build" + File.separator +"lib" + File.separator + "appium.js";

	public static final String MAC_APPIUM_NODE = "/Applications/Appium.app/Contents/Resources/node/bin/node";
	public static final String MAC_APPIUM_JS = "/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js";

	/* ===================== Repository paths========================== */

	public static final String ANDROID_OBJECT_REPO_FILE_PATH = PROJ_RESOURCE_PATH + "ObjectRepo" + File.separator
			+ "AndroidOR" + File.separator;
	public static final String IOS_OBJECT_REPO_FILE_PATH = PROJ_RESOURCE_PATH + "ObjectRepo" + File.separator + "IOSOR"
			+ File.separator;
	public static final String WEB_OBJECT_REPO_FILE_PATH = PROJ_RESOURCE_PATH + "ObjectRepo" + File.separator + "WebOR"
			+ File.separator;
	public static final String EXCEL_REPORT_FILE_PATH = PROJ_RESOURCE_PATH + "TestSuites";

	/*
	 * =====================Database Configuration
	 * file==========================
	 */

	public static final String DB_PROPERTIES_FILE = "DBUtil.properties";
	
	/* =====================Features Properties File========================== */

	public static final String FEATURES_PROPERTIES_FILE = "Features.properties";

	/* =====================Jira File========================== */

	public static final String JIRA_PROPERTIES_FILE = "jira.properties";

	/* =====================Docker Properties========================== */

	public static final String DOCKER_PROPERTIES_FILE = "docker.properties";
	
	/* =====================Dashboard Properties========================== */

	public static final String DASHBOARD_PROPERTIES_FILE = "dashboard.properties";

	/* =====================Docker Compose File========================== */

	public static final String DOCKER_COMPOSE_FILE = "docker-compose.yml";

	/* =====================Send mail File========================== */

	public static final String SENDMAIL_PROPERTIES_FILE = "sendmail.properties";

	/* =====================Email File========================== */

	public static final String EMAIL_PROPERTIES_FILE = ENVIRONMENT_PROPERTIES_PATH + "email.properties";

	/* =====================HTTP Status Codes========================== */
	public static final String STATUS_OK = "200";
	public static final String STATUS_NOT_FOUND = "303";
	public static final String STATUS_BAD_REQUEST = "400";
	public static final String STATUS_UNAUTHORIZED = "401";
	public static final String STATUS_FORBIDDEN = "403";
	public static final String STATUS_RESOURCE_NOTFOUND = "404";
	public static final String STATUS_TIMEOUT = "408";
	public static final String STATUS_SERVER_ERROR = "500";
	public static final String STATUS_INVALID_RESPONSE = "502";
	public static final String STATUS_SERVICE_UNAVAILABLE = "503";
	public static final String STATUS_UNSUPPORTED_HTTPVERSION = "505";
	
	public static final String APPIUM_SERVER_URL = "http://0.0.0.0:4723/wd/hub";

}
