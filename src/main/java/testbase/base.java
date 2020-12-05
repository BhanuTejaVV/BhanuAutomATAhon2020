
package testbase;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import frameworkdata.ExtentManager;
import uiActions.util;



public class base {
	
	public static WebDriver driver;
	public static Properties prop;
	public static Properties OR;
	
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	 private static Logger log = LogManager.getLogger(base.class.getName());
	public  SoftAssert softAssert = new SoftAssert();
// initialize driver
	public WebDriver initializeDriver() throws IOException{
	    
		//data properties
		 prop = new Properties();
		 try{
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\config\\data.properties");
		prop.load(fs);
		}catch(Exception e){
		e.printStackTrace();
		 }
	
		//OR properties
		OR = new Properties();
		try{
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\config\\OR.properties");
		OR.load(fis);
		}catch(Exception e){
			e.printStackTrace();
		}
		String browserName = prop.getProperty("browser");
	
		if(browserName.equals("chrome")){
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("disable-infobars");
			
			//execute in chrome driver
			System.setProperty("webdriver.chrome.driver",".\\drivers\\chromedriver.exe");
		 driver = new ChromeDriver(options);
		 test.log(LogStatus.PASS, "Chrome Browser");
		}
		else if(browserName.equals("firefox")){
			//execute in firefox driver
			
			System.setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
			
			driver=new FirefoxDriver();
			 test.log(LogStatus.PASS, "Firefox Browser");
		}
		else if(browserName.equals("IE")){
			//execute in ie
			System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver();
			 test.log(LogStatus.PASS, "IE Browser");
		}
		else if(browserName.equals("Edge")){
			//execute in Edge
			System.setProperty("webdriver.edge.driver", ".\\drivers\\MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			 test.log(LogStatus.PASS, "Edge Browser");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
		
	}
	
	
	
	

	
	// get element
	public  WebElement getElement(String locatorKey){
		WebElement e=null;
		WebDriverWait wait = new WebDriverWait(driver,30);
		try{
		if(locatorKey.endsWith("_id"))
			e = wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(locatorKey))));
		else if(locatorKey.endsWith("_name"))
			e = wait.until(ExpectedConditions.elementToBeClickable(By.name(OR.getProperty(locatorKey))));
		else if(locatorKey.endsWith("_xpath"))
			e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(locatorKey))));
		else if(locatorKey.endsWith("_class"))
			e = wait.until(ExpectedConditions.elementToBeClickable(By.className(OR.getProperty(locatorKey))));
		else if(locatorKey.endsWith("_css"))
			e = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(OR.getProperty(locatorKey))));
		
		else{
			reportFailure("Locator not correct - " + locatorKey);
			
			Assert.assertFalse(false, "Locator not correct - " + locatorKey);
			
		}
		wait.until(ExpectedConditions.visibilityOf(e));
		wait.until(ExpectedConditions.elementToBeClickable(e));
		}catch(Exception ex){
			// fail the test and report the error
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			
			Assert.assertFalse(false, "Failed the test - "+ex.getMessage());
		}
		return e;
	}






/*****************************Reporting********************************/

public void reportPass(String msg){
test.log(LogStatus.PASS, msg);
}

public  void reportFailure(String msg){
	test.log(LogStatus.FAIL, msg);
	takeScreenShot();
	Assert.fail(msg);
}






public  void takeScreenShot(){
	
	Date d=new Date();
	String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
	
	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	try {
		FileHandler.copy(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
	test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
	
}

public String testData(String str) {
	
	
	String val = null;
	    try{
			FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\data\\inputdata1.xlsx");
			Workbook wb=new XSSFWorkbook(fs);
			Sheet sh = wb.getSheet(this.getClass().getSimpleName());
			int rowCount = sh.getLastRowNum()-sh.getFirstRowNum();
			int dataRow=(int) sh.getRow(1).getCell(0).getNumericCellValue();
			for (int i = 0; i < rowCount+1; i++) {

		        Row row = sh.getRow(i);
		        Row row1 = sh.getRow(i+dataRow);

		        for (int j = 0; j < row.getLastCellNum(); j++) {
		        	
		        	DataFormatter formatter = new DataFormatter(); 
		        	 Cell cell = row.getCell(j);
		        	 String cellContent = formatter.formatCellValue(cell);
		        	
		        if(cellContent.equals(null)) {
		        		break;
		        	}else if(str.equals(cellContent)){
		            	 val=row1.getCell(j).getStringCellValue();
		            	break;
		            }

		        }
		    }
			}catch(Exception e){
			e.printStackTrace();
			 }
		return val;
}



/**
 * @param throwable
 */
public void getScreenshot(Throwable throwable) {
	// TODO Auto-generated method stub
	
}
}

	

