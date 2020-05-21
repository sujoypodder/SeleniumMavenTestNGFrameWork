package ucam.uap.bd.edu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginAutoTest {

	WebDriver driver;

	@Parameters({ "browser" })

	@BeforeClass
	public void setUp(@Optional("chrome") String browser) {

		if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\88016\\Desktop\\ecl-programe\\SampleLoginTestUap\\webdriver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\88016\\Desktop\\ecl-programe\\SampleLoginTestUap\\webdriver\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			System.out.println("don't know how to start" + browser + ",start with chrome instead");
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\88016\\Desktop\\ecl-programe\\SampleLoginTestUap\\webdriver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
	}

	// PositiveTest starting
	@Test(priority = 1)
	public void positiveTest() {

		driver.get("http://ucam.uap-bd.edu/Security/Login.aspx");
		System.out.println("start positive testing");
		// login id
		WebElement logid = driver.findElement(By.id("logMain_UserName"));
		logid.sendKeys("15201128");
		// password
		WebElement pass = driver.findElement(By.id("logMain_Password"));
		pass.sendKeys("15201128");
		// login button
		WebElement logbtn = driver.findElement(By.id("logMain_Button1"));
		logbtn.click();
//		WebElement forget=driver.findElement(By.linkText("Forgot Your Password ?"));
//		forget.click();
		// Url verify
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "http://ucam.uap-bd.edu/Security/Home.aspx?mmi=41485d2c6c554d494e63";
		if (expectedUrl.equals(actualUrl)) {
			System.out.println("verified url");
		} else {
			System.out.println("the expected and actual url is not same");
		}
		// logout visible or not
		WebElement logout = driver.findElement(By.id("ctl00_lblLogout"));
		Assert.assertTrue(logout.isDisplayed(), "logout button is not visible");
		// message check
		WebElement successmsg = driver
				.findElement(By.xpath("//*[@id=\"aspnetForm\"]/div[3]/div/div[3]/div/div/div[3]/div/div[1]/h2[1]"));
		String actualmsg = successmsg.getText();
		String expectedmsg = "Welcome to UAP UCAM";
		Assert.assertTrue(actualmsg.contains(expectedmsg), "actual msg does not contain expected msg");
		// title verify
		String expectedTitle = "Home";
		String actualTitle = driver.getTitle();
		if (actualTitle.equals(expectedTitle)) {
			System.out.println("title verified");
		} else {
			System.out.println("actual and expected title is not same");
		}
	}

	// NegativeTest starting
	@Parameters({ "loginid", "password", "expectedermsg" })
	@Test(priority = 2)
	public void negativeTest(String loginid, String password, String expectedermsg) {

		driver.get("http://ucam.uap-bd.edu/Security/Login.aspx");
		System.out.println("negative test starting");
		// login id
		WebElement logid = driver.findElement(By.id("logMain_UserName"));
		logid.sendKeys(loginid);
		// password
		WebElement pass = driver.findElement(By.id("logMain_Password"));
		pass.sendKeys(password);
		// login button
		WebElement logbtn = driver.findElement(By.id("logMain_Button1"));
		logbtn.click();
//		WebElement forget=driver.findElement(By.linkText("Forgot Your Password ?"));
//		forget.click();
		// error message check
		WebElement errormsg = driver.findElement(By.xpath("//*[@id=\"logMain\"]/tbody/tr/td/div[4]"));
		String actualermsg = errormsg.getText();
		Assert.assertTrue(actualermsg.contains(expectedermsg), "actual msg does not contain expected msg");
	}

	@AfterClass
	public void tearDown() {
		sleep(3000);
		driver.close(); //
		driver.quit();
	}

	private void sleep(long l) {
		try {
			Thread.sleep(l);
		} catch (InterruptedException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
