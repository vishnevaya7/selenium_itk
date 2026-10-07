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

    // Локатор поля для ввода логина
    @FindBy(css = "[data-test='username-field']")
    private WebElement usernameField;

    // Локатор поля для ввода пароля
    @FindBy(css = "[data-test='password-field']")
    private WebElement passwordField;

    // Локатор кнопки "Log in", по-приколу через относительный локатор
    @FindBy(xpath = "//button[@data-test='login-button']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Открывает страницу логина
    public void open() {
        driver.get("http://localhost:8080");
    }

    // Вводит логин
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    // Водит пароль
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Жмет кнопку LogIn
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    // Вызов полной авторизации
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
