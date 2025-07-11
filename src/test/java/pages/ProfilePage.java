package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By profileSection = By.xpath("//a[contains(@class, 'Account_link') and contains(text(), 'Профиль')]");
    private final By logoutButton = By.xpath("//button[contains(@class, 'Account_button__') and text()='Выход']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Step("Клик по кнопке 'Выход'")
    public void clickLogoutButton() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }


    @Step("Проверка, что страница профиля открыта")
    public boolean isProfilePageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(profileSection)).isDisplayed();
    }

}