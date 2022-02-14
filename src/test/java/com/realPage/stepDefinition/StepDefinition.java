package com.realPage.stepDefinition;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.realPage.pages.Herokuapp;
import com.realPage.util.Configuration;
import com.realPage.util.GlobalData;
import com.realPage.util.ReusableFunctions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.core.cli.Main;
import junit.framework.Assert;

public class StepDefinition {
	public WebDriver driver;
	public StepDefinition def;
	public Scenario scenario;

	private Herokuapp herokuapp;

	private static final Logger log = LoggerFactory.getLogger(StepDefinition.class);

	// Cucumber Hooks - @After()
	@After(order=1)
	public void tearDown() {
		driver.quit();
	}
	
	@After(order=2)
	public void tearDown(Scenario scenario) {
	    if (scenario.isFailed()) {
	      // Take a screenshot...
	      final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	      scenario.embed(screenshot, "image/png"); 
	    }
	}

	// Cucumber Hooks - @Before()

	@Before(order = 1)
	public void Setup(Scenario scenario) {
		this.scenario = scenario;
	}

	@Before(order = 2)
	public void beforeScenario() {

		System.out.println("Reading Config...");
		Configuration config = new Configuration(".\\config.properties");
		config.ReadProperty();

		GlobalData.DataFilePath = GlobalData.ConfigData.get("testDataFilePath");

		if (GlobalData.ConfigData.get("browser").equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", GlobalData.ConfigData.get("ChromePath"));
			driver = new ChromeDriver();
			System.out.println("Launched " + GlobalData.ConfigData.get("browser") + " Browser successfully");
		} else if (GlobalData.ConfigData.get("browser").equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", GlobalData.ConfigData.get("FirefoxPath"));
			System.out.println("Launched " + GlobalData.ConfigData.get("browser") + " Browser successfully");
			driver = new FirefoxDriver();
		} else if (GlobalData.ConfigData.get("browser").equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", GlobalData.ConfigData.get("EdgePath"));
			driver = new EdgeDriver();
			System.out.println("Launched " + GlobalData.ConfigData.get("browser") + " Browser successfully");
		} else
			System.out.println("Invalid browser");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	@Given("Scenario Name as {string}")
	public void scenario_Name_as(String scenarioName) {
//		GlobalData.TestCaseName = scenarioName;
		GlobalData.TestScenarioName = scenarioName;
		herokuapp = new Herokuapp(driver);
		herokuapp.clickLinkbyValue(driver, scenarioName);
		System.out.println("Am here: " + scenarioName);
	}

	@Given("DataSheet as {string}")
	public void datasheet_as(String DataSheetName) {
		GlobalData.DataSheetName = DataSheetName;
		ReusableFunctions.readTestData(GlobalData.TestScenarioName);
	}

	@Given("User navigates to Url")
	public void user_navigates_to_Url() {
		driver.get(GlobalData.ConfigData.get("realpage_url"));
		String title = driver.getTitle();
		System.out.println("Title of page is: " + title);
		Assert.assertEquals(title, "The Internet");
	}

	@When("User logs into the application and validate home page")
	public void user_logs_into_the_application_and_validate_home_page() {
		herokuapp = new Herokuapp(driver);
		String status = herokuapp.login(driver, scenario);
		Assert.assertEquals(status, "pass");
	}

	@When("Validate user logs out of the application")
	public void validate_user_logs_out_of_the_application() {
		herokuapp = new Herokuapp(driver);
		String status = herokuapp.logout(driver, scenario);
		Assert.assertEquals(status, "pass");
	}

	@When("Navigate to new window and return to original window")
	public void Navigate_to_new_window_and_return_to_original_window() {
		herokuapp = new Herokuapp(driver);
		String status = herokuapp.handleMultipleWins(driver, scenario);
		Assert.assertEquals(status, "pass");
	}

	@When("Provide email and click on button")
	public void provide_email_and_click_on_button() {
		herokuapp = new Herokuapp(driver);
		String status = herokuapp.forgotPassword(driver, scenario);
		Assert.assertEquals(status, "pass");
	}

	@Given("POST Method")
	public void post_Method() {
		herokuapp = new Herokuapp(driver);
		int status = herokuapp.postRequest(scenario);
		Assert.assertEquals(status, 201);
	}


}
