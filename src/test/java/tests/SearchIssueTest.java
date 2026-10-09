package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.IssuePage;
import pages.LoginPage;
import utils.ScreenshotListener;

@Listeners(ScreenshotListener.class)
public class SearchIssueTest extends BaseTest {


//    TC-004: Поиск созданной задачи

    @Test
    public void testSearchIssue_TC004() {
        System.out.println("TC-004: Поиск созданной задачи");

        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        loginPage.open();
        loginPage.login("admin", "admin123");

        IssuePage issuePage = new IssuePage(getDriver());


        issuePage.openIssuesPage();

        String searchQuery = "DEMO-20";
        issuePage.enterSearchQuery(searchQuery);
        boolean issueFound = issuePage.isIssueFound(searchQuery);

        Assert.assertTrue(issueFound, "Задача '" + searchQuery + "' не найдена в поиске!");
        System.out.println("TC-004 ПРОЙДЕН: Задача '" + searchQuery + "' найдена");
    }
}