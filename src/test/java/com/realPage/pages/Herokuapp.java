package com.realPage.pages;

import java.util.Set;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realPage.util.GlobalData;
import com.realPage.util.ReusableFunctions;

import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Herokuapp {

	private WebDriver driver;

	// Page Object Design Pattern - web elements for the pages are defined in the
	// page layer itself
	public Herokuapp(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	private static Logger log = LoggerFactory.getLogger(Herokuapp.class);

	@FindBy(id = "username")
	private WebElement uname;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(xpath = "//i[contains(text(),'Login')]")
	private WebElement loginButton;

	@FindBy(linkText = "Form Authentication")
	private WebElement formAuthLink;

	@FindBy(xpath = "//i[contains(text(),'Logout')]")
	private WebElement logoutButton;

	@FindBy(xpath = "//h3[contains(text(),'Opening a new window')]")
	private WebElement multipleWindows;

	@FindBy(xpath = "//a[contains(text(),'Click Here')]")
	private WebElement clickHereParentLink;

	@FindBy(xpath = "//h3[contains(text(),'New Window')]")
	private WebElement newWindow;

	@FindBy(id = "email")
	private WebElement email;

	@FindBy(xpath = "//i[contains(text(),'Retrieve password')]")
	private WebElement retrievePasswordButton;

	@FindBy(xpath = "//h1[contains(text(),'Internal Server Error')]")
	private WebElement forgotPassConfPage;

	// Function to login into the application
	public String login(WebDriver driver, Scenario scenario) {
		try {
			if (ReusableFunctions.waitForElementClickable(driver, uname, 20).equalsIgnoreCase("pass")) {
				ReusableFunctions.inputText(uname, GlobalData.ConfigData.get("username"));
				ReusableFunctions.inputText(password, GlobalData.ConfigData.get("passcode"));
				ReusableFunctions.inputText(password, GlobalData.ConfigData.get("passcode"));
				loginButton.click();
				if (ReusableFunctions.waitForElementClickable(driver, logoutButton, 20).equalsIgnoreCase("pass")) {
					ReusableFunctions.captureScreenshot(driver, scenario);
					System.out.println("Login successful-Landed in Home page");
					return "pass";
				} else {
					System.out.println("Login is NOT successful");
					return "fail";
				}
			} else {
				System.out.println("Page NOT loaded successfully");
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	// Function to logout from the application
	public String logout(WebDriver driver, Scenario scenario) {
		try {
			if (ReusableFunctions.waitForElementClickable(driver, logoutButton, 20).equalsIgnoreCase("pass")) {
				logoutButton.click();
				if (ReusableFunctions.waitForElementClickable(driver, loginButton, 20).equalsIgnoreCase("pass")) {
					ReusableFunctions.captureScreenshot(driver, scenario);
					System.out.println("Logout successful");
					return "pass";
				} else {
					System.out.println("Logout is NOT successful");
					return "fail";
				}
			} else {
				System.out.println("Page NOT loaded successfully");
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	// Function click by value
	public static String clickLinkbyValue(WebDriver driver, String testData) {
		try {
			driver.findElement(By.linkText(testData)).click();
			return "pass";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public String handleMultipleWins(WebDriver driver, Scenario scenario) {
		try {
			if (ReusableFunctions.waitForElementClickable(driver, multipleWindows, 20).equalsIgnoreCase("pass")) {
				if (multipleWindows.isDisplayed()) {
					String pWindow = driver.getWindowHandle();
					clickHereParentLink.click();
					Set<String> windows = driver.getWindowHandles();
					for (String window : windows) {
						if (!(window.equals(pWindow))) {
							driver.switchTo().window(window);
							if (newWindow.isDisplayed()) {
								System.out.println("Navigated to new Window");
								ReusableFunctions.captureScreenshot(driver, scenario);
//						return "pass";
							} else {
								System.out.println("Navigation to new Window failed");
								return "fail";
							}
						}
					}
					driver.switchTo().window(pWindow);
					return "pass";
				} else {
					System.out.println("Multiple Windows are NOT displayed");
					return "fail";
				}
			} else {
				System.out.println("Page NOT loaded successfully");
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

	}

	public String forgotPassword(WebDriver driver, Scenario scenario) {
		try {
			if (ReusableFunctions.waitForElementClickable(driver, email, 20).equalsIgnoreCase("pass")) {
				email.sendKeys("baala.cg@gmail.com");
				String pWindow = driver.getWindowHandle();
				retrievePasswordButton.click();
				if (forgotPassConfPage.isDisplayed()) {
					System.out.println("Password retrieved successfully");
					ReusableFunctions.captureScreenshot(driver, scenario);
//					return "pass";
				} else {
					System.out.println("Password retrieve failed");
					return "fail";
				}
				driver.navigate().back();
				return "pass";
			} else {
				System.out.println("Page NOT loaded successfully");
				return "fail";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}

	public int getBasicAuth(WebDriver driver, Scenario scenario) {
		Response resp = RestAssured.given().auth().preemptive().basic("ToolsQA", "TestPassword").when()
				.get("https://restapi.demoqa.com/authentication/CheckForAuthentication/");
		return resp.getStatusCode();
	}
	
	public int postRequest(Scenario scenario) {
		RequestSpecification request = RestAssured.given();
		JSONObject json = new JSONObject();
		json.put(GlobalData.DataElements.get("key1"), GlobalData.DataElements.get("value1"));
		json.put(GlobalData.DataElements.get("key2"), GlobalData.DataElements.get("value2"));
		request.body(json.toJSONString());
		System.out.println("Request String:" + json.toJSONString());		
		Response resp = request.post("https://reqres.in/api/users");
		System.out.println("Response String: " + resp.getBody().asPrettyString());		
		return resp.getStatusCode();
	}
}
