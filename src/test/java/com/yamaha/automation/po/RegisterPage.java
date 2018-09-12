package com.yamaha.automation.po;

import org.openqa.selenium.By;

public class RegisterPage {
	public static By fname = By.id("firstname");
	public static By lname = By.id("lastname");
	public static By email = By.name("email");
	public static By country = By.id("country");
	public static By support = By.className("fa-question-circle");
	public static By tos = By.xpath("//a[text()='Terms of Service']");
}
