package cucumber.tests;
import core.driver.singleton.WebDriverSingleton;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

/**
 * Created by Dina_Abdykasheva on 8/21/2017.
 */

@CucumberOptions(strict = true, plugin = { "json:target/cucumber-report.json",
        "html:target/cucumber-report" }, tags = "@smokeTest", features = "src/main/resources/cucumber_feature/GMail_login.feature", glue = {
        "cucumber.steps" })
public class GMailLoginTest extends AbstractTestNGCucumberTests {
}
