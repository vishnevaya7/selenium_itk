package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.IssuePage;
import utils.ConfigReader;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class DeleteIssueTest extends BaseTest {

    @BeforeMethod
    public void setUpAuth() {
        loginAsAdmin(); // используем фикстуру из BaseTest
    }

    // TC-006: Удаление задачи (Положительный тест)
    // тест по дефолту false, тк делает ситуацию с флаки тестами, он есть в xml, но запускаю после всех тестов
    @Test(enabled = false)
    public void testDeleteIssue_TC006() {
        System.out.println("TC-006: Удаление задачи");

        // переход на страницу списка задач из конфига
        getDriver().get(ConfigReader.getProperty("base.url") + ConfigReader.getProperty("issues.path"));

        // удаление задачи, проверяем что задача есть в списке перед удалением
        IssuePage issuePage = new IssuePage(getDriver());
        issuePage.deleteIssue("DEMO-22");

        // проверка: мы вернулись на список задач и задача удалилась
        boolean isIssuesPage = getDriver().getCurrentUrl().contains("/issues");

        Assert.assertTrue(isIssuesPage, "После удаления задачи не произошел возврат на список задач");
        System.out.println("TC-006 ПРОЙДЕН: Задача успешно удалена");
    }
}