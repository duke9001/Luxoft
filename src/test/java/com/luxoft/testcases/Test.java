package com.luxoft.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test {

	// This class contains everything needed to run a full visual test against the ACME bank site.
    // It runs the test once locally,
    // and then it performs cross-browser testing against multiple unique browsers in Applitools Ultrafast Grid.
    // It runs the test from a main function, not through a test framework.

    // Test constants
    private final static BatchInfo BATCH = new BatchInfo("Example: Selenium Java Basic with the Ultrafast Grid");
    
    public static void main(String [] args) {

        VisualGridRunner runner = null;
        Eyes eyes = null;
        WebDriver driver = null;

        try {
            // The following steps set up Applitools for testing.

            // Create the runner for the Ultrafast Grid.
            // Concurrency refers to the number of visual checkpoints Applitools will perform in parallel.
            // Warning: If you have a free account, then concurrency will be limited to 1.
            runner = new VisualGridRunner(new RunnerOptions().testConcurrency(5));

            // Create the Applitools Eyes object connected to the VisualGridRunner and set its configuration.
            eyes = new Eyes(runner);

            // Create a configuration for Applitools Eyes.
            Configuration config = eyes.getConfiguration();

            // Set the Applitools API key so test results are uploaded to your account.
            // If you don't explicitly set the API key with this call,
            // then the SDK will automatically read the `APPLITOOLS_API_KEY` environment variable to fetch it.
            config.setApiKey(System.getenv("6UL100uMbLBr107108jjxTOxYhlYqBXHVN6zwRYwaTg104gCnFc110"));

            // Create a new batch for tests.
            // A batch is the collection of visual tests.
            // Batches are displayed in the dashboard, so use meaningful names.
            config.setBatch(BATCH);

            // Add 3 desktop browsers with different viewports for cross-browser testing in the Ultrafast Grid.
            // Other browsers are also available, like Edge and IE.
            config.addBrowser(800, 600, BrowserType.CHROME);
            config.addBrowser(1600, 1200, BrowserType.FIREFOX);
            config.addBrowser(1024, 768, BrowserType.SAFARI);

            // Add 2 mobile emulation devices with different orientations for cross-browser testing in the Ultrafast Grid.
            // Other mobile devices are available, including iOS.
            config.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);
            config.addDeviceEmulation(DeviceName.Nexus_10, ScreenOrientation.LANDSCAPE);

            // Set the configuration for Eyes
            eyes.setConfiguration(config);

            // Open the browser with the ChromeDriver instance.
            // Even though this test will run visual checkpoints on different browsers in the Ultrafast Grid,
            // it still needs to run the test one time locally to capture snapshots.
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

            // Set an implicit wait of 10 seconds.
            // For larger projects, use explicit waits for better control.
            // https://www.selenium.dev/documentation/webdriver/waits/
            // The following call works for Selenium 4:
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // If you are using Selenium 3, use the following call instead:
            // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


            // The following steps are a test covering login for the Applitools demo site, which is a dummy banking app.
            // The interactions use typical Selenium WebDriver calls,
            // but the verifications use one-line snapshot calls with Applitools Eyes.
            // If the page ever changes, then Applitools will detect the changes and highlight them in the dashboard.
            // Traditional assertions that scrape the page for text values are not needed here.

            // Open Eyes to start visual testing.
            // It is a recommended practice to set all four inputs:
            eyes.open(
                    driver,                                         // WebDriver object to "watch"
                    "ACME Bank Web App",                            // The name of the app under test
                    "Log into bank account",                        // The name of the test case
                    new RectangleSize(1200, 600));     // The viewport size for the local browser

            // Load the login page.
            driver.get("https://demo.applitools.com");

            // Verify the full login page loaded correctly.
            eyes.check(Target.window().fully().withName("Login page"));

            // Perform login.
            driver.findElement(By.id("username")).sendKeys("applibot");
            driver.findElement(By.id("password")).sendKeys("I<3VisualTests");
            driver.findElement(By.id("log-in")).click();

            // Verify the full main page loaded correctly.
            // This snapshot uses LAYOUT match level to avoid differences in closing time text.
            eyes.check(Target.window().fully().withName("Main page").layout());

            // Close Eyes to tell the server it should display the results.
            eyes.closeAsync();
        }
        catch (Exception e) {
            // Dump any errors and abort any tests.
            e.printStackTrace();
            if (eyes != null)
                eyes.abortAsync();
        }

        try {
            // No matter what, perform cleanup.
            if (driver != null)
                driver.quit();

            // Close the batch and report visual differences to the console.
            // Note that it forces execution to wait synchronously for all visual checkpoints to complete.
            if (runner != null) {
                TestResultsSummary allTestResults = runner.getAllTestResults();
                System.out.println(allTestResults);
            }
        }
        catch (Exception e) {
            // Dump any cleanup errors.
            e.printStackTrace();
        }

        // Always force execution to end.
        System.exit(0);
    }

}
