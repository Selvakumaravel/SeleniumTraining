package com.yamaha.automation.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yamaha.automation.reusable.ExcelUtil;
import com.yamaha.automation.reusable.WebDriverHelper;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
	  MethodListener.class })
public class TS001 {
		{
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
		}
	@BeforeSuite
	public void bs() throws FileNotFoundException, IOException
	{
		WebDriverHelper.initialize();
	}
	@BeforeMethod
	public void bm()
	{
		WebDriverHelper.navigate("http://magento.com");
		WebDriverHelper.click(By.className("fa-user"));
	}
	@Test(dataProvider="login_data")
	public void login(String user, String pwd, String id)
	{
		WebDriverHelper.type(By.id("email"), user);
		WebDriverHelper.type(By.id("pass"), pwd);
		WebDriverHelper.click(By.id("send2"));
		WebDriverHelper.assertText(By.xpath("//div[@class='account-help']/span"),id);
		WebDriverHelper.click(By.linkText("Log Out"));
	}
	
	@Test(dataProvider="reg")
	public void register(String fname, String lname, String email, String country)
	{
		WebDriverHelper.click(By.xpath("//span[text()='Register']"));
		WebDriverHelper.type(By.id("firstname"), fname);
		WebDriverHelper.type(By.id("lastname"), lname);
		WebDriverHelper.type(By.name("email"), email);
		WebDriverHelper.select(By.id("country"), country);
		WebDriverHelper.mouseover(By.className("fa-question-circle"));
		WebDriverHelper.mouseover(By.xpath("//a[text()='Terms of Service']"));
	}
	@AfterSuite
	public void as()
	{
		WebDriverHelper.quit();
	}
	@DataProvider
	public Object[][] reg() throws FileNotFoundException, IOException
	{
		return ExcelUtil.DataTable("src/test/resources/Data/Book1.xlsx", "Register");
	}
	
	@DataProvider
	public Object[][] login_data() throws FileNotFoundException, IOException
	{
		return ExcelUtil.DataTable("src/test/resources/Data/Book1.xlsx", "Login",2);
	}
}
