package listners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.Status;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import frameworkdata.ExtentManager;
import testbase.base;


public class listners  implements ITestListener{
	
	public static ExtentReports extent = ExtentManager.getInstance();
	public static ExtentTest test;

testbase.base b = new testbase.base();

public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestSuccess(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestFailure(ITestResult result) {

	
	// TODO Auto-generated method stub
//	b.getScreenshot(result.getName());
	b.getScreenshot(result.getThrowable());
   

	//b.takeScreenShot(result.getName());
	
}

public void onTestSkipped(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
	
}

public void onStart(ITestContext context) {
	// TODO Auto-generated method stub
	
}

public void onFinish(ITestContext context) {
	// TODO Auto-generated method stub
	
}
}