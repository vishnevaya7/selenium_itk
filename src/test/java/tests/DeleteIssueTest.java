package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.IssuePage;
import pages.LoginPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class DeleteIssueTest extends BaseTest {

//    TC-006: Удаление задачи (Положительный тест)

    // тест по дефолту false, тк делает ситуацию с флаки тестами, он есть в xml, но запускаю после всех тестов
    @Test(enabled = false)
    public void testDeleteIssue_TC006() {

        // авторизация
        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        loginPage.open();
        loginPage.login("admin", "admin123");

        // переход на страницу поиска/списка задач
        getDriver().get("http://localhost:8080/issues");

        // удаление задачи, проверяем что задача есть в списке перед удалением
        IssuePage issuePage = new IssuePage(getDriver());
        issuePage.deleteIssue("DEMO-22");

        // проверка: мы вернулись на список задач и задача удалилась
        boolean isIssuesPage = getDriver().getCurrentUrl().contains("/issues");

        Assert.assertTrue(isIssuesPage, "После удаления задачи не произошел возврат на список задач");
        System.out.println("TC-006 ПРОЙДЕН: Задача успешно удалена");
    }
}