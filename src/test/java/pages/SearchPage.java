package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поисковая строка
    @FindBy(css = "input[placeholder='Search for text or add a filter']")
    private WebElement searchInput;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void openIssuesPage() {
        driver.get("http://localhost:8080/issues");
        System.out.println("Перешли на страницу Issues");
    }

    // Ввести поисковый запрос и нажать Enter
    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click(); // Сначала кликаем, чтобы получить фокус

        // Надежная очистка: выделяем всё (Ctrl+A) и удаляем, это работает в 100% случаев
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        // Даем YouTrack 3 сек на перерисовку таблицы после поиска
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(" Введен поисковый запрос: " + query);
    }

    // Проверить, что задача с ожидаемым названием найдена в результатах
    public boolean isIssueFound(String taskName) {
        try {
            // Ищем ссылку на задачу, которая содержит искомый текст
            // (этот локатор сработает и для ID "DEMO-20", и для названия "что-то")
            By locator = By.xpath("//a[contains(text(), '" + taskName + "')]");
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("Задача '" + taskName + "' не найдена: " + e.getMessage());
            return false;
        }
    }
}