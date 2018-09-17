package com.innominds.team.driverinit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.innominds.team.frameworkengine.Constants;
import com.innominds.team.frameworkengine.WaitForPage;
import com.innominds.team.utils.PropertyFileUtils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

/*** The Class DeviceAppManger. */

@SuppressWarnings("rawtypes")
public class DeviceAppManger {
    DesiredCapabilities capabilities;
    AndroidDriver androidDriver;

    /**
     * Instantiates a new device app manger.
     */
    public DeviceAppManger() {
        capabilities = new DesiredCapabilities();
    }

    /**
     * Gets the device driver.
     *
     * @return the device driver
     */
    public AppiumDriver getDeviceDriver() {
        return androidDriver;
    }

    /**
     * Install app.
     *
     * @param env the env
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public void installApp(String env) throws IOException, InterruptedException {
        System.out.println("APK Installation Started..........");
        if (env.equalsIgnoreCase("android") || env.equalsIgnoreCase("atid")) {
            File file = new File(Constants.APKs_DRIVER_PATH + Constants.APK_FILE);
            try {
                String[] commands = new String[6];
                commands[0] = "cmd.exe";
                commands[1] = "/C";
                commands[2] = "start";
                commands[3] = "adb";
                commands[4] = "install";
                commands[5] = file.getAbsolutePath();
                Process p1 = Runtime.getRuntime().exec(commands);
                p1.waitFor();
                WaitForPage.implicitWait();
                WaitForPage.waitForElementtoLoad();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        WaitForPage.implicitWait();
        System.out.println("APK Installation completed.................");
    }

    /**
     * Lauch device driver.
     *
     * @param env the env
     * @return the android driver
     * @throws MalformedURLException the malformed URL exception
     * @throws FileNotFoundException
     */
    public AppiumDriver lauchDeviceDriver(String env, String browser) throws MalformedURLException, FileNotFoundException {
        androidDriver = new AndroidDriver(new URL(PropertyFileUtils.getPropValuesFromEnvConfig("appiumServerUrl", env)), capabilities);
        WaitForPage.implicitWait();
        System.out.println("App Opened Successfully------------");
        return androidDriver;
    }

    /**
     * Checks if is app installed.
     *
     * @return true, if is app installed
     * @throws FileNotFoundException
     */
    public boolean isAppInstalled(String mobEnv) throws FileNotFoundException {
        Boolean app = androidDriver.isAppInstalled(PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv));
        System.out.println("======isAppInstalled : " + app);
        return app;
    }

    /**
     * Sets the android capabilities.
     * 
     * @throws MalformedURLException
     * 
     * @throws FileNotFoundException
     */
    public AndroidDriver setCapabilitiesForWord() throws MalformedURLException {
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "TA93303TQW");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("paltform", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("appPackage", "com.microsoft.office.word");
        capabilities.setCapability("appActivity", "com.microsoft.office.apphost.LaunchActivity");
        androidDriver = new AndroidDriver(new URL(Constants.APPIUM_SERVER_URL), capabilities);
        WaitForPage.implicitWait();
        return androidDriver;
    }

    public AndroidDriver setCapabilitiesForExcel() throws MalformedURLException {
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "TA93303TQW");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("appPackage", "com.microsoft.office.excel");
        capabilities.setCapability("appActivity", "com.microsoft.office.apphost.LaunchActivity");
        androidDriver = new AndroidDriver(new URL(Constants.APPIUM_SERVER_URL), capabilities);
        WaitForPage.implicitWait();
        return androidDriver;

    }

    public AndroidDriver setCapabilitiesForOneNote() throws MalformedURLException {
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("deviceName", "TA93303TQW");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "5.1");
        capabilities.setCapability("appPackage", "com.microsoft.office.onenote");
        capabilities.setCapability("appActivity", "com.microsoft.office.onenote.ui.navigation.ONMNavigationActivity");
        androidDriver = new AndroidDriver(new URL(Constants.APPIUM_SERVER_URL), capabilities);
        WaitForPage.implicitWait();
        return androidDriver;

    }

    /**
     * Uninstall app.
     *
     * @param env the env
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public void uninstallApp(String mobEnv) throws IOException, InterruptedException {
        if (mobEnv.equalsIgnoreCase("android")) {
            try {
                String[] commands = new String[6];
                commands[0] = "cmd.exe";
                commands[1] = "/C";
                commands[2] = "start";
                commands[3] = "adb";
                commands[4] = "uninstall";
                commands[5] = Constants.APK_PACKAGE;
                Process p1 = Runtime.getRuntime().exec(commands);
                p1.waitFor();
            } catch (Exception e) {
                System.err.println(e);
            }
        } else if (mobEnv.equalsIgnoreCase("ios")) {
            androidDriver.removeApp(PropertyFileUtils.getPropValuesFromEnvConfig("bundleId", mobEnv));
        }
        System.out.println("App Uninstalled SUCCESSFULLY-------------");
    }
}
