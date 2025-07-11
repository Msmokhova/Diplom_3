package tests;

import api.UserClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.*;
import utils.User;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты авторизации пользователя")
public class LoginTest extends BaseTest {
    private RegisterPage registerPage;
    private ForgotPasswordPage forgotPasswordPage;
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
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной")
    public void testLoginViaMainPageButton(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        System.out.println("Clicking login button...");
        mainPage.clickLoginButton();
        System.out.println("Logging in with user: " + user.getEmail());
        loginPage.login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isAuthorizedStateDisplayed());
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void testLoginViaPersonalAccountButton(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        System.out.println("Clicking login button...");
        mainPage.clickPersonalAccountButton();
        System.out.println("Logging in with user: " + user.getEmail());
        loginPage.login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isAuthorizedStateDisplayed());
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Вход через кнопку в форме регистрации")
    public void testLoginViaRegisterForm(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        System.out.println("Clicking login button...");
        mainPage.clickPersonalAccountButton();
        loginPage.clickRegisterLink();
        assertTrue(registerPage.isRegisterPageDisplayed(), "Должна открыться страница регистрации");
        registerPage.clickLoginLink();
        System.out.println("Logging in with user: " + user.getEmail());
        loginPage.login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isAuthorizedStateDisplayed());
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void testLoginViaForgotPasswordForm(String browser) {
        initDriver(browser);
        initPages();
        createUser();
        System.out.println("Clicking login button...");
        mainPage.clickPersonalAccountButton();
        loginPage.clickRecoverPasswordLink();
        assertTrue(forgotPasswordPage.isForgotPasswordPageDisplayed(), "Должна открыться страница восстановления пароля");
        forgotPasswordPage.clickLoginLink();
        System.out.println("Logging in with user: " + user.getEmail());
        loginPage.login(user.getEmail(), user.getPassword());
        assertTrue(mainPage.isAuthorizedStateDisplayed());

    }

    private void initPages() {
        registerPage = new RegisterPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);
        profilePage = new ProfilePage(driver);
        userClient = new UserClient();
    }

    private void createUser() {
        user = createTestUser();
        userClient.registerUser(user);
    }
}