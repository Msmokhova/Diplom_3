package tests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("Тесты раздела 'Конструктор'")
public class ConstructorTest extends BaseTest {

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход к разделу 'Булки'")
    void testBunsSection(String browser) {
        initDriver(browser);
        mainPage.clickFillingsSection();
        assertTrue(mainPage.isFillingsSectionActive(), "Раздел 'Начинки' должен быть активен");
        mainPage.clickBunsSection();
        assertTrue(mainPage.isBunsSectionActive(), "Раздел 'Булки' должен быть активен");
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход к разделу 'Соусы'")
    void testSaucesSection(String browser) {
        initDriver(browser);
        mainPage.clickSaucesSection();
        assertTrue(mainPage.isSaucesSectionActive());
    }

    @ParameterizedTest(name = "Браузер: {0}")
    @MethodSource("browsersProvider")
    @DisplayName("Переход к разделу 'Начинки'")
    void testFillingsSection(String browser) {
        initDriver(browser);
        mainPage.clickFillingsSection();
        assertTrue(mainPage.isFillingsSectionActive());
    }

}



