package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "[data-test='username-field']")
    private WebElement usernameField;

    @FindBy(css = "[data-test='password-field']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@data-test='login-button']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /**
     * Открывает главную страницу приложения (страницу авторизации).
     */
    public void open() {
        driver.get("http://localhost:8080");
    }

    /**
     * Вводит имя пользователя в соответствующее поле.
     */
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /**
     * Вводит пароль в соответствующее поле.
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Нажимает кнопку входа в систему.
     */
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    /**
     * Выполняет полный процесс авторизации.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Проверяет, что страница авторизации успешно загружена.
     */
    public boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}