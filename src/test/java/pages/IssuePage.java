package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        driver.get("http://localhost:8080/issues");
    }

    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.click();
        searchInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);

        try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
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

        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}