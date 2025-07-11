package tests;

import api.UserClient;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.*;
import org.junit.jupiter.api.*;
import utils.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тест выхода из аккаунта")
public class LogoutTest extends BaseTest {
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
    @DisplayName("Выход по кнопке 'Выйти' в личном кабинете")
    public void testLogout(String browser) {
        initDriver(browser);
        initPages();
        createUser();

        mainPage.clickPersonalAccountButton();
        loginPage.login(user.getEmail(), user.getPassword());
        mainPage.clickPersonalAccountButton();
        profilePage.clickLogoutButton();

        assertTrue(loginPage.isLoginPageDisplayed(), "Должна открыться страница входа");
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