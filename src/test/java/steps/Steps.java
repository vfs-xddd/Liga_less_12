package steps;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pageObject.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hooks.PageHooks.*;

public class Steps {

    @Step("Открываем страницу {page}")
    public static void openPage(String page) {
        if ("mvideo.ru".equals(page)) {
            openLink(System.getProperty("main_page_url"));
            shouldBeVisible(MainPage.getNavBar());
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
    public static void clickDayProductInCartBtn() {
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
        Assertions.assertEquals("1", getText(MainPage.getElem("Корзина.Цифра")));
    }

    @Step("Нажимаем на кнопку \"Корзина\" - открылась страница /cart ")
    public static void clickCartHeaderIconBtn() {
        click(MainPage.getElem("Корзина"));
        Assertions.assertEquals("https://www.mvideo.ru/cart", getPageLink());
    }

    @Step("Отображается заголовок \"Моя корзина\"")
    public static void headerMyCartIsDisplayed() {
        Assertions.assertTrue(isDisplayed(CartPage.getElem("Моя корзина")));
    }

    @Step("Отображаются карточки с наименованием добавленных товаров \n\"{titles}\"")
    public static void checkCartAddedProduct(String...titles) {
        if (titles.length == 0) {throw new IllegalArgumentException("Нет данных!.");}
        boolean result =false;
        for (String t : titles) {
            result = CartPage.isInCartProductList(t);
        }
        Assertions.assertTrue(result);
    }

    @Step("Отображается кнопка \"Перейти к оформлению\"")
    public static void checkCheckoutBtn() {
        Assertions.assertTrue(isDisplayed(CartPage.getElem("Перейти к оформлению")));
    }

    @Step("Отображается текст \"В корзине 1 товар\".")
    public static void checkCostTitleAndPrice() {
        Assertions.assertEquals("В корзине 1 товар", getText(CartPage.getElem("В корзине товар")));
    }

    @Step("Цена совпадает с ценой товара.")
    public static void checkProductPrice() {
        String itemPrice = CartPage.getFirstItemActualPrice();
        String totalPrice = getText(CartPage.getElem("Общая актуальная цена"));
        Assertions.assertEquals(itemPrice, totalPrice);
    }

    @Step("На странице отображается блок {h2Title}.")
    public static void checkBlockIsDisplayed(String h2Title) {
        MainPage.scrollToFindH2(h2Title);
        shouldBeVisible(MainPage.getH2ProductListContainer(h2Title));
    }

    @Step("Нажимаем на кнопку “В корзину” у первых двух товаров.")
    public static List<String> addToCart2MostWatchedProducts() {
        List <String> titles = MainPage.getMostWatchedTitles().stream().limit(2).collect(Collectors.toList());
        click(MainPage.getMostWatchedAddToCartBtn(titles.get(0)));
        shouldBeAtrr(MainPage.getMostWatchedAddToCartBtn(titles.get(0)), "title", "Перейти в корзину");
        click(MainPage.getMostWatchedAddToCartBtn(titles.get(1)));
        shouldBeAtrr(MainPage.getMostWatchedAddToCartBtn(titles.get(1)), "title", "Перейти в корзину");
        return titles;
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

    @Step("Открывается страница {linkPart} с параметрами поиска")
    public static void checkPageLinkAdd(String linkPart) {
        String expectedLink = System.getProperty("main_page_url") + linkPart;
        String link = getPageLink();
        String actualLink;
        if (link.contains("?")) actualLink = link.substring(0, link.indexOf("?"));
        else actualLink = link;
        Assertions.assertEquals(expectedLink, actualLink);
    }

    @Step("На странице присутствуют параметры поиска")
    public static void listingPageFilterExists() {
        Assertions.assertTrue(isDisplayed(ListingPage.getFilterContainer()));
    }


    @Step("Отображаются только товары содержащие в названии слово \"apple\" в любом регистре")
    public static void checkListingTitlesContains() {
        Assertions.assertTrue(ListingPage.titlesAllMatchAllPages("apple"));
    }

    @Step("Отображается выпадающий список вариантов сортировки со значением \"Сначала популярные\"")
    public static void checkDropdownSort() {
        Assertions.assertEquals("Сначала популярные", getText(ListingPage.getDropdownSort()));
    }

    @Step("Выбираем в списке значение \"Сначала дороже\"")
    public static void selectDropdownSortDescPrice() {
       ListingPage.selectInDropdownSort("Сначала дороже");
       Assertions.assertEquals("Сначала дороже", getText(ListingPage.getDropdownSort()));
    }

    @Step("Цена следующего (слева-направо) товара меньше или равна текущего.")
    public static void listingPageCheckSortDescPrice() {
        Assertions.assertTrue(ListingPage.pricesIsDescAllPages(), "Массив цен не явл-ся убывающей последовательностью!");
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

    @Step("На первых трех товарах нажимаем кнопку добавления товара в сравнение.")
    public static List<String> listingPageAddProductsToCompare() {
        collectionShouldBeVisibleNotEmpty(ListingPage.getTitlesCollection());
        List <String> titles = new ArrayList<>();
        ListingPage.getProductCardsList().stream().limit(3).forEach(el-> {
                    click(el.getComparesBtn());
                    titles.add(el.title());
                });
        shouldBeVisible(MainPage.getElem("Сравнение.Цифра"));
        Assertions.assertEquals("3", getText(MainPage.getElem("Сравнение.Цифра")));
        return titles;
    }

    @Step("На первых трех товарах нажимаем кнопку добавления товара в избранное.")
    public static List<String> listingPageAddProductsToWish() {
        collectionShouldBeVisibleNotEmpty(ListingPage.getTitlesCollection());
        List <String> titles = new ArrayList<>();
        ListingPage.getProductCardsList().stream().limit(3).forEach(el-> {
            click(el.getWishListBtn());
            titles.add(el.title());
        });
        shouldBeVisible(MainPage.getElem("Избранное.Цифра"));
        Assertions.assertEquals("3", getText(MainPage.getElem("Избранное.Цифра")));
        return titles;
    }

    @Step("Нажимаем на кнопку \"Сравнение\".")
    public static void clickCompareBtn() {
        click(MainPage.getElem("Сравнение"));
        shouldBeVisible(ComparePage.getH1());
        Assertions.assertEquals("Сравнение товаров", getH1Text());
    }

    @Step("Нажимаем на кнопку \"Избранное\".")
    public static void clickWishBtn() {
        click(MainPage.getElem("Избранное"));
        shouldBeVisible(WishPage.getH1());
        Assertions.assertEquals("Избранное", getH1Text());
    }

    @Step("Отображается заголовок \"{h1Title}\".")
    public static void pageH1Is(String h1Title) {
        Assertions.assertEquals(h1Title, getH1Text());
    }

    @Step("Отображаются карточки с именами добавленных товаров \n{expectedTitles}.")
    public static void productCardsTitlesContains(List<String> expectedTitles) {
        List <String> actualTitles = new ArrayList<>();
        if (getH1Text().equals("Сравнение товаров")) actualTitles = ComparePage.getProductTitles();
        else if (getH1Text().equals("Избранное")) actualTitles = WishPage.getProductTitles();
        else Assertions.fail("Неизвестный тип страницы с товарами!");
        Assertions.assertTrue(actualTitles.containsAll(expectedTitles) & actualTitles.size()==expectedTitles.size());
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
