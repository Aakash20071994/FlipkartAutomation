package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.internal.EclipseInterface;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Durations;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        driver.manage().window().maximize();
    }
    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start of TestCase01");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Wrappers.Url(driver, "https://www.flipkart.com/");
        Wrappers.search(driver, "Washing Machine");
        WebElement popularityTab = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Popularity']")));
        popularityTab.click();
        Thread.sleep(4000);
        List<WebElement> ratings = driver.findElements(By.xpath("//span[@class='Y1HWO0']//div[@class='XQDdHH']"));
        Thread.sleep(4000);
        int count = 0;
        for (int i = 0; i < ratings.size(); i++) {
            String text = ratings.get(i).getText();
            Thread.sleep(2000);
            double rating = Double.parseDouble(text);
            if (rating <= 4.0) {
                count = count + 1;
            }

        }
        System.out.println("Count Less than or equlas 4:" + count);

        System.out.println("TestCase01:Pass");
        System.out.println("End of TestCase");

    }

   @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start of TestCase02");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Wrappers.Url(driver, "https://www.flipkart.com/");
        try {
            WebElement popupCloseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='_30XB9F']")));
            
            if (popupCloseButton.isDisplayed()) {
                popupCloseButton.click();  // Close the pop-up
                System.out.println("Login pop-up closed.");
            }
        } catch (Exception e) {
            System.out.println("Login pop-up not present, continuing with the test.");
        }
        Wrappers.search(driver, "iPhone");
        Thread.sleep(4000);
        List<WebElement> listOfitems = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='KzDlHZ']")));
        List<WebElement> discount = wait
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='UkUFwK']")));
        int itemCount = Math.min(listOfitems.size(), discount.size());

        for (int i = 0; i < itemCount; i++) {
            String listOfItemText = listOfitems.get(i).getText();
            String TextofDiscount = discount.get(i).getText();
            String discountNum = TextofDiscount.replaceAll("[^0-9.]", "");
            int discontElement = Integer.parseInt(discountNum);
            if (discontElement >= 17) {
                System.out.println("Title:" + listOfItemText + " " + "Discount:" + discontElement + "%");
            }

        }
        System.out.println("TestCase02:Pass");
        System.out.println("End of TestCase");

    }
    @Test
    public void testCase03() throws InterruptedException{
        System.out.println("Start of TestCase03");
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        Wrappers.Url(driver, "https://www.flipkart.com/");

        try {
            WebElement popupCloseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='_30XB9F']")));
            
            if (popupCloseButton.isDisplayed()) {
                popupCloseButton.click();  // Close the pop-up
                System.out.println("Login pop-up closed.");
            }
        } catch (Exception e) {
            System.out.println("Login pop-up not present, continuing with the test.");
        }
        Wrappers.search(driver, "Coffee Mug");
        Thread.sleep(2000);
        Wrappers.sortByRating(driver, "4");
        Thread.sleep(5000);
        Wrappers.review(driver);
        System.out.println("TestCase03:Pass");
        System.out.println("End of TestCase");
       
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}