package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CreateIssuePage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SearchPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class LoginTest extends BaseTest {

    private DashboardPage loginAsAdmin() {
        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());

        loginPage.open();
        loginPage.login("admin", "admin123");
        dashboardPage.dismissOnboardingIfPresent();

        return dashboardPage;
    }

//    TC-001: Успешная авторизация в системе (Положительный тест).
    @Test
    public void testSuccessfulLogin_TC001() {
        System.out.println("TC-001: Успешная авторизация");

        DashboardPage dashboardPage = loginAsAdmin();

        Assert.assertTrue(dashboardPage.isDashboardLoaded(),
                "ОШИБКА: Дашборд не загрузился после успешного входа");

        System.out.println("TC-001 ПРОЙДЕН");
    }

//    ТC-002: Проверка отображения ошибки при вводе невалидных учетных данных (Data-Driven)
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

        Assert.assertFalse(dashboardPage.isDashboardLoaded(),
                "ОШИБКА: Произошел вход с неверными данными: " + login + "/" + password);

        System.out.println("TC-002 ПРОЙДЕН: Ошибка корректно показана");
    }

//    TC-003: Создание новой задачи (Положительный тест). Здесь прямая ссылка, чтобы не было флаки тестов
    @Test
    public void testCreateIssue_TC003() {
        System.out.println("TC-003: Создание новой задачи");

        loginAsAdmin();

        getDriver().get("http://localhost:8080/newIssue");
        System.out.println("Перешли на страницу создания задачи");

        CreateIssuePage createIssuePage = new CreateIssuePage(getDriver());
        createIssuePage.createIssue("Автотестом: Новая задача", "Это тестовое описание задачи");

        System.out.println("TC-003 ПРОЙДЕН: Задача создана");
    }

//    TC-004: Поиск созданной задачи по её идентификатору (Положительный тест).
    @Test
    public void testSearchIssue_TC004() {
        System.out.println("TC-004: Поиск созданной задачи");

        loginAsAdmin();

        String searchQuery = "DEMO-21";

        SearchPage searchPage = new SearchPage(getDriver());
        searchPage.openIssuesPage();
        searchPage.enterSearchQuery(searchQuery);

        boolean issueFound = searchPage.isIssueFound(searchQuery);
        Assert.assertTrue(issueFound, "Задача '" + searchQuery + "' не найдена в поиске!");

        System.out.println("TC-004 ПРОЙДЕН: Задача '" + searchQuery + "' найдена");
    }

    /**
     * TC-005: Выход из системы (Положительный тест).
     */
    @Test
    public void testLogout_TC005() {
        System.out.println("TC-005: Выход из системы");

        DashboardPage dashboardPage = loginAsAdmin();
        dashboardPage.logout();

        LoginPage loginPage = new LoginPage(getDriver());
        boolean isLoginPage = loginPage.isLoginPageLoaded();

        Assert.assertTrue(isLoginPage, "После выхода из системы не отобразилась страница логина");
        System.out.println("TC-005 ПРОЙДЕН: Успешный выход из системы");
    }
}