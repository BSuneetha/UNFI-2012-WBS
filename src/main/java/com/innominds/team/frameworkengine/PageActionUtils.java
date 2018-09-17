/**
 * 
 */
package com.innominds.team.frameworkengine;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.innominds.team.utils.PropertyFileUtils;

import io.appium.java_client.AppiumDriver;

/**
 * The Class with wrapper function for webdriver api's.
 *
 * @author paggrawal, Chaya Venkateswarlu, Praveen Gaddam and Kiran Kumar
 *         Cherukuri
 */
public class PageActionUtils {

	public void initAppiumDriver(AppiumDriver driver) throws FileNotFoundException, IOException {
        this.appiumDriver = driver;
    }
	
	private By by;
	public WebElement element;
	public List<WebElement> elements;

	public WebDriverWait wait;
	public static Logger logger = LogManager.getLogger(PageActionUtils.class.getName());
	AppiumDriver<WebElement> appiumDriver;

	/**
	 * The Enum AttributeName.
	 */
	private enum AttributeName {

		unKnown, name, id, xpath, css, classname, linktext, partiallinkText, tagname, empty
	}

	/**
	 * Gets getBy.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return By
	 */
	protected By getBy(Map<String, Map<String, String>> map2, String keyWord, WebDriver driver) {
		Map<String, String> a = map2.get(keyWord);
		
		try {
			wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);

			Map.Entry<String, String> entry = a.entrySet().iterator().next();
			String propType = entry.getKey().toLowerCase().trim();
			String propValue = entry.getValue().trim();

			AttributeName attributeName = AttributeName.unKnown;
			attributeName = AttributeName.valueOf(propType.trim());

			if (propType != null && propValue != null) {
				switch (attributeName) {
				case id: 
					by = By.id(propValue);
					break;
				
				case name: 
					by = By.name(propValue);
					break;
				
				case xpath: 
					by = By.xpath(propValue);
					break;
				
				case classname: 
					by = By.className(propValue);
					break;
				
				case css: 
					by = By.cssSelector(propValue);
					break;
				
				case linktext: 
					by = By.linkText(propValue);
					break;
				
				case partiallinkText: 
					by = By.partialLinkText(propValue);
					break;
				
				case tagname: 
					by = By.tagName(propValue);
					break;
				
				default:
					Reporter.log("No locator type found....");
					break;

				}
			} else {
				logger.info("Property type is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return by;
	}

	/**
	 * Gets the web element.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web element
	 * @throws Exception
	 */

	public WebElement getWebElement(Map<String, Map<String, String>> map2, String keyWord, WebDriver driver)
			throws Exception {
		by = null;
		try {
			by = getBy(map2, keyWord, driver);

			if (by != null) {

				// if
				// (wait.until(ExpectedConditions.presenceOfElementLocated(by))
				// != null)
				element = driver.findElement(by);
			} else {
				//System.out.println("Properties not found for element locator");
			}

		} catch (Exception e) {

			logger.error(e);
			logger.error("Property type is empty or some other error");
			//Close driver if element not found
			driver.quit();
			throw new Exception(e.getMessage());
		}
		return element;
	}
	
	//Method added by Chaitanya
	public void waitForElementPresent(WebElement ele, long timeout) throws InterruptedException {
		long rpt = timeout/5;
		for (int i=0; i<5; i++) {
			if (!ele.isDisplayed() && !ele.isEnabled()) {
				Thread.sleep(rpt);
			} else
				break;
		}
	}
	
	

	public WebElement getNavigationWebElementClickable(Map<String, Map<String, String>> map2, String keyWord,
			WebDriver driver) throws Exception {
		try {
			by = getBy(map2, keyWord, driver);

			if (by != null) {
				element = driver.findElement(by);
			} else {
				//System.out.println("Properties not found for element locator");
			}

		} catch (Exception e) {

			logger.error(e);
			logger.error("Property type is empty or some other error");
			throw new Exception(e.getMessage());
		}
		return element;
	}

	/**
	 * Gets the web elements.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web elements
	 */

	public List<WebElement> getWebElements(Map<String, Map<String, String>> map2, String keyWord, WebDriver driver) {
		try {
			by = getBy(map2, keyWord, driver);

			if (by != null) {
				elements = driver.findElements(by);
			} else {
				//System.out.println("Properties not found for element locator");
			}

		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return elements;
	}

	/**
	 * Switch to popup window.
	 * 
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void switchToWindowHandle(WebDriver driver) throws FileNotFoundException, IOException, InterruptedException {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String currentWindow : allWindows) {
				driver.switchTo().window(currentWindow);
			}
		} catch (Exception ex) {
			throw new RuntimeException("Failed: to switch to new Window " + ex.getMessage());
		}
	}
	
	/**
	 * Method added by Sunitha
	 */
	public void switchToWindowHandleRemoveOld(String oldWindow, WebDriver driver) {
		String currentWindow = null;
	     for (String handle : driver.getWindowHandles()) {
	      currentWindow = handle;
	       if (handle.equals(oldWindow))
	       {
	         driver.switchTo().window(handle);
	         driver.close();
	       }
	     }
	     driver.switchTo().window(currentWindow);
	}

	/**
	 * Maximize window.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void maxmizeWindow(WebDriver driver) throws Exception {
		try {
			driver.manage().window().maximize();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to maximize window " + e.getMessage());
		}
	}
	
	//Method added by Sunitha
	public boolean sortingByInteger(List<WebElement> elements, WebDriver driver) {
		ArrayList<Integer> obtainedList = new ArrayList<>();

		for (WebElement we : elements) {
			obtainedList.add(Integer.parseInt(we.getText()));
		}
		ArrayList<Integer> sortedList = new ArrayList<>();
		for (int s : obtainedList) {
			sortedList.add(s);
		}
		Collections.sort(sortedList);
		if (obtainedList.equals(sortedList))
			return true;
		else
			return false;
	}
	
	//Method added by Sunitha
	public boolean sortingByString(List<WebElement> elements, WebDriver driver) {
		ArrayList<String> obtainedList = new ArrayList<>();

		for (WebElement we : elements) {
			obtainedList.add(we.getText());
		}
		ArrayList<String> sortedList = new ArrayList<>();
		for (String s : obtainedList) {
			sortedList.add(s);
		}
		Collections.sort(sortedList, String.CASE_INSENSITIVE_ORDER);
		if (obtainedList.equals(sortedList))
			return true;
		else
			return false;
	}

	//Method added by Sunitha
	public boolean sortingByDouble(List<WebElement> elements, WebDriver driver) {
		ArrayList<Double> obtainedList = new ArrayList<>();

		for (WebElement we : elements) {
			obtainedList.add(Double.parseDouble(we.getText()));
		}
		ArrayList<Double> sortedList = new ArrayList<>();
		for (Double s : obtainedList) {
			sortedList.add(s);
		}
		Collections.sort(sortedList);
		if (obtainedList.equals(sortedList))
			return true;
		else
			return false;
	}

	/**
	 * Get Page Source.
	 * 
	 * @throws Exception
	 */
	public String getPageSource(WebDriver driver) throws Exception {
		String pageSrc = null;
		try {
			pageSrc = driver.getPageSource();

		} catch (Exception e) {
			throw new RuntimeException("Failed: to retrive Page  Source " + e.getMessage());
		}
		return pageSrc;
	}

	/**
	 * Switch to Frame using Name.
	 *
	 * @param frame
	 *            the frame
	 * @throws Exception
	 *             the exception
	 */
	public void switchToFrameByName(String frame, WebDriver driver) throws Exception {
		try {
			driver.switchTo().frame(frame);

		} catch (Exception e) {
			throw new RuntimeException("Failed: to switch to Frame by Name" + e.getMessage());
		}
	}

	/**
	 * Switch to Frame using Name.
	 *
	 * @param id
	 *            the id
	 * @throws Exception
	 *             the exception
	 */
	public void switchToFrameByID(int id, WebDriver driver) throws Exception {
		try {
			driver.switchTo().frame(id);

		} catch (Exception e) {
			throw new RuntimeException("Failed: to switch to Frame by ID " + e.getMessage());
		}
	}

	/**
	 * Switch to Frame using Element.
	 *
	 * @param element
	 *            the element
	 * @throws Exception
	 *             the exception
	 */
	public void switchToFrameByElement(WebElement element, WebDriver driver) throws Exception {
		try {
			driver.switchTo().frame(element);

		} catch (Exception e) {
			throw new RuntimeException("Failed: to switch to Frame by Element " + e.getMessage());
		}
	}

	/**
	 * Retrive Focused window URL.
	 * 
	 * @throws Exception
	 */
	public String getCurrentURL(WebDriver driver) throws Exception {
		String url = null;
		try {
			url = driver.getCurrentUrl();

		} catch (Exception e) {
			throw new RuntimeException("Failed: to retrive current URL " + e.getMessage());
		}
		return url;
	}

	/**
	 * Gets the dynamic xpath by locator.
	 *
	 * @param value
	 *            the value
	 * @param columnname
	 *            the columnname
	 * @return the dynamic xpath by locator
	 */

	public By getDynamicXpathByLocator(String value, String columnname) {
		By locator = null;
		if (columnname.equalsIgnoreCase("Status")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value " + value + "']+td");
		} else if (columnname.equalsIgnoreCase("Remarks")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value " + value + "']+td+td");
		} else if (columnname.equalsIgnoreCase("Report")) {
			locator = By.cssSelector("[aria-label='Column File Name, Value " + value + "']+td+td+td>div");
		} else if (columnname.equalsIgnoreCase("Name")) {
			locator = By.cssSelector("td[aria-label='Column " + columnname + ", Value " + value + "']");
		} else if (columnname.equalsIgnoreCase("ID")) {
			locator = By.cssSelector("td[aria-label='Column " + columnname + ", Value " + value + "']");
		}
		return locator;
	}

	/**
	 * Gets the web element.
	 *
	 * @param a
	 *            the a
	 * @return the web element
	 */
	public WebElement getMobileElement(Map<String, String> a) {
		WebElement we = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.name(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.xpath(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("linktext")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.linkText(a.get("PropertyValue")));

				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					Thread.sleep(500);
					we = appiumDriver.findElement(By.cssSelector(a.get("PropertyValue")));
				}

			} else {
				logger.info("Property type is empty");
			}

		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return we;
	}

	/**
	 * Gets the web element.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web element
	 */
	public WebElement getMobileElement(Map<String, Map<String, String>> map2, String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		WebElement we = getMobileElement(map3);
		return we;
	}

	/**
	 * Gets the web elements.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web elements
	 */
	public List<WebElement> getMobileElements(Map<String, Map<String, String>> map2, String keyWord) {
		Map<String, String> map3 = map2.get(keyWord);
		List<WebElement> list = getMobileElements(map3);
		return list;
	}

	/**
	 * Gets the web elements.
	 *
	 * @param a
	 *            the a
	 * @return the web elements
	 */
	public List<WebElement> getMobileElements(Map<String, String> a) {
		List<WebElement> list = null;
		try {
			if (!a.get("PropertyType").isEmpty()) {
				if (a.get("PropertyType").toLowerCase().equals("id")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.id(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("name")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.name(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("xpath")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.xpath(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("linktext")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.linkText(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("css")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.cssSelector(a.get("PropertyValue")));
				} else if (a.get("PropertyType").toLowerCase().equals("class")) {
					Thread.sleep(500);
					list = appiumDriver.findElements(By.className(a.get("PropertyValue")));
				}
			} else {
				logger.info("Property type is empty");
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("Property type is empty or some other error");
		}
		return list;
	}

	/**
	 * Waits until page title is displayed.
	 *
	 * @param title
	 *            the title
	 * @return true, if successful
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public boolean assertPageTitle(String title, WebDriver driver) throws InterruptedException {
		boolean titleVal = false;
		try {
			String pageTitle = driver.getTitle();
			titleVal = pageTitle.trim().contains(title);
		} catch (Exception ex) {
			throw new RuntimeException("Failed: to get page title " + ex.getMessage());

		}
		return titleVal;
	}

	/**
	 * Get element using partialLinkText.
	 *
	 * @param innerText
	 *            the inner text
	 * @return the web element
	 */
	public WebElement findElementByPartialLinkText(String innerText, WebDriver driver) {
		return driver.findElement(By.partialLinkText(innerText));
	}

	/**
	 * Search for elements under given tag name, return first match.
	 *
	 * @param parentElement
	 *            the parent element
	 * @param elementTagName
	 *            the element tag name
	 * @return the element by tag name
	 * @throws ElementNotVisibleException
	 *             the element not visible exception
	 */
	public WebElement getElementByTagName(WebElement parentElement, String elementTagName)
			throws ElementNotVisibleException {
		List<WebElement> tmpList = parentElement.findElements(By.tagName(elementTagName));
		Iterator<WebElement> itr = tmpList.iterator();
		while (itr.hasNext()) {
			WebElement tmpEle = itr.next();
			if (tmpEle.isDisplayed()) {
				return tmpEle;
			}
		}
		return null;
	}

	/**
	 * Double click element.
	 *
	 * @param we
	 *            the we
	 * @throws NoSuchElementException
	 *             the no such element exception
	 */
	public void doubleClickElement(WebElement we, WebDriver driver) throws NoSuchElementException {
		new Actions(driver).doubleClick(we).perform();
		logger.info("Double clicked on webelement");

	}

	/**
	 * Mouse hover.
	 *
	 * @param we
	 *            the we
	 * @throws NoSuchElementException
	 *             the no such element exception
	 */
	public void mouseHover(WebElement we, WebDriver driver) throws NoSuchElementException {
		new Actions(driver).moveToElement(we).build().perform();
		logger.info("Hover mouse on webelement");

	}

	/**
	 * Right click.
	 *
	 * @param element
	 *            the element
	 * @throws NoSuchElementException
	 *             the no such element exception
	 */
	public void rightClick(WebElement element, WebDriver driver) throws NoSuchElementException {
		new Actions(driver).contextClick(element).build().perform();
		logger.info("Sucessfully Right clicked on the element");
	}

	/**
	 * Check checkbox.
	 *
	 * @param element
	 *            the element
	 */
	public void checkCheckbox(WebElement element) {
		if (!element.isSelected()) {
			element.click();
		}
	}

	/**
	 * Click.
	 *
	 * @param element
	 *            the element
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void click(WebElement element) throws IllegalArgumentException {
		try {
			element.click();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to click WebElement " + e.getMessage());
		}

	}

	/**
	 * Enter text.
	 *
	 * @param element
	 *            the element
	 * @param text
	 *            the text
	 */
	public void enterText(WebElement element, String text) {
		try {
			element.clear();
			element.sendKeys(text.trim());
		} catch (Exception e) {
			throw new RuntimeException("Failed: to enter input text " + e.getMessage());
		}

	}

	/**
	 * Clear text.
	 *
	 * @param element
	 *            the element
	 */
	public void clearText(WebElement element) {
		try {
			element.clear();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to enter input text " + e.getMessage());
		}

	}

	/**
	 * Wait alert and close.
	 *
	 * @param isAccept
	 *            the is accept
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 */
	public void waitAlertAndClose(boolean isAccept, WebDriver driver) throws IllegalArgumentException {
		try {
			new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
			Alert al = driver.switchTo().alert();
			if (isAccept) {
				al.accept();
			} else {
				al.dismiss();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get alert " + e.getMessage());
		}

	}

	/**
	 * Gets the text from JS alert.
	 *
	 * @param isAccept
	 *            the is accept
	 * @return the text from JS alert
	 */
	public String getTextFromJSAlert(boolean isAccept, WebDriver driver) {
		String alertText = "";
		try {
			new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
			Alert al = driver.switchTo().alert();
			alertText = al.getText().trim();
			if (isAccept) {
				al.accept();
			} else {
				al.dismiss();
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get text from Alert " + e.getMessage());
		}
		return alertText;
	}

	/**
	 * Gets the value.
	 *
	 * @param element
	 *            the element
	 * @return the value
	 */
	public String getValue(WebElement element) {
		return element.getAttribute("value");
	}

	/**
	 * Returns true if element is visible, else false.
	 *
	 * @param element
	 *            the element
	 * @return true, if is element displayed
	 * @throws ElementNotVisibleException
	 *             the element not visible exception
	 */
	public boolean isElementDisplayed(WebElement element) throws ElementNotVisibleException {
		boolean isVis = false;
		try {
			isVis = element.isDisplayed();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to retrive visibility of element " + e.getMessage());
		}
		return isVis;
	}

	/**
	 * Returns true if element is enabled, else false.
	 *
	 * @param element
	 *            the element
	 * @return true, if is element enabled
	 * @throws ElementNotVisibleException
	 *             the element not visible exception
	 */
	public boolean isElementEnabled(WebElement element) throws ElementNotVisibleException {
		boolean isEna = false;
		try {
			isEna = element.isEnabled();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to retrive enable of element " + e.getMessage());
		}
		return isEna;

	}

	/**
	 * Validate string text in object contains a substring.
	 *
	 * @param element
	 *            the element
	 * @param subString
	 *            the sub string
	 * @throws ElementNotVisibleException
	 *             the element not visible exception
	 * @throws NoSuchElementException
	 *             the no such element exception
	 */
	public void verifyTextPresent(WebElement element, String subString)
			throws ElementNotVisibleException, NoSuchElementException {
		String elementText;
		if (element.getText() != null) {
			elementText = element.getText();
		} else {
			elementText = element.getTagName();
		}
		if (!elementText.contains(subString)) {
			Assert.fail("Text not present in the message string");
			logger.info(subString + "Not present");
		}
	}

	/**
	 * Drag and drop.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param destinationElement
	 *            the destination element
	 */
	public void jsDragAndDrop(WebElement sourceElement, WebElement destinationElement,WebDriver driver) 
	{
		try 
		{
			String xto=Integer.toString(destinationElement.getLocation().x);
		    String yto=Integer.toString(destinationElement.getLocation().y);
		    ((JavascriptExecutor)driver).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; " +
		    "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
		    sourceElement,xto,yto);
		} catch (Exception e) {
			logger.info("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}

	/**
	 * Drag and drop.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param destinationElement
	 *            the destination element
	 */
	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement,WebDriver driver) {
		try 
		{
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) 
			{
				new Actions(driver).dragAndDrop(sourceElement, destinationElement).build().perform();
			} else {
				logger.info("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			logger.info("Element with " + sourceElement + "or" + destinationElement
					+ "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.info("Element " + sourceElement + "or" + destinationElement + " was not found " + e.getStackTrace());
		} catch (Exception e) {
			logger.info("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}
	
	/**
	 * moveToElement.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param destinationElement
	 *            the destination element
	 */
	public void moveToElement(WebElement sourceElement, WebElement destinationElement,WebDriver driver) {
		try 
		{
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) 
			{
				new Actions(driver).click(sourceElement).moveToElement(destinationElement).click().build().perform();
			} else {
				logger.info("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			logger.info("Element with " + sourceElement + "or" + destinationElement
					+ "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.info("Element " + sourceElement + "or" + destinationElement + " was not found " + e.getStackTrace());
		} catch (Exception e) {
			logger.info("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}
	
	/**
	 * Drag and drop.
	 *
	 * @param sourceElement
	 *            the source element
	 * @param destinationElement
	 *            the destination element
	 */
	public void dragAndDropEx(WebElement sourceElement, WebElement destinationElement,WebDriver driver) {
		try 
		{
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) 
			{
				new Actions(driver).clickAndHold(sourceElement).moveToElement(destinationElement).release(destinationElement).build().perform();
				
				Point coordinates1 = sourceElement.getLocation();
				Point coordinates2 = destinationElement.getLocation();  
				Robot robot = new Robot();           
				robot.mouseMove(coordinates1.getX(), coordinates1.getY());
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseMove(coordinates2.getX(), coordinates2.getY());
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			} else {
				logger.info("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			logger.info("Element with " + sourceElement + "or" + destinationElement
					+ "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.info("Element " + sourceElement + "or" + destinationElement + " was not found " + e.getStackTrace());
		} catch (Exception e) {
			logger.info("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}

	/**
	 * Capture screenshot.
	 *
	 * @param filename
	 *            the filename
	 * @return the file
	 */
	public File captureScreenshot(String filename, WebDriver driver) {
		File file = null;
		try {
			String filePath = Constants.SCREENSHOT_FOLDER_PATH + File.separator + filename;
			File folder = new File(filePath);
			if (!folder.exists()) {
				boolean result = false;
				try {
					folder.mkdir();
					result = true;
				} catch (Exception e) {

				}
				if (result) {
					//System.out.println("Folder with name " + filename + " created");
				}
			}
			file = new File(folder + "//" + filename + ".jpg");
			if (file.exists()) {
				if(file.delete()) {
					File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, new File(file.toString()));
				//System.out.println("Snapahot has been taken for the Failed test case");
				}
			} else {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrFile, new File(file.toString()));
				//System.out.println("Snapahot has been taken for the Failed test case");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;

	}

	/**
	 * Js click.
	 *
	 * @param myWebElement
	 *            the my web element
	 */
	public void jsClick(WebElement myWebElement, WebDriver driver) {
		try {

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", myWebElement);
		} catch (Exception e) {
			throw new RuntimeException("Failed: to click the webelement " + e.getMessage());
		}
	}

	/**
	 * Js refresh browser.
	 */
	public void jsRefreshBrowser(WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("history.go(0)");
		} catch (Exception e) {
			throw new RuntimeException("Failed: to Refresh the browser " + e.getMessage());
		}
	}

	/**
	 * Js inner text.
	 *
	 * @return the string
	 */
	public String jsInnerText(WebDriver driver) {
		try {
			String sText = ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;")
					.toString();
			return sText;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get the inner text of the page" + e.getMessage());
		}
	}

	/**
	 * Js scroll window.
	 */
	public void jsScrollWindow(WebDriver driver) {
		try {
			// Vertical scroll - down by 150 pixels
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,150)");
		} catch (Exception e) {
			throw new RuntimeException("Failed: to scroll the window " + e.getMessage());
		}
	}

	/**
	 * Js drag and drop.
	 *
	 * @param dragElementFrom
	 *            the drag element from
	 * @param xTo
	 *            the x to
	 * @param yTo
	 *            the y to
	 */
	public void jsDragAndDrop(WebElement dragElementFrom, WebDriver driver, int xTo, int yTo) {
		try {
			// To drag and drop element by 100 pixel offset In horizontal
			// direction X
			((JavascriptExecutor) driver).executeScript(
					"function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; "
							+ "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
					dragElementFrom, xTo, yTo);
		} catch (Exception e) {
			throw new RuntimeException("Failed: to drag and drop the element " + e.getMessage());
		}
	}

	/**
	 * Js get title.
	 *
	 * @return the string
	 */
	public String jsGetTitle(WebDriver driver) {
		try {
			String sText = ((JavascriptExecutor) driver).executeScript("return document.title;").toString();
			return sText;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get the title of the web page " + e.getMessage());
		}
	}

	/**
	 * Js enable element.
	 *
	 * @param element
	 *            the element
	 */
	public void jsEnableElement(WebElement element, WebDriver driver) {
		try {
			String js = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";
			((JavascriptExecutor) driver).executeScript(js, element);
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get the title of the web page " + e.getMessage());
		}
	}

	/**
	 * Js generate alert popup.
	 *
	 * @param alert_message
	 *            the alert message
	 */
	public void jsGenerateAlertPopup(String alert_message, WebDriver driver) {
		try {
			if (alert_message.isEmpty()) {
				alert_message = "Test alert";
			}
			((JavascriptExecutor) driver).executeScript("alert(alert_message);");
		} catch (Exception e) {
			throw new RuntimeException("Failed: to generate the alert popup " + e.getMessage());
		}
	}

	/**
	 * Navigate forward.
	 */
	public void navigateForward(WebDriver driver) {
		try {
			driver.navigate().forward();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to navigate forward to the next page " + e.getMessage());
		}
	}

	/**
	 * Navigate back.
	 */
	public void navigateBack(WebDriver driver) {
		try {
			driver.navigate().back();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to navigate back to the previous page " + e.getMessage());
		}
	}

	/**
	 * Refresh browser.
	 */
	public void refreshBrowser(WebDriver driver) {
		try {
			driver.navigate().refresh();

		} catch (Exception e) {
			throw new RuntimeException("Failed: to refresh to the page " + e.getMessage());
		}
	}

	/**
	 * Checks if is displayed.
	 *
	 * @param element
	 *            the element
	 * @param UserNameID
	 *            the user name ID
	 * @return true, if is displayed
	 */
	public boolean isDisplayed(WebElement element, String UserNameID) {
		try {
			boolean status = element.isDisplayed();
			return status;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to check if the element is displayed or not" + e.getMessage());
		}
	}

	/**
	 * Checks if is selected.
	 *
	 * @param element
	 *            the element
	 * @return true, if is selected
	 */
	public boolean isSelected(WebElement element) {
		boolean status = false;
		try {
			status = element.isSelected();

		} catch (Exception e) {
			throw new RuntimeException("Failed: to check if the element is selected or not" + e.getMessage());
		}
		return status;
	}

	/**
	 * Submit form.
	 *
	 * @param element
	 *            the element
	 */
	public void submitForm(WebElement element) {
		try {
			element.submit();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to submit the form" + e.getMessage());
		}

	}

	/**
	 * Gets the text.
	 *
	 * @param element
	 *            the element
	 * @return the text
	 */
	public String getText(WebElement element) {
		try {
			String linkText = element.getText();
			return linkText;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get inner text of the page" + e.getMessage());
		}
	}

	/**
	 * Select by visible text from list.
	 *
	 * @param elements
	 *            the elements
	 * @param text
	 *            the text
	 */
	public void selectByVisibleTextFromList(List<WebElement> elements, String text, WebDriver driver) {

		try {
			for (WebElement element : elements) {
				if (element.getText().trim().equals(text.trim())) {
					jsClick(element, driver);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to select the element by visible text From" + e.getMessage());
		}
	}

	/**
	 * De select all.
	 *
	 * @param element
	 *            the element
	 */
	public void deSelectAll(WebElement element) {

		try {
			Select listbox = new Select(element);
			listbox.deselectAll();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to de-select the elements" + e.getMessage());
		}
	}

	/**
	 * Checks if is multi select.
	 *
	 * @param element
	 *            the element
	 * @return true, if is multi select
	 */
	public boolean isMultiSelect(WebElement element) {
		boolean multiSelect = false;
		try {
			Select listbox = new Select(element);
			multiSelect = listbox.isMultiple();

		} catch (Exception e) {
			throw new RuntimeException("Failed: to get selected value as multi-select" + e.getMessage());
		}
		return multiSelect;
	}

	/**
	 * Select by visible text.
	 *
	 * @param element
	 *            the element
	 * @param text
	 *            the text
	 */
	public void selectByVisibleText(WebElement element, String text) {
		Select oSelect = null;
		try {
			oSelect = new Select(element);
			wait.until(ExpectedConditions.visibilityOf(element));
			Thread.sleep(Constants.THREAD_SLEEP);
			oSelect.selectByVisibleText(text.trim());
		} catch (Exception e) {
			throw new RuntimeException("Failed: to select the element by visible text" + e.getMessage());
		}
	}

	/**
	 * Select by index.
	 *
	 * @param element
	 *            the element
	 * @param indexid
	 *            the indexid
	 */
	public void selectByIndex(WebElement element, String indexid) {
		Select oSelect = null;
		boolean breakIt = true;
		try {
			while (breakIt) {
				try {
					Thread.sleep(Constants.THREAD_SLEEP);
					oSelect = new Select(element);
					oSelect.selectByIndex(Math.round(Float.parseFloat(indexid)));
					break;
				} catch (Exception e) {
					if (e.getMessage().contains("element is not attached")) {
						breakIt = false;
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to select the element by Index" + e.getMessage());
		}
	}

	/**
	 * Select by value.
	 *
	 * @param element
	 *            the element
	 * @param value
	 *            the value
	 * @return the select
	 */
	public Select selectByValue(WebElement element, String value) {
		try {
			Select oSelect = new Select(element);
			oSelect.selectByValue(value);
			return oSelect;
		} catch (Exception e) {
			throw new RuntimeException("Failed: to select the element by Value" + e.getMessage());
		}
	}

	/**
	 * Gets the all options.
	 *
	 * @param element
	 *            the element
	 * @return the all options
	 */
	@SuppressWarnings("null")
	public List<String> getAllOptions(WebElement element) {
		List<String> options = null;
		try {
			Select oSelect = new Select(element);
			List<WebElement> elementCount = oSelect.getOptions();
			int iSize = elementCount.size();

			for (int i = 0; i > iSize; i++) {
				options.add(elementCount.get(i).getText());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get the options for the selected tag" + e.getMessage());
		}
		return options;
	}

	/**
	 * Gets the selected option.
	 *
	 * @param element
	 *            the element
	 * @return the selected option
	 */
	public String getSelectedOption(WebElement element) {
		String selOption = "";
		try {
			Select oSelect = new Select(element);
			selOption = oSelect.getFirstSelectedOption().getText().trim();
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get selected option for the selected tag" + e.getMessage());
		}
		return selOption;
	}

	/**
	 * Gets the selected options.
	 *
	 * @param element
	 *            the element
	 * @return the selected options
	 */
	@SuppressWarnings("null")
	public List<String> getSelectedOptions(WebElement element) {
		List<String> secOptions = null;
		try {
			Select oSelect = new Select(element);
			List<WebElement> elementCount = oSelect.getAllSelectedOptions();
			int iSize = elementCount.size();

			for (int i = 0; i > iSize; i++) {
				secOptions.add(elementCount.get(i).getText());
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed: to get selected options for the selected tag" + e.getMessage());
		}
		return secOptions;
	}


	public String getBrowserName(WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap.getBrowserName().toLowerCase();
	}

	public String getOsName(WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap.getPlatform().toString().toLowerCase();
	}

	HttpURLConnection con = null;

	public boolean browserResponse(WebDriver driver) {
		try {
			HttpURLConnection.setFollowRedirects(true);

			con = (HttpURLConnection) new URL(driver.getCurrentUrl()).openConnection();
			con.setRequestMethod("HEAD");

			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}
	}

	public ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
		@Override
		public Boolean apply(WebDriver driver) {

			return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
					.equals("complete");
		}
	};
	public ExpectedCondition<Boolean> xhrLoad = new ExpectedCondition<Boolean>() {
		@Override
		public Boolean apply(WebDriver driver) {
			return ((JavascriptExecutor) driver).executeScript("return XMLHttpRequest.DONE").toString().equals("4");
		}
	};

	public ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
		@Override
		public Boolean apply(WebDriver driver) {
			try {
				return (Boolean) ((JavascriptExecutor) driver)
						.executeScript("return window.jQuery != undefined || jQuery.active == 0");
			} catch (Exception e) {
				return true;
			}
		}
	};

	public void waitForPageLoaded(WebDriver driver) {
		boolean flag = true;
		try {
			//0.5 second delay beyond which we could say slow performance
			Thread.sleep(500);
			WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_TIMEOUT);
			flag = wait.until(jQueryLoad) && wait.until(jsLoad) && wait.until(xhrLoad);
			browserResponse(driver);

		} catch (Exception e) {
			//System.out.println("wait for page to load exception");
			browserResponse(driver);
		}
	}
	
	/**
	 * Js scroll window.
	 */
	public void jsScrollWindowUp(WebDriver driver) {
		try {
			// Vertical scroll - down by 150 pixels
			((JavascriptExecutor) driver).executeScript("window.scrollBy(450,0)","");
		} catch (Exception e) {
			throw new RuntimeException("Failed: to scroll the window " + e.getMessage());
		}
	}
	
	/**
	 * Gets the elements text
	 *
	 * @param elements
	 *            the element
	 * @return the String array
	 */
	@SuppressWarnings("null")
	public ArrayList<String> storeElementsDataInArray(List<WebElement> elements)
	{
		ArrayList<String> data = new ArrayList<String>();
		try
		{
			for(WebElement element : elements)
			{
				data.add(element.getText().intern().trim());
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Failed: to get data from elements"+e.getMessage());
		}
		return data;
	}
	
	/**
	 * Select by visible text.
	 *
	 * @param element
	 *            the element
	 * @param text
	 *            the text
	 */
	public void selectByVisibleText(WebDriver driver, WebElement element, String text)
	{
		Select oSelect = null;
		wait = new WebDriverWait(driver, 30);
		try
		{
			oSelect = new Select(element);
			wait.until(ExpectedConditions.visibilityOf(element));
			Thread.sleep(Constants.THREAD_SLEEP);
			oSelect.selectByVisibleText(text.trim());
		}catch(Exception e)
		{
			throw new RuntimeException("Failed: to select the element by visible text"+e.getMessage());
		}
	}
	
	/**
	 * Gets the web element.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web element
	 * @throws Exception 
	 */

	public WebElement getWebElementWithText(Map<String, Map<String, String>> map2,	String keyWord, WebDriver driver, String text) 
	{
		try
		{
			by = getBy(map2, keyWord,driver);

			if(by != null)
			{

				if(wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text)) != null)
					element = driver.findElement(by);
			}else
			{
				//System.out.println("Properties not found for element locator");
			}


		} catch (Exception e) {
			throw new RuntimeException("Failed to identify Element in ApplicatioDOM");
		}
		return getElementWithPixelChk(element ,by, driver);
	}
	
	private WebElement getElementWithPixelChk(WebElement element2, By by2, WebDriver driver) {
		boolean flag=true;
		int count=0;
		Point x,x1;
		while(count<5){
			 x= element.getLocation();	
			millisecSleep(100);
			 x1 =element.getLocation();	
			if(x.equals(x1)){
				count++;
			}else{
				count=0;
			}
		}
		return element;			
	}
	
	/**
	 * Gets the web element.
	 *
	 * @param map2
	 *            the map 2
	 * @param keyWord
	 *            the key word
	 * @return the web element
	 * @throws Exception 
	 */

	public WebElement getWebElementV(Map<String, Map<String, String>> map2,	String keyWord, WebDriver driver) 
	{
		try
		{
			by = getBy(map2, keyWord,driver);

			if(by != null)
			{

				if(wait.until(ExpectedConditions.presenceOfElementLocated(by)) != null)
					element = driver.findElement(by);
			}else
			{
				//System.out.println("Properties not found for element locator");
			}


		} catch (Exception e) {
			throw new RuntimeException("Failed to identify Element in ApplicatioDOM");
		}
		return getElementWithPixelChk(element ,by, driver);
	}

	private void millisecSleep(int i) {
		try {
			TimeUnit.MILLISECONDS.sleep(i);
		} catch (InterruptedException e) {

		}
	}

	public static String getDataFromWebPropFile(String key) throws FileNotFoundException {
		
		return  PropertyFileUtils.getPropValuesFromConfig(Constants.WEB_PROPERTIES_FILE, key);
	
	}
}