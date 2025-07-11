package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static final String YANDEX_BROWSER_PATH =
            "C:/Users/Mihail/AppData/Local/Yandex/YandexBrowser/Application/browser.exe";

    public static WebDriver getDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "yandex":
                return setupYandexDriver();
            case "chrome":
            default:
                return setupChromeDriver();
        }
    }

    private static WebDriver setupYandexDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/webdrivers/yandexdriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary(YANDEX_BROWSER_PATH);
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}