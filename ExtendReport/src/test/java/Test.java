import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by PTL 5 on 9/4/2017.
 */
public class Test {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void beforeSuite(){
        extent = new ExtentReports("D:\\extendReport.html",true);
        extent.loadConfig(new File("extend_config.xml"));
    }

    @BeforeMethod
    public void beforeMethod(Method method){
        test = extent.startTest((this.getClass().getSimpleName() + " :: " + method.getName()),method.getName());
        test.assignAuthor("Sachintha Sri Lakmal");
        test.assignCategory("Somke Test");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        test.log(LogStatus.PASS,"Browser Launched Sucsessfully");
    }

    @org.testng.annotations.Test
    public void extendReportTest() throws InterruptedException {
        driver.get("www.google.com");
        String currentUrl = driver.getCurrentUrl();
        test.log(LogStatus.PASS,"The Current URL of The Web Page" + currentUrl);
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("a");
        driver.findElement(By.id("btnLogin")).click();
        WebElement error  = driver.findElement(By.xpath("//*[@id=\"spanMessage\"]"));
        String actualResult = error.getText();
        String expectedResult = "";

        if(actualResult.equalsIgnoreCase(expectedResult)){
            test.log(LogStatus.PASS, "Actual Result :: " + actualResult);
        }else {
            test.log(LogStatus.FAIL,"Actual Result::" +actualResult+"Expected Result::" +expectedResult);
        }
    }
    @AfterMethod
    public void afterMethod(){
        driver.close();
        driver.quit();
        test.log(LogStatus.PASS,"Browser Close Sucessfully");
        extent.endTest(test);
    }
    @AfterSuite
    public void afterSuit(){
        extent.flush();
        extent.close();
    }

}
