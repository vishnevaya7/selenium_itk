package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.IssuePage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class SearchIssueTest extends BaseTest {

    @BeforeMethod
    public void setUpAuth() {
        loginAsAdmin(); // используем фикстуру из BaseTest
    }

    // TC-004: Поиск созданной задачи
    @Test
    public void testSearchIssue_TC004() {
        System.out.println("TC-004: Поиск созданной задачи");

        IssuePage issuePage = new IssuePage(getDriver());
        issuePage.openIssuesPage(); // URL внутри этого метода тоже должен брать из ConfigReader (см. Шаг 3)

        String searchQuery = "DEMO-20";
        issuePage.enterSearchQuery(searchQuery);
        boolean issueFound = issuePage.isIssueFound(searchQuery);

        Assert.assertTrue(issueFound, "Задача '" + searchQuery + "' не найдена в поиске!");
        System.out.println("TC-004 ПРОЙДЕН: Задача '" + searchQuery + "' найдена");
    }
}