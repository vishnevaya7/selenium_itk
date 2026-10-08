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

    @FindBy(xpath = "//div[contains(@class, 'yt-avatar-user')] | //span[contains(text(), 'admin')]")
    private WebElement userAvatar;

    @FindBy(xpath = "//button[@aria-label='Close'] | //button[contains(@class, 'close')]")
    private WebElement closeOnboardingButton;

    @FindBy(xpath = "//button[contains(text(), 'Skip')]")
    private WebElement skipTourButton;

    @FindBy(xpath = "//button[contains(text(), 'Got it')]")
    private WebElement gotItButton;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

//    закрытие онбординга, тк в чистом новом браузере тест выполянется
    public void dismissOnboardingIfPresent() {
        try {
            if (skipTourButton.isDisplayed()) {
                skipTourButton.click();
                wait.until(ExpectedConditions.invisibilityOf(skipTourButton));
            } else if (closeOnboardingButton.isDisplayed()) {
                closeOnboardingButton.click();
                wait.until(ExpectedConditions.invisibilityOf(closeOnboardingButton));
            }

            // Закрытие уведомления "Tour skipped", если оно появилось
            try {
                if (gotItButton.isDisplayed()) {
                    gotItButton.click();
                }
            } catch (Exception e) {
                // Уведомление может отсутствовать, это не является ошибкой
            }
        } catch (Exception e) {
            // Онбординг уже закрыт или не отображается на данной странице
        }
    }
//    заходит по аватару пользователя
    public boolean isDashboardLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(userAvatar));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(userAvatar));
        userAvatar.click();

        // небольшая пауза для гарантированной отрисовки всплывающего меню в DOM
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Log out') or contains(., 'Выйти')]")
        ));
        logoutButton.click();
    }
}