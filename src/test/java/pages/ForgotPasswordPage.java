package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;

public class ForgotPasswordPage {
    private final WebDriver driver;

    private final By recoverButton = By.xpath("//button[text()='Восстановить']");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Клик по ссылке 'Войти'")
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    @Step("Проверка, что страница восстановления пароля открыта")
    public boolean isForgotPasswordPageDisplayed() {
        return driver.findElement(recoverButton).isDisplayed();
    }
}