package com.yamaha.automation.reusable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


public class WebDriverHelper {
static WebDriver driver;
static FluentWait wait;
	public static void initialize() throws FileNotFoundException, IOException
	{
		Properties p = new Properties();
		p.load(new FileInputStream("src/test/resources/Project.properties"));
		String browser = p.getProperty("browser","chrome");
		String url = p.getProperty("url");
		switch(browser)
		{
		case "gc": case "chrome":
			ChromeOptions optgc = new ChromeOptions();
			optgc.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe");
			driver = new ChromeDriver(optgc);
			break;
		case "ff": case "firefox":
			FirefoxOptions optff = new FirefoxOptions();
			optff.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.gecko.driver", "src/test/resources/Drivers/geckodriver.exe");
			driver = new FirefoxDriver(optff);
			break;
		case "ie":
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.ie.driver", "src/test/resources/Drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver(cap);
			break;
		default:
			ChromeOptions optgcd = new ChromeOptions();
			optgcd.setAcceptInsecureCerts(true);
			System.setProperty("webdriver.chrome.driver", "src/test/resources/Drivers/chromedriver.exe");
			driver = new ChromeDriver(optgcd);
			break;
		}
		ATUReports.setWebDriver(driver);
		wait = new FluentWait(driver)
				.withTimeout(Duration.ofSeconds(60))
				.pollingEvery(Duration.ofSeconds(1));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if(url.startsWith("http")||url.startsWith("ftp")||url.startsWith("file:///"))
			driver.get(url);
		else
			driver.get("http://"+url);
		ATUReports.add("Initialized "+url+" in "+browser,driver.getCurrentUrl(), LogAs.INFO, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
	static int i=0;
	public static void click(By loc)
	{
		i++;
		try
		{
		wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		wait.until(ExpectedConditions.elementToBeClickable(loc));
		driver.findElement(loc).click();
		ATUReports.add("Clicked on "+loc.toString(), LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
		}
		catch(NoSuchElementException e)
		{
			if(i<3)
			{
				driver.manage().deleteAllCookies();
				driver.navigate().refresh();
				click(loc);
			}
			else
			{
			ATUReports.add("Clicked on "+loc.toString()+" failed after 3 attempts", LogAs.FAILED, new CaptureScreen(
	                ScreenshotOf.DESKTOP));
			}
		}
		catch(ElementNotInteractableException e)
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click()", driver.findElement(loc));
			ATUReports.add("Clicked on "+loc.toString()+" using JS Executor", LogAs.PASSED, new CaptureScreen(
	                ScreenshotOf.DESKTOP));
		}
	}
	
	public static void type(By loc, String value)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		driver.findElement(loc).clear();
		driver.findElement(loc).sendKeys(value);
		ATUReports.add("Enter text on "+loc.toString(),value, LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
	
	public static void select(By loc, String value)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		Select sel = new Select(driver.findElement(loc));
		sel.selectByVisibleText(value);
		ATUReports.add("Select from "+loc.toString()+" dropdown ",value, LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
	public static void mouseover(By loc)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(loc));
		wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(loc)).build().perform();
		ATUReports.add("Mouse over on"+loc.toString(), LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
	public static void navigate(String url)
	{
		driver.get(url);
		ATUReports.add("Navigate onto URL",url, LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
	public static void quit()
	{
		driver.quit();
	}
	public static void assertText(By loc, String text)
	{
		Assert.assertEquals(driver.findElement(loc).getText(), text);
		ATUReports.add("Text Assertion",driver.findElement(loc).getText(),text , LogAs.PASSED, new CaptureScreen(
                ScreenshotOf.DESKTOP));
	}
}
