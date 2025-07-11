package tests;


import api.UserClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.RegisterPage;
import utils.UserGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import utils.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты регистрации пользователя")
public class RegistrationTest extends BaseTest {
    private RegisterPage registerPage;
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
            }
        } catch (Exception e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Успешная регистрация пользователя")
    public void testSuccessfulRegistration(String browser) {
        initDriver(browser);
        initPages();
        user = UserGenerator.getRandomUser();
        mainPage.clickPersonalAccountButton();
        loginPage.clickRegisterLink();
        registerPage.registerUser(user.getName(), user.getEmail(), user.getPassword());

        assertTrue(loginPage.isLoginPageDisplayed(), "После успешной регистрации должна открыться страница входа");
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Ошибка при регистрации с некорректным паролем (менее 6 символов)")
    public void testRegistrationWithShortPassword(String browser) {
        initDriver(browser);
        initPages();
        user = UserGenerator.getRandomUser();
        String shortPassword = "12345";
        mainPage.clickPersonalAccountButton();
        loginPage.clickRegisterLink();
        registerPage.registerUser(user.getName(), user.getEmail(), shortPassword);

        assertTrue(registerPage.isPasswordErrorDisplayed(), "Должна отображаться ошибка о некорректном пароле");
    }

    private void initPages() {
        registerPage = new RegisterPage(driver);
        userClient = new UserClient();
    }
}