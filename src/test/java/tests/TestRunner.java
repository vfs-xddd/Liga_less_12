package tests;

import hooks.TestsConfig;
import hooks.WebDriverHandler;
import io.qameta.allure.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        clickDayProductInCartBtn();
        checkCartHeaderIconIfAdded();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 3")
    @Description("Переход в корзину.")
    public void test3() {
        dayProductIsDisplayed();
        clickDayProductInCartBtn();
        String title = getDayProductTitle();
        clickCartHeaderIconBtn();
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
         checkBlockIsDisplayed("Самые просматриваемые");
         List<String> titles = addToCart2MostWatchedProducts();
         clickCartHeaderIconBtn();
         checkCartAddedProduct(titles.toArray(new String[0]));
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 5")
    @Description("Поиск товаров.")
    public void test5() {
        searchInputIsDisplayed();
        sendTextAndClickSearchBtn();
        checkPageLinkAdd("/product-list-page");
        listingPageFilterExists();
        checkListingTitlesContains();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 6")
    @Description("Поиск товаров.")
    public void test6() {
        searchInputIsDisplayed();
        sendTextAndClickSearchBtn();
        checkPageLinkAdd("/product-list-page");
        listingPageFilterExists();
        checkListingTitlesContains();
        checkDropdownSort();
        selectDropdownSortDescPrice();
        checkListingTitlesContains();
        listingPageCheckSortDescPrice();
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
    @DisplayName("Тест кейс 8")
    @Description("Проверка добавления товаров в список сравнения.")
    public void test8() {
        searchInputIsDisplayed();
        sendTextAndClickSearchBtn();
        checkPageLinkAdd("/product-list-page");
        listingPageFilterExists();
        List<String> titles = listingPageAddProductsToCompare();
        clickCompareBtn();
        checkPageLinkAdd("/product-comparison");
        pageH1Is("Сравнение товаров");
        productCardsTitlesContains(titles);
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 9")
    @Description("Проверка добавления товара в список избранного.")
    public void test9() {
        searchInputIsDisplayed();
        sendTextAndClickSearchBtn();
        checkPageLinkAdd("/product-list-page");
        listingPageFilterExists();
        List<String> titles = listingPageAddProductsToWish();
        clickWishBtn();
        checkPageLinkAdd("/wish-list");
        pageH1Is("Избранное");
        productCardsTitlesContains(titles);
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