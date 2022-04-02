package tests;

import hooks.TestsConfig;
import hooks.WebDriverHandler;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static steps.Steps.*;


/** Главный тестовый класс.
 *  <p>Наследует конфигурацию WebDriver, Allure и входные данные Properties.</p>
 *  <code>Junit5</code>
 *  @author Maksim_Kachaev
 * */
public class TestRunner extends TestsConfig {

    @BeforeEach
    public void before() {
        openPage("mvideo.ru");
    }

    @AfterEach
    public void after() {
        WebDriverHandler.clearSession();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 1")
    @Description("Проверка шапки главной страницы mvideo.")
    public void test1() {
        checkStatusProfile();
        checkCompareWishlistCart();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 2")
    @Description("Проверка активации кнопки корзины.")
    public void test2() {
        dayProductIsDisplayed();
        pressDayProductInCartBtn();
        checkCartHeaderIconIfAdded();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 3")
    @Description("Переход в корзину.")
    public void test3() {
        dayProductIsDisplayed();
        pressDayProductInCartBtn();
        String title = getDayProductTitle();
        pressCartHeaderIconBtn();
        headerMyCartIsDisplayed();
        checkCartAddedProduct(title);
        checkCheckoutBtn();
        checkCostTitleAndPrice();
        checkProductPrice();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 4")
    @Description("Добавление в корзину два товара.")
    public void test4() {
         //checkMostWatched();
        //addToCart2MostWatchedProducts();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 5")
    @Description("Поиск товаров.")
    public void test5() {
        searchInputIsDisplayed();
        sendTextAndClickSearchBtn();
        checkListingPageIsOpen();
        checkListingTitlesContains();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 7")
    @Description("Проверка модального окна авторизации клиента.")
    public void test7() {
        clickLoginBtn();
        checkLoginFormCloseBtn();
        checkLoginFormTitle();
        checkLoginFormPhone();
        checkLoginFormContinueBtn();
        checkLoginFormLegalEntities();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 10")
    @Description("Проверка изменения города.")
    public void test10() {
        clickLocation();
        checkLocationFormTitle();
        clickTargetCity();
        locationFormIsNotDisplayed();
        checkCurrentLocation();
    }
}