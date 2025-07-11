package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import utils.WebDriverFactory;

import java.time.Duration;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {
    protected WebDriver driver;
    protected MainPage mainPage;
    protected LoginPage loginPage;

    protected void initDriver(String browserType) {
        if (driver != null) {
            driver.quit();
        }
        driver = WebDriverFactory.getDriver(browserType);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.get("https://stellarburgers.nomoreparties.site");

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @BeforeEach
    public void setUp(TestInfo testInfo) {
    }

    @AfterEach
    public void tearDown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected static Stream<Arguments> browsersProvider() {
        return Stream.of(
                Arguments.of("chrome"),
                Arguments.of("yandex")
        );
    }
}