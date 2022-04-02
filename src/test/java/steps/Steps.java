package steps;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pageObject.CartPage;
import pageObject.ListingPage;
import pageObject.MainPage;
import pageObject.ProductCard;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.page;
import static hooks.PageHooks.*;

public class Steps {

    @Step("Открываем страницу {page}")
    public static void openPage(String page) {
        if ("mvideo.ru".equals(page)) {
            MainPage.openAndCheck();
        }
    }

    @Step("Кнопка “Статус заказа” и “Войти” отображаются и активны")
    public static void checkStatusProfile() {
        checkElemActive(MainPage.getElem("Войти"));
        checkElemActive(MainPage.getElem("Статус заказа"));
    }

    @Step("Кнопки “Сравнение”, “Избранное” и “Корзина” отображаются и не активны")
    public static void checkCompareWishlistCart() {
        checkElemNotActive(MainPage.getElem("Сравнение"));
        checkElemNotActive(MainPage.getElem("Избранное"));
        checkElemNotActive(MainPage.getElem("Корзина"));
    }

    @Step("На странице отображается блок “Товары дня”")
    public static void dayProductIsDisplayed() {
        Assertions.assertTrue(isDisplayed(MainPage.getElem("Товары дня")));
    }

    @Step("Нажимаем у товара на кнопку \"В корзину\"")
    public static void pressDayProductInCartBtn() {
        click(MainPage.getElem("Товары дня.В корзину"));
        Assertions.assertEquals("В корзине", getText(MainPage.getElem("Товары дня.В корзину")));
    }

    @Step("Получить название добавленного товара.")
    public static String getDayProductTitle() {
        return getText(MainPage.getElem("Товары дня.Описание"));
    }
    @Step("Кнопка “Корзина” в шапке становится активной и на ней отображается число 1")
    public static void checkCartHeaderIconIfAdded() {
        checkElemActive(MainPage.getElem("Корзина"));
        Assertions.assertEquals("1", getText(MainPage.getElem("Цифра над корзиной")));
    }

    @Step("Нажимаем на кнопку \"Корзина\" - открылась страница /cart ")
    public static void pressCartHeaderIconBtn() {
        click(MainPage.getElem("Корзина"));
        Assertions.assertEquals("https://www.mvideo.ru/cart", getPageLink());
    }

    @Step("Отображается заголовок \"Моя корзина\"")
    public static void headerMyCartIsDisplayed() {
        Assertions.assertTrue(isDisplayed(CartPage.getElem("Моя корзина")));
    }

    @Step("Отображается карточка с наименованием добавленного товара \"{title}\"")
    public static void checkCartAddedProduct(String title) {
        Assertions.assertTrue(CartPage.isInCartProductList(title));
    }

    @Step("Отображается кнопка \"Перейти к оформлению\"")
    public static void checkCheckoutBtn() {
        Assertions.assertTrue(isDisplayed(CartPage.getElem("Перейти к оформлению")));
    }

    @Step("Отображается текст \"В корзине 1 товар\" и цена совпадает с ценой товара")
    public static void checkCostTitleAndPrice() {
        Assertions.assertEquals("В корзине 1 товар", getText(CartPage.getElem("В корзине товар")));
    }

    @Step("Цена совпадает с ценой товара.")
    public static void checkProductPrice() {
        String itemPrice = CartPage.getFirstItemActualPrice();
        String totalPrice = getText(CartPage.getElem("Общая актуальная цена"));
        Assertions.assertEquals(itemPrice, totalPrice);
    }

    @Step("На странице отображается блок \"Самые просматриваемые\"")
    public static void checkMostWatched() {
        boolean cond1 = MainPage.scrollToFindH2("Самые просматриваемые");
        boolean cond2 = !MainPage.isEmptyMostWatchedItemTitles();
        Assertions.assertTrue(cond1 & cond2, "h2 Самые просматриваемые exist? - "+ cond1 + " , items in container exist?- "+ cond2);
    }

    @Step("Нажимаем на кнопку “В корзину” у первых двух товаров.")
    public static void addToCart2MostWatchedProducts() {
        click(MainPage.getMostWatchedAddToCartBtn().get(0));
        click(MainPage.getMostWatchedAddToCartBtn().get(1));
        //.................
        //TODO
    }


    @Step("В шапке отображается строка поиска товаров")
    public static void searchInputIsDisplayed() {
        Assertions.assertTrue(isDisplayed(MainPage.getElem("Строка поиска")));
    }

    @Step("Вводим в  строку поиска товаров \"apple\" и нажимаем на кнопку поиска")
    public static void sendTextAndClickSearchBtn() {
        SelenideElement inputEl = MainPage.getElem("Строка поиска");
        click(inputEl);
        send(inputEl, "apple");
        click(MainPage.getElem("Кнопка поиска"));
        shouldBeVisible(ListingPage.getProductListContainer());
    }

    @Step("Открывается страница \"/product-list-page\" с параметрами поиска")
    public static void checkListingPageIsOpen() {
        String expectedLink = System.getProperty("main_page_url") + "/product-list-page";
        String link = getPageLink();
        String actualLink = link.substring(0, link.indexOf("?"));
        boolean cond1 = isDisplayed(ListingPage.getFilterContainer());
        boolean cond2 = expectedLink.equals(actualLink);
        Assertions.assertTrue(cond1 & cond2, "FilterContainer isDisplayed?- "+ cond1 + "PageLinkCorrect?- "+cond2);
    }

    @Step("Отображаются только товары содержащие в названии слово \"apple\" в любом регистре")
    public static void checkListingTitlesContains() {
        Assertions.assertTrue(ListingPage.titlesAllMatchAllPages("apple"));
    }

    @Step("Нажимаем кнопку Войти.")
    public static void clickLoginBtn() {
        click(MainPage.getElem("Войти"));
        shouldBeVisible(MainPage.getElem("Форма входа"));
    }

    @Step("Отображается кнопка закрытия модального окна.")
    public static void checkLoginFormCloseBtn() {
        Assertions.assertTrue(isDisplayed(MainPage.getElem("Форма входа.Закрыть")));
    }

    @Step("Отображается модальное окно с заголовком Вход или регистрация.")
    public static void checkLoginFormTitle() {
        Assertions.assertTrue(isDisplayed(MainPage.getElem("Форма входа.Описание")));
    }

    @Step("Отображается поле для ввода текста с плейсхолдером \"Телефон\".")
    public static void checkLoginFormPhone() {
        Assertions.assertEquals("Телефон", getText(MainPage.getElem("Форма входа.Телефон")));
    }

    @Step("Отображается неактивная кнопка \"Продолжить\".")
    public static void checkLoginFormContinueBtn() {
        checkElemNotActive(MainPage.getElem("Форма входа.Продолжить"));
    }

    @Step("Отображается ссылка \"Для юридических лиц\".")
    public static void checkLoginFormLegalEntities() {
        isDisplayed(MainPage.getElem("Форма входа.Для юридических лиц"));
    }

    @Step("Нажимаем на ссылку с текущим городом.")
    public static void clickLocation() {
        click(MainPage.getElem("Город"));
        isDisplayed(MainPage.getElem("Форма локации"));
    }

    @Step("Открыто модальное окно с заголовком \"Выберите город\".")
    public static void checkLocationFormTitle() {
        Assertions.assertEquals("Выберите город", getText(MainPage.getElem("Форма локации.Описание")));
    }

    @Step("Нажимаем на строку с городом \"Краснодар\"")
    public static void clickTargetCity() {
        click(MainPage.getCity("Краснодар"));
        shouldNotBeVisible(MainPage.getElem("Форма локации"));
    }

    @Step("Модальное окно с заголовком \"Выберите город\" не отображается")
    public static void locationFormIsNotDisplayed() {
        Assertions.assertFalse(isDisplayed(MainPage.getElem("Форма локации.Описание")));
    }

    @Step("В хедере отображается ссылка с городом \"Краснодар\"")
    public static void checkCurrentLocation() {
      Assertions.assertEquals("Краснодар",  getText(MainPage.getElem("Город")));
    }
}
