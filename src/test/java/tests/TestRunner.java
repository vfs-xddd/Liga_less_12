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
    public void before() {openPage("mvideo.ru");}

    @AfterEach
    public void after() {
        //WebDriverHandler.clearSession();
        }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 1")
    @Description("Проверка шапки главной страницы mvideo.")
    public void test1() {
        mPcheckStatusProfile();
        mPcheckCompareWishlistCart();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 2")
    @Description("Проверка активации кнопки корзины.")
    public void test2() {
        mPdayProductIsDisplayed();
        mPclickDayProductInCartBtn();
        mPcheckCartHeaderIconIfAdded();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 3")
    @Description("Переход в корзину.")
    public void test3() {
        mPdayProductIsDisplayed();
        mPclickDayProductInCartBtn();
        String title = mPgetDayProductTitle();
        mPclickCartHeaderIconBtn();
        cPheaderMyCartIsDisplayed();
        xxPcheckCartAddedProduct(title);
        cPcheckCheckoutBtn();
        cPcheckCostTitleAndPrice();
        cPcheckProductPrice();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 4")
    @Description("Добавление в корзину два товара.")
    public void test4() {
         xxPcheckBlockIsDisplayed("Самые просматриваемые");
         List<String> titles = mPaddToCart2MostWatchedProducts();
         mPclickCartHeaderIconBtn();
         xxPcheckCartAddedProduct(titles.toArray(new String[0]));
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 5")
    @Description("Поиск товаров.")
    public void test5() {
        lPsearchInputIsDisplayed();
        mPsendTextAndClickSearchBtn();
        xxPcheckPageLinkAdd("/product-list-page");
        lPlistingPageFilterExists();
        lPcheckListingTitlesContains();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 6")
    @Description("Поиск товаров.")
    public void test6() {
        lPsearchInputIsDisplayed();
        mPsendTextAndClickSearchBtn();
        xxPcheckPageLinkAdd("/product-list-page");
        lPlistingPageFilterExists();
        lPcheckListingTitlesContains();
        lPcheckDropdownSort();
        lPselectDropdownSortDescPrice();
        lPcheckListingTitlesContains();
        lPSortDescPrice();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 7")
    @Description("Проверка модального окна авторизации клиента.")
    public void test7() {
        mPclickLoginBtn();
        mPcheckLoginFormCloseBtn();
        mPcheckLoginFormTitle();
        mPcheckLoginFormPhone();
        mPcheckLoginFormContinueBtn();
        mPcheckLoginFormLegalEntities();
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 8")
    @Description("Проверка добавления товаров в список сравнения.")
    public void test8() {
        lPsearchInputIsDisplayed();
        mPsendTextAndClickSearchBtn();
        xxPcheckPageLinkAdd("/product-list-page");
        lPlistingPageFilterExists();
        List<String> titles = lPAddProductsToCompare();
        mPclickCompareBtn();
        xxPcheckPageLinkAdd("/product-comparison");
        xxPpageH1Is("Сравнение товаров");
        xxPproductCardsTitlesContains(titles);
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 9")
    @Description("Проверка добавления товара в список избранного.")
    public void test9() {
        lPsearchInputIsDisplayed();
        mPsendTextAndClickSearchBtn();
        xxPcheckPageLinkAdd("/product-list-page");
        lPlistingPageFilterExists();
        List<String> titles = lPAddProductsToWish();
        mPclickWishBtn();
        xxPcheckPageLinkAdd("/wish-list");
        xxPpageH1Is("Избранное");
        xxPproductCardsTitlesContains(titles);
    }

    /**UI test*/
    @Test
    @DisplayName("Тест кейс 10")
    @Description("Проверка изменения города.")
    public void test10() {
        mPclickLocation();
        mPcheckLocationFormTitle();
        mPclickTargetCity();
        mPlocationFormIsNotDisplayed();
        mPcheckCurrentLocation();
    }
}