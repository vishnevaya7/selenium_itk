package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ConfigReader;

public class IssuePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search for text or add a filter']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@aria-label='Show more']")
    private WebElement moreOptionsButton;

    @FindBy(xpath = "//button[contains(., 'Delete issue')]")
    private WebElement deleteIssueButton;

    @FindBy(xpath = "//button[@data-test='confirm-ok-button']")
    private WebElement confirmDeleteButton;

    public IssuePage(WebDriver driver) {
        super(driver);
    }

    public void openIssuesPage() {
        // берем URL из конфига вместо хардкода
        driver.get(ConfigReader.getProperty("base.url") + ConfigReader.getProperty("issues.path"));
    }

    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        // ждем пока результаты поиска обновятся
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(searchInput, query)
        ));
    }

    public boolean isIssueFound(String taskName) {
        try {
            By locator = By.xpath("//a[contains(text(), '" + taskName + "')]");
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBackOnIssuesPage() {
        return driver.getCurrentUrl().contains("issues");
    }

    public void deleteIssue(String issueId) {
        openIssuesPage();
        enterSearchQuery(issueId);

        wait.until(ExpectedConditions.elementToBeClickable(moreOptionsButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteIssueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();

        // ждем пока задача исчезнет со страницы
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//a[contains(text(), '" + issueId + "')]")
        ));
    }
}