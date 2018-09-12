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

import com.yamaha.automation.po.HomePage;
import com.yamaha.automation.po.LoginPage;
import com.yamaha.automation.po.RegisterPage;
import com.yamaha.automation.po.UserPage;
import com.yamaha.automation.reusable.ExcelUtil;
import com.yamaha.automation.reusable.WebDriverHelper;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
	  MethodListener.class })
public class TS002 {
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
		WebDriverHelper.click(HomePage.user);
	}
	@Test(dataProvider="login_data")
	public void login(String user, String pwd, String id)
	{
		WebDriverHelper.type(LoginPage.email, user);
		WebDriverHelper.type(LoginPage.pwd, pwd);
		WebDriverHelper.click(LoginPage.login);
		WebDriverHelper.assertText(UserPage.id,id);
		WebDriverHelper.click(UserPage.logout);
	}
	
	@Test(dataProvider="reg")
	public void register(String fname, String lname, String email, String country)
	{
		WebDriverHelper.click(LoginPage.reg);
		WebDriverHelper.type(RegisterPage.fname, fname);
		WebDriverHelper.type(RegisterPage.lname, lname);
		WebDriverHelper.type(RegisterPage.email, email);
		WebDriverHelper.select(RegisterPage.country, country);
		WebDriverHelper.mouseover(RegisterPage.support);
		WebDriverHelper.mouseover(RegisterPage.tos);
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
