package com.cu.cukes.runners;

import com.cu.cukes.AfterSuite;
import com.cu.cukes.CustomCucumberRunner;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import com.cu.util.DriverFactory;

/**
 * <p>Cucumber run class</p>
 *
 * <p>Sets the tags to be used within the integration (build) tests,
 * where the classname must contain the phrase 'test' to be picked
 * up in the build Junit runner.</p>
 *
 * <p>Also configures the logging/output cucumber settings, specifies
 * the location of the feature files and implements functionality
 * which requires the @BeforeSuite and @AfterSuite annotations defined in
 * other classes (e.g. Closing the RemoteWebDriver instance after
 * ALL tests have run).</p>
 *
 */
@RunWith(CustomCucumberRunner.class)
@CucumberOptions(
		publish = true,
		monochrome = true,
		plugin = {
				"pretty",
				"html:target/cucumber-report/cucumber-html-report",
				"html:target/cucumber-report/cucumber.html",
				"json:target/cucumber-report/cucumber.json"},
		features = "src/main/resources/features",
		glue = {"com.cu.cukes.stepdefs", "com.cu.util"},
		tags = "@Reg"
//		tags= "@Test01"
		// Use the tags to specify which tests to run.
)
public class RunCukeTest {
	/**
	 * close the RemoteWebDriver after ALL tests are run -
	 * closing after each test causes numerous problems and would lead to
	 * a massive runtime overhead (destroying and recreating the browser instance on every test).
	 * Only works on LOCAL TEST RUNS due to Cucumber Parallel running
	 **/

	@AfterSuite
	public static void tearDown() {
		DriverFactory.closeDriver();
	}
}