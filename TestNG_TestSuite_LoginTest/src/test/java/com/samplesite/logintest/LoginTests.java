package com.samplesite.logintest;

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

public class LoginTests {
	WebDriver drive;

	@Parameters({ "browser" })
	@BeforeClass(alwaysRun = true)
	public void setUp(@Optional("chrome") String browser) {
//		create driver
		switch (browser) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\88016\\eclipse-workspace\\SeleniumMavenTestNGLoginTest\\Driver\\chromedriver.exe");
			drive = new ChromeDriver();
			break;
		case "firefox":
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\88016\\eclipse-workspace\\SeleniumMavenTestNGLoginTest\\Driver\\geckodriver.exe");
			drive = new FirefoxDriver();
			break;
		default:
			System.out.println("Do not know how to start" + browser + ",starting with chrome instead");
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\88016\\eclipse-workspace\\SeleniumMavenTestNGLoginTest\\Driver\\chromedriver.exe");
			drive = new ChromeDriver();
			break;
		}

		// sleep for 3 sec
		// sleep(3000);
		// maximize driver variable
		drive.manage().window().maximize();
	}

	// PositiveTest---------------------------------------------
	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })

	public void PositiveLoginTest() {
		System.out.println("starting positive logintest");

//		open test page
		String url = "http://the-internet.herokuapp.com/login";
		drive.get(url);
		System.out.println("page is opened");
		// sleep for 2 sec
		// sleep(2000);

//		enter username
		WebElement username = drive.findElement(By.id("username"));
		username.sendKeys("tomsmith");

//		enter password
		WebElement password = drive.findElement(By.name("password"));
		password.sendKeys("SuperSecretPassword!");

//		click login button
		WebElement logInButton = drive.findElement(By.tagName("button"));
		logInButton.click();
		sleep(3000);

//		verifications:
//			new url
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = drive.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "actual page url is not the same as expected");
//			logout button is visible
		WebElement logOutButton = drive.findElement(By.xpath("//a[@class='button secondary radius']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "log out button is not visible");
//			succesful login message
		// WebElement successMessage = drive.findElement(By.cssSelector("div#flash"));
		WebElement successMessage = drive.findElement(By.xpath("//div[@id='flash']"));
		String actualMessage = successMessage.getText();
		String expectedMessage = "You logged into a secure area!";
		// Assert.assertEquals(actualMessage, expectedMessage, "actual message is not
		// same as expected message");
		Assert.assertTrue(actualMessage.contains(expectedMessage),
				"actual msg does not contain expected msg.\nActual message " + actualMessage + "\nExpected message: "
						+ expectedMessage);

	}

	// NegativeTest------------------------------------------
	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 2, groups = { "negativeTests", "smokeTests" })
	public void negativeLoginTest(String username, String password, String expectedErrorMessage) {
		System.out.println("starting negativeLoginTest with " + username + "and" + password);

//		open test page
		String url = "http://the-internet.herokuapp.com/login";
		drive.get(url);
		System.out.println("page is opened");
		// sleep for 2 sec
		// sleep(2000);

//		enter username
		WebElement usernameElement = drive.findElement(By.id("username"));
		usernameElement.sendKeys(username);

//		enter password
		WebElement passwordElement = drive.findElement(By.name("password"));
		passwordElement.sendKeys(password);

//		click login button
		WebElement logInButton = drive.findElement(By.tagName("button"));
		logInButton.click();
		sleep(3000);

//		verifications:
//			new url
		WebElement errorMessage = drive.findElement(By.xpath("//div[@class='flash error']"));
		String actualerrorMessage = errorMessage.getText();
		// Assert.assertEquals(actualMessage, expectedMessage, "actual message is not
		// same as expected message");
		Assert.assertTrue(actualerrorMessage.contains(expectedErrorMessage),
				"actual error msg does not contain expected error msg.\nActual message " + actualerrorMessage
						+ "\nExpected message: " + expectedErrorMessage);

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		// close driver
		sleep(2000);
		drive.quit();
	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
