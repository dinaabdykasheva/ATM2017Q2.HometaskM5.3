package cucumber.tests;
import core.driver.singleton.WebDriverSingleton;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;

/**
 * Created by Dina_Abdykasheva on 8/21/2017.
 */

@CucumberOptions(strict = true, plugin = { "json:target/cucumber-report.json",
        "html:target/cucumber-report" }, features = "src/main/resources/cucumber_feature/GMail.feature", glue = {
        "cucumber.steps" })
public class GMailTest extends AbstractTestNGCucumberTests {
    @AfterClass(description = "closeDriver")
    public void closeDriver() {
        WebDriverSingleton.kill();
    }
}

