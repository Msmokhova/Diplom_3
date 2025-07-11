package tests;

import api.UserClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.ProfilePage;
import utils.User;


import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты навигации")
public class NavigationTest extends BaseTest {
    private ProfilePage profilePage;
    private User user;
    private UserClient userClient;

    @AfterEach
    public void cleanUp() {
        try {
            if (user != null && userClient != null) {
                String accessToken = userClient.getAccessToken(user);
                if (accessToken != null) {
                    userClient.deleteUser(accessToken);
                }
                user = null;
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }
    private User createTestUser() {
        User user = new User();
        user.setName("TestUser_" + System.currentTimeMillis());
        user.setEmail("test" + System.currentTimeMillis() + "@example.com");
        user.setPassword("StrongPassword123!");
        return user;
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход в личный кабинет")
    public void testNavigateToPersonalAccount(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        mainPage.clickLoginButton();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.clickPersonalAccountButton();

        assertTrue(profilePage.isProfilePageDisplayed(), "Должна открыться страница профиля");
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход из личного кабинета в конструктор через кнопку 'Конструктор'")
    public void testNavigateFromProfileToConstructorViaButton(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        mainPage.clickLoginButton();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.clickPersonalAccountButton();
        mainPage.clickConstructorLink();

        assertTrue(mainPage.isMainPageDisplayed(), "Должна открыться главная страница");
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход из личного кабинета в конструктор через логотип")
    public void testNavigateFromProfileToConstructorViaLogo(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        mainPage.clickLoginButton();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.clickPersonalAccountButton();
        mainPage.clickLogo();

        assertTrue(mainPage.isMainPageDisplayed(), "Должна открыться главная страница");
    }
    private void initPages() {
        profilePage = new ProfilePage(driver);
        userClient = new UserClient();
    }

    private void createUser() {
        user = createTestUser();
        userClient.registerUser(user);
    }
}
