package pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import utils.Common;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.*;

public class ListingPage {

    static {getPage();}

    private static final String XPATH_ALL_TITLES = "//mvid-plp-product-title";
    private static final String XPATH_ALL_MAIN_PRICES = "//*[@class=  'price__main-value'  ]";
    private static final String XPATH_PAGINATION = "//mvid-pagination";
    private static final String XPATH_PRODUCTS_LAYOUT = "//mvid-plp-product-cards-layout";
    private static final String XPATH_DROPDOWN_SORT_CONTAINER = "//mvid-product-list-controls//*[@class=  'dropdown__options'  ]";
    private static final String XPATH_DROPDOWN_SORT_OPTION = "/div[contains(text(),  '?' ) ]";

    @FindBy(tagName = "mvid-product-list")
    private static SelenideElement productListContainer;

    @FindBy(className = "plp__filters")
    private static SelenideElement filterContainer;

    @FindBy(xpath = "//mvid-product-list-controls//*[@class= 'dropdown with-icon' ]")
    private static SelenideElement dropdownSort;


    @CanIgnoreReturnValue
    public static ListingPage getPage() {
        return page(ListingPage.class);
    }

    public static SelenideElement getProductListContainer() {
        return productListContainer;
    }

    public static SelenideElement getFilterContainer() {return filterContainer;}

    public static SelenideElement getDropdownSort() {
        return dropdownSort;
    }

    public static ElementsCollection getTitlesCollection() {
        return $$x(XPATH_ALL_TITLES);
    }

    public static ElementsCollection getPricesCollection() {
        return $$x(XPATH_ALL_MAIN_PRICES);
    }

    public static List<ProductCard> getProductCardsList() {
      return  getTitlesCollection().texts().stream().map(ProductCard::new).collect(Collectors.toList());
    }

    /**Проверяет на текущей странице листинга, что все названия карточек товаров содержат переданный текст*/
    public static boolean titlesAllMatch(String text) {
       return getTitlesCollection().texts().stream()
                .allMatch(el->el.toLowerCase(Locale.ROOT)
                        .contains(text));
    }

    /**Проверяет на всех страницах листинга, что все названия карточек товаров содержат переданный текст*/
    public static boolean titlesAllMatchAllPages(String text) {
        while (true) {
            $x(XPATH_PRODUCTS_LAYOUT).shouldBe(Condition.visible);
            scrollAllProductCards();
            if (!titlesAllMatch(text)) return false;
            if (pages("current") == pages("size")) break;
            pages("next");
        }
        return true;
    }

    /**Проверяет на всех страницах листинга, что все карточки товаров отсортированы по убыванию основной цены*/
    public static boolean pricesIsDescAllPages() {
        while (true) {
            $x(XPATH_PRODUCTS_LAYOUT).shouldBe(Condition.visible);
            scrollAllProductCards();
            List <Integer> list = getPricesCollection().texts()
                    .stream()
                    .map(el->el.substring(0, el.length()-1))        //убираем символ рубля вконце
                    .map(el->el.replace(" ", ""))   //убираем пробелы
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            if (!Common.isDescending(list)) return false;
            if (pages("current") == pages("size")) break;
            pages("next");
        }
        return true;
    }

    /**Скроллит все карточки товаров от последней(в ряду/блоке) к последней(в ряду/блоке)
     * пока общее кол-во карточек не перестанет меняться.*/
    public static void scrollAllProductCards() {
        int time = 1000;
        $x(XPATH_PRODUCTS_LAYOUT).shouldBe(Condition.visible);
        Common.waitResultBecomeStableWhileDoingSmth(
                ()-> getTitlesCollection().size(),
                ()-> {
                    int num = getTitlesCollection().size()-1;
                    getTitlesCollection().get(num).scrollIntoView(false).shouldBe(Condition.visible);},
                time);
    }

    public static void selectInDropdownSort(String typeOfSort) {
        dropdownSort.scrollIntoView(false).hover().shouldBe(Condition.visible).click();
        Common.shouldBe($x(XPATH_DROPDOWN_SORT_CONTAINER)::isDisplayed, Configuration.timeout);
        String xpath = XPATH_DROPDOWN_SORT_CONTAINER + XPATH_DROPDOWN_SORT_OPTION.replace("?", typeOfSort);
        $x(xpath).click();
    }

    private static List<SelenideElement> getPagesList() { return $$x(XPATH_PAGINATION + "//li");}

    /**Пагинация. Аргументами осуществляется управление.
     *
     * @param command next, previous, last, current, size
     * @return результат команды(если есть)*/
    @CanIgnoreReturnValue
    public static int pages(String command) {
        switch (command) {
            case "next": {
                List<SelenideElement> pagesList = getPagesList();
                SelenideElement el = pagesList.get(pagesList.size()-1);
                if (elCssClassContains(el, "disabled")) break;
                el.click(); break;
            }
            case "previous": {
                SelenideElement el = getPagesList().get(0);
                if (elCssClassContains(el, "disabled")) break;
                el.click(); break;
            }
            case "last": {
                List<SelenideElement> list = getPagesList();
                list.get(list.size()-2).click(); break;
            }
            case "size": {
                List<SelenideElement> list = getPagesList();
                return Integer.parseInt(list.get(list.size()-2).getText());
            }
            case "current": {
                return Integer.parseInt(getPagesList().stream()
                        .filter(el-> elCssClassContains(el, "active"))
                        .collect(Collectors.toList())
                        .get(0)
                        .find(new By.ByTagName("span"))
                        .getText());
            }
        }
        return 0;
    }

    @SuppressWarnings("SameParameterValue")
    public static boolean elCssClassContains(SelenideElement el, String cssClass) {
        List<String> list = Arrays.asList((Objects.requireNonNull(el.getAttribute("class")).split(" ")));
        return list.contains(cssClass);
    }



}
