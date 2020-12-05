package frameworkdata;

//http://relevantcodes.com/Tools/ExtentReports2/javadoc/index.html?com/relevantcodes/extentreports/ExtentReports.html


import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class ExtentManager {
	private static ExtentReports extent;
	public static Properties prop;
	public static ExtentReports getInstance() {
		 prop = new Properties();
		 try{
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\config\\data.properties");
		prop.load(fs);
		}catch(Exception e){
		e.printStackTrace();
		 }
		 
		 
		 if (extent == null) {
				Date d=new Date();
				String fileName=d.toString().replace(":", "_").replace(" ", "_")+".html";
				extent = new ExtentReports(System.getProperty("user.dir")+"\\reports\\"+fileName, true, DisplayOrder.NEWEST_FIRST);

				
				extent.loadConfig(new File(System.getProperty("user.dir")+"//ReportsConfig.xml"));
				// optional
				extent.addSystemInfo("Selenium Version", "3.7.1").addSystemInfo(
						"Environment", "QA").addSystemInfo("Browser", prop.getProperty("browser"));
				
			}
			return extent;
	}
			
			
		
	}

