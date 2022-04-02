package pageObject;

import com.codeborne.selenide.Condition;
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
    private static final String XPATH_PAGINATION = "//mvid-pagination";
    private static final String XPATH_PRODUCTS_LAYOUT = "//mvid-plp-product-cards-layout";

    @FindBy(tagName = "mvid-product-list")
    private static SelenideElement productListContainer;

    @FindBy(className = "plp__filters")
    private static SelenideElement filterContainer;


    @CanIgnoreReturnValue
    public static ListingPage getPage() {
        return page(ListingPage.class);
    }

    public static SelenideElement getProductListContainer() {
        return productListContainer;
    }

    public static SelenideElement getFilterContainer() {
        return filterContainer;
    }

    public static ElementsCollection getTitlesCollection() {
        return $$x(XPATH_ALL_TITLES);
    }

    public static boolean titlesAllMatch(String text) {
       return getTitlesCollection().texts().stream()
                .allMatch(el->el.toLowerCase(Locale.ROOT)
                        .contains(text));
    }

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

    public static void scrollAllProductCards() {
        int time = 1000;
        Common.waitResultBecomeStableWhileDoingSmth(
                ()-> getTitlesCollection().size(),
                ()-> {
                    int num = getTitlesCollection().size()-1;
                    getTitlesCollection().get(num).scrollIntoView(false).shouldBe(Condition.visible);},
                time);
    }

    private static List<SelenideElement> getPagesList() { return $$x(XPATH_PAGINATION + "//li");}

    @CanIgnoreReturnValue
    private static int pages(String command) {
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
