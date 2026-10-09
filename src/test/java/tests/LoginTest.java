package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class LoginTest extends BaseTest {

//    TC-001: Успешная авторизация в системе (Положительный тест).

    @Test
    public void testSuccessfulLogin_TC001() {
        System.out.println("TC-001: Успешная авторизация");

        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());

        loginPage.open();
        loginPage.login("admin", "admin123");

        Assert.assertTrue(dashboardPage.isDashboardLoaded(),
                "ОШИБКА: Дашборд не загрузился после успешного входа");

        System.out.println("TC-001 ПРОЙДЕН");
    }

//    TC-002: Проверка отображения ошибки при вводе невалидных учетных данных (Data-Driven).

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][] {
                {"admin", "wrongpass"},
                {"wrong", "admin123"},
                {"", "admin123"}
        };
    }

    @Test(dataProvider = "invalidLoginData")
    public void testWrongPassword_TC002(String login, String password) {
        System.out.println("TC-002: Ошибка при неверных данных");
        System.out.println("Тестовые данные: login='" + login + "', password='" + password + "'");

        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());

        loginPage.open();
        loginPage.login(login, password);

        // проверяем, что НЕ зашли
        Assert.assertFalse(dashboardPage.isDashboardLoaded(),
                "ОШИБКА: Произошел вход с неверными данными: " + login + "/" + password);

        System.out.println("TC-002 ПРОЙДЕН: Ошибка корректно показана");
    }
}