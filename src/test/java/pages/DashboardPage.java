package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локатор аватара пользователя
    @FindBy(xpath = "//div[contains(@class, 'yt-avatar-user')] | //span[contains(text(), 'admin')]")
    private WebElement userAvatar;

    // Локатор кнопки закрытия онбординга (крестик)
    @FindBy(xpath = "//button[@aria-label='Close'] | //button[contains(@class, 'close')]")
    private WebElement closeOnboardingButton;

    // Локатор кнопки пропуска тура "Skip the tour"
    @FindBy(xpath = "//button[contains(text(), 'Skip')]")
    private WebElement skipTourButton;

    // Кнопка "Got it" для закрытия уведомления об пропуске тура
    @FindBy(xpath = "//button[contains(text(), 'Got it')]")
    private WebElement gotItButton;



    // Кнопка "New card"
    @FindBy(xpath = "//button[contains(text(), 'New card')]")
    private WebElement newCardButton;

    // Кнопка "Create" в левом меню
    @FindBy(xpath = "//span[contains(text(), 'Create')]")
    private WebElement createMenuButton;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // закрывает онбординг и уведомления, тк у меня каждый новый браузер без куки и независымый
    public void dismissOnboardingIfPresent() {
        try {
            if (skipTourButton.isDisplayed()) {
                skipTourButton.click();
                System.out.println("Онбординг пропущен (кнопка Skip)");
                wait.until(ExpectedConditions.invisibilityOf(skipTourButton));
            } else if (closeOnboardingButton.isDisplayed()) {
                closeOnboardingButton.click();
                System.out.println("Онбординг закрыт (крестик)");
                wait.until(ExpectedConditions.invisibilityOf(closeOnboardingButton));
            }

            // Закрываем уведомление "Tour skipped" (кнопка Got it)
            try {
                if (gotItButton.isDisplayed()) {
                    gotItButton.click();
                    System.out.println("Уведомление закрыто (Got it)");
                }
            } catch (Exception e) {
                // пупу
            }

        } catch (Exception e) {
            System.out.println("Онбординг не найден (уже закрыт)");
        }
    }

    // проверка, что мы успешно вошли (аватар пользователя на странице)
    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(userAvatar));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // жмет кнопку "New card"
    public void clickNewCard() {
        wait.until(ExpectedConditions.elementToBeClickable(newCardButton));
        newCardButton.click();
        System.out.println("Нажата кнопка 'New card'");
    }

    // жмет кнопку "Create" в левом меню
    public void clickCreateMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(createMenuButton));
        createMenuButton.click();
        System.out.println("Нажата кнопка 'Create' в меню");
    }

    public void clickNewIssueFromMenu() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'New issue')] | //span[contains(text(), 'New issue')]")
        ));
        WebElement newIssueOption = driver.findElement(By.xpath("//div[contains(text(), 'New issue')] | //span[contains(text(), 'New issue')]"));
        newIssueOption.click();
        System.out.println("Выбрано 'New issue' из меню");
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userAvatar));
        userAvatar.click();
        System.out.println("Открыто меню пользователя");

        //Крошечная пауза, чтобы всплывающее меню нава успело отрисоваться в DOM
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        org.openqa.selenium.WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath("//button[contains(., 'Log out') or contains(., 'Выйти')]")
        ));

        logoutButton.click();
        System.out.println("Выполнен выход из системы");
    }

}