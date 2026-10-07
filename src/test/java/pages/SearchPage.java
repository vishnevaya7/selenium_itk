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

    @FindBy(css = "input[placeholder='Search for text or add a filter']")
    private WebElement searchInput;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает страницу со списком задач (Issues).
     */
    public void openIssuesPage() {
        driver.get("http://localhost:8080/issues");
        System.out.println("Перешли на страницу Issues");
    }

    /**
     * Вводит поисковый запрос и инициирует поиск.
     * Использует комбинацию Ctrl+A + Backspace для гарантированной очистки поля ввода.
     */
    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        System.out.println("Введен поисковый запрос: " + query);
    }

    /**
     * Проверяет наличие задачи с указанным названием или ID в результатах поиска.
     */
    public boolean isIssueFound(String taskName) {
        try {
            By locator = By.xpath("//a[contains(text(), '" + taskName + "')]");
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            System.out.println("Задача '" + taskName + "' не найдена: " + e.getMessage());
            return false;
        }
    }
}