package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateIssuePage extends BasePage {

    @FindBy(xpath = "//textarea[@data-test='summary']")
    private WebElement summaryField;

    //
    @FindBy(xpath = "//div[@data-test='wysiwyg-editor-content']")
    private WebElement descriptionField;

    @FindBy(xpath = "//button[@data-test='submit-button']")
    private WebElement createButton;

    public CreateIssuePage(WebDriver driver) {
        super(driver);
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