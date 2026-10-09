package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class LogoutTest extends BaseTest {

//    TC-005: Выход из системы (Положительный тест).

    @Test
    public void testLogout_TC005() {
        System.out.println("TC-005: Выход из системы");

        // авторизация
        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        loginPage.open();
        loginPage.login("admin", "admin123");

        // выполнение выхода
        dashboardPage.logout();

        // проверка редиректа на страницу логина
        boolean isLoginPage = loginPage.isLoginPageLoaded();
        Assert.assertTrue(isLoginPage, "После выхода из системы не отобразилась страница логина");

        System.out.println("TC-005 ПРОЙДЕН: Успешный выход из системы");
    }
}