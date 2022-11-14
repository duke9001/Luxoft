package com.luxoft.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;


import com.luxoft.base.BaseClass;
import com.luxoft.pageobjects.ContactUs;
import com.luxoft.utility.Log;

public class ContactUsTest extends BaseClass{

	private ContactUs contactUs;		

	@Test(testName = "Test country and city drop downs", groups = {"Smoke", "Sanity", "Regression"})
	public void TestContryandCityDropDown() throws Throwable {
		


		contactUs = new ContactUs();

		driver.get(readConfig.getContactUsURL());
		extentTest.info("Navigated to Url");
		Log.startTestCase("verify country and city drop downs");
		contactUs.clickonCallUsTab();
		contactUs.clickonCountry("ae54a5c026f31ada088992587d92cb3a");
		contactUs.getCity("Shanghai");	
		contactUs.getAddress("Shanghai, China");		
		Assert.assertEquals("+0086(21) 64170010", contactUs.getPhone("+0086(21) 64170010"));
		extentTest.pass("Test country and city drop downs passed");


	}
}
