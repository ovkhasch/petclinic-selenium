package selenium.automation.test;


import java.io.File;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;

public class PetClinicTest {
  //private WebDriver driver;
  private RemoteWebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {

    String remote_url_chrome = "http://localhost:4444/";
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless", "--disable-gpu");
    driver = new RemoteWebDriver(new URL(remote_url_chrome), options);
//    options.addArguments("--headless", "--disable-gpu", "--remote-debugging-port=9222");
//    driver = new ChromeDriver(options);

    //	DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
//    driver = new PhantomJSDriver(capabilities);
//    baseUrl = "http://ec2-3-232-123-215.compute-1.amazonaws.com:31090";
    baseUrl = System.getProperty("petclinicUrl");
    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

//  public<T> Object getScreenshotAs(OutputType<T> outputType) {
//    Augmenter augmenter = new Augmenter();
//    TakesScreenshot ts = (TakesScreenshot) augmenter.augment(driver);
//    return ts.getScreenshotAs(outputType);
//  }

  @Test
  public void testNewOwnerForm() throws Exception {
    if (baseUrl == null || baseUrl.isEmpty()) {
      fail("Empty base URL");
    }
    System.out.println("Base URL: " + baseUrl);
    
    driver.get(baseUrl);    
    driver.manage().window().setSize(new Dimension(1366, 720));
    driver.get(baseUrl + "/owners/find");
    File screenshot = driver.getScreenshotAs(OutputType.FILE);
    FileUtils.copyFile(screenshot, new File("screenshot1.png"));

    //driver.findElement(By.cssSelector("li:nth-child(3) span:nth-child(2)")).click(); // Find owners
    driver.findElement(By.linkText("Add Owner")).click();
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("siva");
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("chedde");
    driver.findElement(By.id("address")).clear();
    driver.findElement(By.id("address")).sendKeys("electronic city");
    driver.findElement(By.id("city")).clear();
    driver.findElement(By.id("city")).sendKeys("bangalore");
    driver.findElement(By.id("telephone")).clear();
    driver.findElement(By.id("telephone")).sendKeys("99999");
    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
    driver.get(baseUrl + "/owners/find");
    //driver.findElement(By.cssSelector("li:nth-child(3) span:nth-child(2)")).click(); // Find owners
//    driver.findElement(By.linkText("Find owners")).click();
    driver.findElement(By.name("lastName")).clear();
    driver.findElement(By.name("lastName")).sendKeys("chedde");
    driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
