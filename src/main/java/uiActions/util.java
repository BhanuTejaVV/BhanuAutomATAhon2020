
package uiActions;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;

//import com.hybridFramework.helper.Javascript.JavaScriptHelper;
//import com.hybridFramework.helper.Logger.LoggerHelper;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import frameworkdata.ExtentManager;
import testbase.base;

@Test
public class util extends base{

	public static WebDriver driver;
	public static Properties prop;
	public static Properties OR;
	public File f1;
	public File f2;
	public FileInputStream file1;
	public FileInputStream file2;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	//private Logger Log = LoggerHelper.getLogger(util.class);
	 private static Logger log = LogManager.getLogger(util.class.getName());
	
	 public static Robot rob;
	
	//close browser

	public void closeBrowser(){
		driver.close();
		driver = null;
	}
	
	
	
	
	
	 	/********************Explicit Wait/Expected Conditions*******************************/
	
	public void waitForElementPresence(WebDriver driver,By element)
	{
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    try{
	    wait.until(ExpectedConditions.presenceOfElementLocated(element));
	    } catch(Exception e){
	    	reportFailure(e.getMessage());
			e.printStackTrace();
	    
			Assert.assertFalse(false, "Failed the test - "+e.getMessage());
	    }
	}
	
	
	public void waitForElementVisibility(WebDriver driver,WebElement element)
	{
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    try{
	    wait.until(ExpectedConditions.visibilityOf(element));
	   
	    } catch(Exception e){
	    	reportFailure(e.getMessage());
			e.printStackTrace();
	    
			Assert.assertFalse(false, "Failed the test - "+e.getMessage());
	    }
	}
	
	public void waitForlocatorVisibility(WebDriver driver,By locator)
	{
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    try{
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    } catch(Exception e){
	    	reportFailure(e.getMessage());
			e.printStackTrace();
	    
			Assert.assertFalse(false, "Failed the test - "+e.getMessage());
	    }
	}
	
	
	public void islocatorInvisible(WebDriver driver,By locator)
	{
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    try{
	    
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	    } catch(Exception e){
	    	reportFailure(e.getMessage());
			e.printStackTrace();
	    
			Assert.assertFalse(false, "Failed the test - "+e.getMessage());
	    }
	}
	
	
	public void waitForElementClickable(WebDriver driver,WebElement element)
	{
	    WebDriverWait wait = new WebDriverWait(driver,20);
	    try{
	    
	    wait.until(ExpectedConditions.elementToBeClickable(element));
	    } catch(Exception e){
	    	reportFailure(e.getMessage());
			e.printStackTrace();
	    
			Assert.assertFalse(false, "Failed the test - "+e.getMessage());
	    }
	}
	
	
	
	/****************click,wait,type **********************/
	
	       //wait
			public void waitToLoad() throws InterruptedException{
				Thread.sleep(5000);
			}

			
			public static void waitForPageLoad(WebDriver driver,int i) {

				driver.manage().timeouts().pageLoadTimeout(i, TimeUnit.SECONDS);

				}
			
	//click
	public  void click(String locatorKey){
		try{
			
		getElement(locatorKey).click();
		
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
//type
	public void type(String locatorKey,String data){
		try{
		getElement(locatorKey).sendKeys(data);
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/********************Keyboard Actions***********************/
	public void pressTabKey(){
		try{
			rob.keyPress(KeyEvent.VK_TAB);
	        Thread.sleep(2000);	
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
		}
	}
	public void pressEnterKey(){
		try{
			rob.keyPress(KeyEvent.VK_ENTER);
	        Thread.sleep(2000);	
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
		}
	}
	public void enterDataUsingKeyboard(String text){
		try{
			
			StringSelection stringSelection = new StringSelection(text);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, stringSelection);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);	
		}catch(Exception e){
			reportFailure(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/********************Browser Navigation***********************/
	public static void navigate_forward(WebDriver driver) {
		driver.navigate().forward();
		}

		public static void navigate_back(WebDriver driver) {
		driver.navigate().back();
		}

		public static void refresh(WebDriver driver) {
		driver.navigate().refresh();
		}
	
	/***********************Validations***************************/

	public boolean verifyTextEquals(WebElement element,String expectedText){
		try {
	Assert.assertTrue(element.getText().matches(expectedText));
		}catch (Error e) {
			reportFailure(e.toString());
			Assert.assertFalse(false, "Text didn't match");
		}
	return false;
	}
	
	//wait for element
	public WebElement waitForElement(WebDriver driver,long time,WebElement element){
		WebDriverWait wait = new WebDriverWait(driver,time);
		//
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);
		//return wait.until(ExpectedConditions.elementToBeClickable(element));
		return element;
		
		
	}
	
	public void elementNotPresent(WebElement element){
		try {
	        if(element.isDisplayed())
	        	Assert.assertFalse(false);	
		}catch (Error e) {
			    Assert.assertTrue(true);
		}
	}
	
		

/*****************************Alerts***********************************/
	//alert
	      public static boolean alert(){
			try{
				Alert a = driver.switchTo().alert();
				String str = a.getText();
				a.accept();
				return true;
			}catch(Exception e){
			
			return false;
			}
			}
	
		
	      public Alert getAlert() {
		 driver.switchTo().alert();
		 return getAlert();
		  }
		
		
		public void AcceptAlert() {
			 getAlert().accept();
			 test.log(LogStatus.PASS, "Alert Accepted");
		}
		
		public void DismissAlert() {
			
			getAlert().dismiss();
			 test.log(LogStatus.PASS, "Alert Dismissed");
		}
		
		public String getAlertText() {
			String text = getAlert().getText();
			 test.log(LogStatus.PASS, "Text retrieved");
			return text;
		}
		
		public boolean isAlertPresent() {
			try {
				driver.switchTo().alert();
				test.log(LogStatus.PASS, "Alert Found");
				
				return true;
			} catch (NoAlertPresentException e) {
				// Ignore
				test.log(LogStatus.FAIL, "Alert not found");
				return false;
			}
		}
		
		public void AcceptAlertIfPresent() {
			if (!isAlertPresent())
				return;
			AcceptAlert();
			test.log(LogStatus.PASS, "Alert Accepted");
		}
		
		public void DismissAlertIfPresent() {

			if (!isAlertPresent())
				return;
			DismissAlert();
			test.log(LogStatus.PASS, "Alert Dismissed");
		}
		
		public void AcceptPrompt(String text) {
			
			if (!isAlertPresent())
				return;
			
			Alert alert = getAlert();
			alert.sendKeys(text);
			alert.accept();
			test.log(LogStatus.PASS, "Prompt Alert Accepted");
		}

	    /******************* window handling ********************/
	
		public static void switchToNewWindow() {
			Set s = driver.getWindowHandles();
			Iterator itr = s.iterator();
			String w1 = (String) itr.next();
			String w2 = (String) itr.next();
			driver.switchTo().window(w2);
			}

			public static void switchToOldWindow() {
			Set s = driver.getWindowHandles();
			Iterator itr = s.iterator();
			String w1 = (String) itr.next();
			String w2 = (String) itr.next();
			driver.switchTo().window(w1);
			}

			public static void switchToParentWindow() {
			driver.switchTo().defaultContent();
			}

		
		
		/*****************Verify element is displayed ***************/
	
		
		
			public static ExpectedCondition<Boolean> isElementDisplayed(final WebElement element){
				return new ExpectedCondition<Boolean> (){
					public Boolean apply(WebDriver driver){
						try{
							return element.isDisplayed();
						}catch(NoSuchElementException e){
							return false;
						}catch(StaleElementReferenceException e1){
							return false;
						}
					}
				};
			}
			
			
			
			//checkbox
			
			public boolean isChecboxSelected(WebElement element){
				try {
			Assert.assertTrue(element.isSelected());
				}catch (Error e) {
					reportFailure(e.toString());
					Assert.assertFalse(false, "checkbox not selected");
				}
			return false;
			}
			

	/*************************Dropdown*********************************/
		
	   public String getSelectedValue(WebElement element) {
			String value = new Select(element).getFirstSelectedOption().getText();
		 	return value;
		}
		
		public void SelectUsingIndex(WebElement element,int index) {
			Select select = new Select(element);
			select.selectByIndex(index);
			
		}
		
		public void SelectUsingVisibleText(WebElement element,String text) {
			Select select = new Select(element);
			select.selectByVisibleText(text);
			}
		
		public void SelectByVisibleValue(WebElement element,String text) {
			Select select = new Select(element);
			select.selectByValue(text);
		}
		
		
		public List<String> getAllDropDownValues(WebElement locator) {
			Select select = new Select(locator);
			List<WebElement> elementList = select.getOptions();
			List<String> valueList = new LinkedList<String>();
			
			for (WebElement element : elementList) {
				test.log(LogStatus.PASS,element.getText());
				valueList.add(element.getText());
			}
			return valueList;
		}
		

	/************************scroll*******************************/
		//scroll to bottom
		public static void scrollToBottom(WebDriver driver) {
	        ((JavascriptExecutor) driver)
	                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
	    }
		
		//scroll to element
		public static void scrollTo(WebDriver driver, WebElement element) {
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView();", element);
	    }
		
		//scroll by coordinates
		public void scrollingByCoordinatesofAPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
		}

		
		}

