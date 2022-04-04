package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/** Класс конфигурации веб драйвера.
 *
 * @author Maksim_Kachaev
 * */
public class WebDriverHandler {

    /**Настраевает конфигурацию веб драйвера
     * @return готовый драйвер для потока тестов
     * */
    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions ops = new ChromeOptions();
        ops.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(ops);
        driver.manage().window().maximize();
        Configuration.timeout = 8000;
        Configuration.holdBrowserOpen = true;

        return driver;
    }

    public static void clearSession() {
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
    }
}
