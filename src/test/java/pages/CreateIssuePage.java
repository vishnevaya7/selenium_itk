package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateIssuePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "[data-test='summary']")
    private WebElement summaryField;

    @FindBy(css = "[data-test='wysiwyg-editor-content']")
    private WebElement descriptionField;

    @FindBy(css = "[data-test='submit-button']")
    private WebElement createButton;

    public CreateIssuePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    public void enterSummary(String summary) {
        wait.until(ExpectedConditions.visibilityOf(summaryField));
        summaryField.clear();
        summaryField.sendKeys(summary);
    }

    public void enterDescription(String description) {
        wait.until(ExpectedConditions.visibilityOf(descriptionField));
        descriptionField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        descriptionField.sendKeys(description);
    }

    public void clickCreate() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton));
        createButton.click();
    }

    public void createIssue(String summary, String description) {
        enterSummary(summary);
        enterDescription(description);
        clickCreate();
        System.out.println("Задача успешно создана: " + summary);
    }
}