package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.xpath("//fieldset[1]//input");
    private final By emailInput = By.xpath("//fieldset[2]//input");
    private final By passwordInput = By.xpath("//fieldset[3]//input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By passwordError = By.xpath("//fieldset[3]//p[contains(@class, 'input__error')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Step("Ожидание загрузки страницы регистрации")
    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    @Step("Ввод имени: {name}")
    public void enterName(String name) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(nameInput));
        element.click();
        element.sendKeys(name);
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        element.click();
        element.sendKeys(email);
    }

    @Step("Ввод пароля: {password}")
    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        element.click();
        element.sendKeys(password);
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public void clickRegisterButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        element.click();
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        element.click();
    }

    @Step("Регистрация пользователя")
    public void registerUser(String name, String email, String password) {
        waitForLoad();
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }

    @Step("Проверка ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).isDisplayed();
    }

    @Step("Проверка, что страница регистрации открыта")
    public boolean isRegisterPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton)).isDisplayed();
    }
}