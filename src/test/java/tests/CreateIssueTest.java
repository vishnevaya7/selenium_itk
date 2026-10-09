package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CreateIssuePage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class CreateIssueTest extends BaseTest {

// TC-003: Создание новой задачи (Положительный тест).

    @Test
    public void testCreateIssue_TC003() {
        System.out.println("TC-003: Создание новой задачи");

        // авторизация
        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        loginPage.open();
        loginPage.login("admin", "admin123");

        // переход на страницу создания задачи по прямой ссылке.
        getDriver().get("http://localhost:8080/newIssue");
        System.out.println("Перешли на страницу создания задачи");

        // 3. Создание задачи
        CreateIssuePage createIssuePage = new CreateIssuePage(getDriver());
        createIssuePage.createIssue("Автотест: Новая задача", "Это тестовое описание задачи");

        System.out.println("TC-003 ПРОЙДЕН: Задача создана");
    }
}