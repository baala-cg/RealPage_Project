package com.realPage.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/java"},
		glue = {"com.realPage.stepDefinition"},
		plugin = {"pretty","json:target/cucumber.json"}
//		tags="@RESTAPI"
		)

public class TestRunner {

}
