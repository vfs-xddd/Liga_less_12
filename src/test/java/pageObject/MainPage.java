package pageObject;

import com.codeborne.selenide.*;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/** <code>Page</code>
 * Главная страница.
 * @author Maksim_Kachaev
 * */
public class MainPage {

    static {getPage();}

    private static final String XPATH_HEADER_ICON = "//mvid-header-icon[.//*[text() = '";
    private static final String XPATH_MOST_WATCHED_CONTAINER = "//mvid-simple-product-collection[./h2[text()=  'Самые просматриваемые'  ]]/parent::*";
    private static final String XPATH_MOST_WATCHED_ITEM_TITLE = "//*[contains(@class,  'title'   )]/a";
    private static final String XPATH_MOST_WATCHED_ADD_TO_CART = "/following::button[contains(@class,  'actions__cart' )]";
    private static final String XPATH_LOCATION_FORM_CONTAINER = "//mvid-modal//*[contains(@class, 'modal-wrapper__content' )]";
    private static final String XPATH_H2_PRODUCT_LIST = "//h2[text()= '?' ]/parent::* //mvid-product-cards-group";

    @FindBy(className = "app-header-navbar")
    private static SelenideElement navBar;

    @FindBy(xpath = XPATH_HEADER_ICON +  "Статус заказа"  +"']]")
    private static SelenideElement statusOrder;

    @FindBy(xpath = XPATH_HEADER_ICON +  "Войти"  +"']]")
    private static SelenideElement profile;

    @FindBy(xpath = XPATH_HEADER_ICON +  "Сравнение"  +"']]")
    private static SelenideElement compare;

    @FindBy(xpath = XPATH_HEADER_ICON +  "Избранное"  +"']]")
    private static SelenideElement wishlist;

    @FindBy(xpath = XPATH_HEADER_ICON +  "Корзина"  +"']]")
    private static SelenideElement cart;

    @FindBy(className = "product-container")
    private static SelenideElement dyyProductContainer;

    @FindBy(tagName = "mvid-day-product")
    private static ElementsCollection dayProductCollection;

    @FindBy(tagName = "h2")
    private static ElementsCollection h2;

    @FindBy(tagName = "mvid-login-form")
    private static SelenideElement loginFormContainer;

    @FindBy(className = "login-form-top-nav__btn-icon")
    private static SelenideElement loginFormCloseBtn;

    @FindBy(xpath = "//mvid-login-form//h2")
    private static SelenideElement loginFormTitle;

    @FindBy(tagName = "mvid-form-field-label")
    private static SelenideElement loginFormPhone;

    @FindBy(xpath = "//*[@class=  'mv-main-button--content' and contains(text(), 'Продолжить') ]")
    private static SelenideElement loginFormContinueBtn;

    @FindBy(xpath = "//button[contains(@class, 'login-form__link' )]")
    private static SelenideElement loginFormLegalEntities;

    @FindBy(className = "location-text")
    private static SelenideElement location;

    @FindBy(xpath = XPATH_LOCATION_FORM_CONTAINER)
    private static SelenideElement locationFormContainer;

    @FindBy(xpath = XPATH_LOCATION_FORM_CONTAINER + "//h3")
    private static SelenideElement locationFormTitle;

    @FindBy(xpath = XPATH_LOCATION_FORM_CONTAINER + "//*[@class=  'location-select__location-list'  ]/li")
    private static ElementsCollection locationFormCitiesList;

    @FindBy(xpath = "//*[contains(@class,   'search-input-container'  )]//input")
    private static SelenideElement searchInput;

    @FindBy(xpath = "//mvid-icon[contains(@class, 'search-icon' )]")
    private static SelenideElement searchIconBtn;


    @CanIgnoreReturnValue
    public static MainPage getPage() {
        return page(MainPage.class);
    }

    public static SelenideElement getNavBar() {
        return navBar;
    }

    public static SelenideElement getElem(String title) {
        switch (title) {
            case "Корзина": return cart;
            case "Корзина.Цифра": return cart.$(new By.ByTagName("mvid-bubble"));
            case "Избранное": return wishlist;
            case "Избранное.Цифра": return  wishlist.$(new By.ByTagName("mvid-bubble"));
            case "Сравнение": return compare;
            case "Сравнение.Цифра": return  compare.$(new By.ByTagName("mvid-bubble"));
            case "Войти": return profile;
            case "Статус заказа": return statusOrder;
            case "Товары дня": return dyyProductContainer;
            case "Товары дня.В корзину": return getDayProductInCartBtn();
            case "Товары дня.Описание": return getDayProductTitle();
            case "Самые просматриваемые": return $x(XPATH_MOST_WATCHED_CONTAINER);
            case "Форма входа": return loginFormContainer;
            case "Форма входа.Закрыть": return loginFormCloseBtn;
            case "Форма входа.Описание": return loginFormTitle;
            case "Форма входа.Телефон": return loginFormPhone;
            case "Форма входа.Продолжить": return loginFormContinueBtn;
            case "Форма входа.Для юридических лиц": return loginFormLegalEntities;
            case "Город": return location;
            case "Форма локации": return locationFormContainer;
            case "Форма локации.Описание": return locationFormTitle;
            case "Строка поиска": return searchInput;
            case "Кнопка поиска": return searchIconBtn;
            default: return null;
        }
    }

    private static ElementsCollection getH2Collection() {
        return h2;
    }

    public static SelenideElement getCity(String city) {
       return locationFormCitiesList.filter(Condition.exactText(city)).first();
    }

    public static SelenideElement getH2ProductListContainer(String h2Title) {
        String xpath = XPATH_H2_PRODUCT_LIST.replace("?", h2Title);
        return $x(xpath);
    }

    /**Ищет заданный H2 заголовок, прокручивая H2 и переходя к след ниже по странице.
     * Проверяет, что заголовок найден*/
    public static void scrollToFindH2(String h2Title){
        String xpathAdd = "/following::h2";
        String xpath = "//h2";
       while (true){
           SelenideElement el = $x(xpath);
           if (!el.exists()) break;
           el.scrollIntoView(true).sibling(0).shouldBe(visible);
           xpath += xpathAdd;
       }
       h2.forEach(el->el.scrollIntoView(true));
       getH2Collection().shouldBe(CollectionCondition.itemWithText(h2Title));
       getH2Collection().filter(Condition.exactText(h2Title)).first().scrollIntoView(true);
    }

    public static List<String> getMostWatchedTitles() {
        String xpath = XPATH_MOST_WATCHED_CONTAINER + XPATH_MOST_WATCHED_ITEM_TITLE;
        return $$x(xpath).texts();
    }

    public static SelenideElement getMostWatchedAddToCartBtn(String title) {
        String xpath = XPATH_MOST_WATCHED_CONTAINER
                + "//*[text()= '?'   ]".replace("?", title)
                + XPATH_MOST_WATCHED_ADD_TO_CART;
        return $x(xpath);
    }

    private static SelenideElement getDayProductContainer() {
        return dayProductCollection.filter(Condition.cssValue("z-index", "0")).first();
    }

    private static SelenideElement getDayProductInCartBtn() {
        return getDayProductContainer().$(By.className("cart_btn"));
    }

    private static SelenideElement getDayProductTitle() {
        return getDayProductContainer().$(By.xpath(".//*[contains(@class,  'title'  )]/a"));
    }

}
