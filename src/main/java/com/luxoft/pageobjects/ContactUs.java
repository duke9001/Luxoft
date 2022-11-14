package com.luxoft.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.luxoft.utility.Log;
import com.luxoft.actiondriver.Action;
import com.luxoft.base.BaseClass;

public class ContactUs extends BaseClass{

	private Action action = new Action();
	
	@FindBy(xpath = "//ul[contains(@class,'tabs__nav')]/li[2]")
	private WebElement callUs;
	
	@FindBy(xpath = "//*[@id=\"tab-2\"]/div/div/div/div[1]/select")
	private WebElement country;
	
	@FindBy(xpath = "//span[contains(@title,'Shanghai')]")
	private WebElement city;
	
	@FindBy(xpath = "//div[@id='2ab8baf523b21b70220368331975f01f']/h5")
	private WebElement address;
	
	@FindBy(xpath = "//div[@id='2ab8baf523b21b70220368331975f01f']/h2/a")
	private WebElement phoneNumber;
	
	
	public ContactUs() {
		PageFactory.initElements(driver, this);
	}

	public String getCurrURL() throws Throwable {
		String URL=action.getCurrentURL(driver);
		return URL;
	}
	
	public ContactUs clickonCallUsTab() {
		action.click(driver, callUs);
		Log.info("click on callus tab");
		extentTest.info("click on call us tab");
		action.explicitWait(driver, country, 20);
		extentTest.pass("call us tab load successfully");
		return new ContactUs();
	}
	
	public ContactUs clickonCountry(String value) {
		action.selectByValue(this.country, value);
		Log.info("click on country");
		extentTest.info("click on country");
		extentTest.pass("country load successfully");
		return new ContactUs();
	}
	
	public ContactUs getCity(String city) {
		String newCity = action.getText(this.city);
		Log.info("verify city");
		extentTest.info("verify city");
		if(city.equals(newCity)) {
			Log.info(city + "found");
			extentTest.pass(city + " found");
		}
		else {
			Log.info(city + "not found");
			extentTest.fail(city + " not found");
		}
		
		return new ContactUs();
	}
	
	public ContactUs getAddress(String address) {
		String newAddress = action.getText(this.address);
		Log.info("verify address");
		extentTest.info("verify address");
		if(address.equals(newAddress)) {
			Log.info(address + " found");
			extentTest.pass(address + " found");
		}
		else {
			Log.info(address + "not found");
			extentTest.fail(address + "not found");
		}
		
		return new ContactUs();
	}
	
	public String getPhone(String phoneNumber) {
		String newPhoneNumber = action.getText(this.phoneNumber);
		Log.info("verify phone number");
		extentTest.info("verify phone number");
		if(phoneNumber.equals(newPhoneNumber)) {
			Log.info(phoneNumber + " found");
			extentTest.pass(phoneNumber + " found");
			newPhoneNumber = phoneNumber;
		}
		else {
			Log.info(phoneNumber + " not found");
			extentTest.fail(phoneNumber + " not found");
		}
		
		return newPhoneNumber;
	}
	
}
