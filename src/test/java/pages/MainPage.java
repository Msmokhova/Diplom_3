package pages;

import org.openqa.selenium.*;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов главной страницы
    private final By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private final By logo = By.className("AppHeader_header__logo__2D0X2");
    private final By constructorSection = By.xpath("//a[contains(@class, 'AppHeader_header__link__3D_hX') and @href='/']");
    private final By orderButton = By.xpath("//button[text()='Оформить заказ']");
    private final By constructorLink = By.xpath("//p[text()='Конструктор']");

    // Локаторы разделов конструктора
    private final By bunsSection = By.xpath("//*[contains(@class, 'tab_tab_')]//*[text()='Булки']");
    private final By saucesSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG')]//span[text()='Соусы']");
    private final By fillingsSection = By.xpath("//div[contains(@class, 'tab_tab__1SPyG')]//span[text()='Начинки']");

    // Локаторы активных разделов
    private final By activeTab = By.xpath("//div[contains(@class, 'tab_tab_type_current__2BEPc')]");
    private final By bunsSectionHeader = By.xpath("//h2[text()='Булки']");
    private final By saucesSectionHeader = By.xpath("//h2[text()='Соусы']");
    private final By fillingsSectionHeader = By.xpath("//h2[text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Step("Клик по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        element.click();
    }
    @Step("Клик по ссылке 'Конструктор'")
    public void clickConstructorLink() {
        driver.findElement(constructorLink).click();
    }

    @Step("Клик по логотипу Stellar Burgers")
    public void clickLogo() {
        driver.findElement(logo).click();
    }


    @Step("Переход в раздел 'Булки'")
    public void clickBunsSection() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(bunsSection));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'center'});",
                element
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        System.out.println("Клик на раздел 'Булки' выполнен");
    }

    @Step("Переход в раздел 'Соусы'")
    public void clickSaucesSection() {
        clickElement(saucesSection);
    }

    @Step("Переход в раздел 'Начинки'")
    public void clickFillingsSection() {
        clickElement(fillingsSection);
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    @Step("Проверка, что активен раздел 'Булки'")
    public boolean isBunsSectionActive() {
        return isSectionActive("Булки", bunsSectionHeader);
    }

    private boolean isSectionActive(String sectionName, By headerLocator) {
        try {
            WebElement activeTabElement = driver.findElement(activeTab);
            boolean isTabActive = activeTabElement.findElement(
                    By.xpath(".//span[text()='" + sectionName + "']")
            ).isDisplayed();
            String activeClass = "tab_tab_type_current__2BEPc";
            boolean hasActiveClass = activeTabElement.getAttribute("class").contains(activeClass);
            boolean isHeaderVisible = driver.findElement(headerLocator).isDisplayed();



            System.out.println("Проверка активности '" + sectionName + "':");
            System.out.println("  isTabActive: " + isTabActive);
            System.out.println("  hasActiveClass: " + hasActiveClass);
            System.out.println("  isHeaderVisible: " + isHeaderVisible);


            return isTabActive && hasActiveClass && isHeaderVisible;
        } catch (Exception e) {
            System.out.println("Ошибка при проверке активности: " + e.getMessage());
            return false;
        }
    }

    @Step("Проверка, что активен раздел 'Соусы'")
    public boolean isSaucesSectionActive() {
        return isSectionActive("Соусы");
    }

    @Step("Проверка, что активен раздел 'Начинки'")
    public boolean isFillingsSectionActive() {
        return isSectionActive("Начинки");
    }

    private boolean isSectionActive(String sectionName) {
        try {
            WebElement activeTabElement = driver.findElement(activeTab);
            WebElement tabElement = activeTabElement.findElement(By.xpath(".//span[text()='" + sectionName + "']"));
            boolean isHeaderVisible = false;
            switch (sectionName) {
                case "Булки":
                    isHeaderVisible = driver.findElement(bunsSectionHeader).isDisplayed();
                    break;
                case "Соусы":
                    isHeaderVisible = driver.findElement(saucesSectionHeader).isDisplayed();
                    break;
                case "Начинки":
                    isHeaderVisible = driver.findElement(fillingsSectionHeader).isDisplayed();
                    break;
            }

            return tabElement.isDisplayed() && isHeaderVisible;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isAuthorizedStateDisplayed() {
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElement(orderButton).isDisplayed());
    }
    @Step("Проверка отображения главной страницы")
    public boolean isMainPageDisplayed() {
        return driver.findElement(constructorSection).isDisplayed();
    }
}