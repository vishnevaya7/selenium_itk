package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    protected static final String BASE_URL = "http://localhost:8080";

    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

//    настройка веб драйвера
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driverThreadLocal.set(driver);

        System.out.println("Браузер создан (Поток: " + Thread.currentThread().getId() + ")");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Браузер закрыт (Поток: " + Thread.currentThread().getId() + ")");
            } catch (Exception e) {
                System.err.println("Не удалось закрыть браузер: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}