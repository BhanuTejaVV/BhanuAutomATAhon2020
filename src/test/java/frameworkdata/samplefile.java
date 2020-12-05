/**
 * 
 */
package frameworkdata;


import org.testng.annotations.Test;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import frameworkdata.ExtentManager;
import uiActions.util;

public class samplefile extends testbase.base{
SoftAssert softAssert = new SoftAssert();
private static Logger log = LogManager.getLogger(samplefile.class.getName());
util u = new util();

@BeforeTest
	public void driverinitialize() throws IOException{
		test = rep.startTest("testname");
		test.log(LogStatus.INFO, "Test case description");
		log.debug("starting test");
		initializeDriver();
		
	}

	
@Test
public void testname() {
	
	driver.get(prop.getProperty("Url"));
	
}


		
@AfterTest
public void closeBrowser(){
	driver.close();
	driver = null; 
	rep.endTest(test);
	rep.flush();
}

}
