package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ConfigReader;

public class SearchPage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search for text or add a filter']")
    private WebElement searchInput;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void openIssuesPage() {
        // берем URL из конфига вместо хардкода
        driver.get(ConfigReader.getProperty("base.url") + ConfigReader.getProperty("issues.path"));
        System.out.println("Перешли на страницу Issues");
    }

    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        // ждем появления результатов поиска
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("table, [data-test='ring-table']")
        ));

        System.out.println("Введен поисковый запрос: " + query);
    }

    //проверка что задача есть, поиск идет по id или по названию
    public boolean isIssueFound(String taskName) {
        try {
            By locator = By.xpath("//tr[contains(@data-test, 'ring-table-row " + taskName + "')]");
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("Задача '" + taskName + "' не найдена: " + e.getMessage());
            return false;
        }
    }
}