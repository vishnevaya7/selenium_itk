package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CreateIssuePage;
import utils.ConfigReader;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class CreateIssueTest extends BaseTest {

    @BeforeMethod
    public void setUpAuth() {
        loginAsAdmin(); // используем фикстуру из BaseTest
    }

    // TC-003: Создание новой задачи (Положительный тест).
    @Test
    public void testCreateIssue_TC003() {
        System.out.println("TC-003: Создание новой задачи");

        // переход на страницу создания задачи по прямой ссылке из конфига
        getDriver().get(ConfigReader.getProperty("base.url") + ConfigReader.getProperty("new.issue.path"));
        System.out.println("Перешли на страницу создания задачи");

        // создание задачи
        CreateIssuePage createIssuePage = new CreateIssuePage(getDriver());
        createIssuePage.createIssue("Автотест: Новая задача", "Это тестовое описание задачи");

        System.out.println("TC-003 ПРОЙДЕН: Задача создана");
    }
}