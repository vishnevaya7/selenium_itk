package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

import java.time.Duration;

public class BaseTest {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // возвращает экземпляр WebDriver, привязанный к текущему потоку
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // создание и инициализация браузера перед каждым тестом
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

    // фикстура для авторизации под админом (берет URL из конфига)
    protected void loginAsAdmin() {
        getDriver().get(ConfigReader.getProperty("base.url") + ConfigReader.getProperty("login.path"));

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage(getDriver());
        dashboardPage.isDashboardLoaded();
    }

    // закрытие браузера после завершения каждого теста
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Браузер закрыт (Поток: " + Thread.currentThread().getId() + ")");
            } catch (Exception e) {
                System.err.println("Браузер не закрыт: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}