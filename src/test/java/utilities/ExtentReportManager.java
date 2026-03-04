 package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {// testContext is nothing but the name of test method that we are
													// running. Automatically it captures the test method name

//		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
//		Date dt=new Date();
//		String currentdatetimestamp=df.format(dt);

		// alternative to the above 3 lines is

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName="Test-Reports-"+timeStamp+".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// location of the report with name
		//sparkReporter = new ExtentSparkReporter(("user.dir")+"\\reports\\"+repName);
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report");// title of the report
		sparkReporter.config().setReportName("opencart Functional Testing");// name of the report
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub module", "Customers");
		extent.setSystemInfo("User name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		// below codes (line 45 to 53) gets the os, browser & groups name from the
		// current xml file.
		String os = testContext.getCurrentXmlTest().getParameter("OS");
		extent.setSystemInfo("Operting System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);

		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includeGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {//result is a variable that contain name of test method 
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());// to display groups in report
		test.log(Status.PASS, result.getName() + "got successfully executed");
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());

		test.log(Status.FAIL, result.getName() + "got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imPath = new BaseClass().captureScreen(result.getName());// Call the captureScreen method to take																			
			// the Screen shot and Store the path of the screenshot in a variable
			test.addScreenCaptureFromPath(imPath);// attach the screenshot to the report
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + "got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {
        extent.flush();
        
        String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport=new File(pathOfExtentReport);
        
        try {
        	Desktop.getDesktop().browse(extentReport.toURI());
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        // the below code will send an email automatically once the test execution is completed and the report is generated.
        
        
//        try {
//        	//we already have the report in reports folder
//        	URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);// converting the path into url format
//        	
//        	//Create the email message
//        	ImageHtmlEmail email=new ImageHtmlEmail();//add dependency -> apache commons email
//        	email.setDataSourceresolver(new setDataSourceresolver(url));
//        	email.setHostName("smtp.googleemail.com");//depends on email server. current one will only work for gmail
//        	email.setsmtpPort(465);
//        	email.setAuthenticator(new DefaultAuthenticator("rahul@gmail.com","password"));
//        	email.setSSLOnConnect(true);
//        	email.setFrom("rahul@gmail.com");//sender
//        	email.setSubject("Test Result");
//        	email.setMsg("Please find Attached report");
//        	email.addTo("pavan@gmail.com");//receiver. we can add multiple names by slightly changing the code
//        	email.attch(url,"extent report", "please check Report...");
//        	email.send();//send the email
//        	
//        }
//        catch(Exception e) {
//        	e.printStackTrace();
//        }
	}

}

