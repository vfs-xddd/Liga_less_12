package hooks;

import org.junit.jupiter.api.AfterAll;
import utils.PropertyManager;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.sleep;

/** Настроечный класс для всех тестов.
 *
 * @author Maksim_Kachaev
 * @see org.junit.jupiter.api.BeforeAll
 * @see org.junit.jupiter.api.AfterAll
 * */
public class TestsConfig {

    /**Создает тестовое окружение*/
    @BeforeAll
    public static void initTestEnvironment(){
        initTestProperty();
        initWebDriver();
        addAllure();
    }

    /**Закрывает браузер. Очищает ресурсы.*/
    @AfterAll
    public static void closeWebDriver() {
        sleep(3000);                    //успеть увидеть результат перед закрытием
        WebDriverRunner.closeWebDriver();
    }

    /**Подключает входные данные*/
    private static void initTestProperty() {new PropertyManager();}

    /**Подключает настроенный веб драйвер в поток*/
    private static void initWebDriver(){WebDriverRunner.setWebDriver(WebDriverHandler.getDriver());}

    /**Добавляет поддержку отчетов Allure*/
    private static void addAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
        );
    }

}
