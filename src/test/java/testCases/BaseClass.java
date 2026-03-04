package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public static WebDriver driver;
	public Logger logger;
	public Properties prop;
	
	@BeforeClass(groups={"Sanity","Master","Regression"} )
	@Parameters({"OS","browser"})
	public void setUp(String os,String br) throws IOException {
		
		//FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\utilities\\config.properties");
		FileReader file= new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
	    prop=new Properties();
		prop.load(file);
				
		logger = LogManager.getLogger(this.getClass());
		
		//below code is for selenium grid -- remote
		if(prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("invalid OS");return;
			}
			
			//browser
			switch(br.toLowerCase()) {
			
			case "chrome": capabilities.setBrowserName("chrome");break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
			case "firefox": capabilities.setBrowserName("firefox");break;
			default : System.out.println("Invalid browser");return;
			}
			
			driver =new RemoteWebDriver(new URL("http://192.168.29.174:4444/wd/hub"),capabilities);//add /wd/hub after the url
		}
		//this is for local
		else if(prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br) {
			case "chrome": driver=new ChromeDriver(); break;
			case "edge": driver=new EdgeDriver(); break;
			case "firefox": driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser");return;
			}	
		}
			
		driver.manage().deleteAllCookies();
		driver.get(prop.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@AfterClass(groups={"Sanity","Master","Regression"} )
	public void tearDown() {
		driver.quit();		
	}
	
	public String generateString() {
		String str=RandomStringUtils.randomAlphabetic(5);
		return str;
	}

	public String generateNumber() {
		String str=RandomStringUtils.randomNumeric(10);
		return str;
	}
	
	public String generateAlphaNumeric() {
		String str1=RandomStringUtils.randomAlphabetic(3);
		String str=RandomStringUtils.randomNumeric(3);
		return (str1+"@"+str);
	}
	
	public String captureScreen(String name) throws IOException {
		
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String trgpath = System.getProperty("user.dir") + "\\screenshots\\" + name + "-" + timestamp + ".png";
		File trg = new File(trgpath);
		src.renameTo(trg);
		return trgpath;
		
	}
}
