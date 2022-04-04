package pageObject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.*;

public class CartPage {

    static {getPage();}

    @FindBy(xpath = "//*[@class=  'main-holder' ] //*[contains(@class,  'c-header-checkout__logo'  )]")
    private static SelenideElement myCart;

    @FindBy(xpath = "//*[@value=  'Перейти к оформлению'  ]")
    private static SelenideElement checkoutBtn;

    @FindBy(className = "c-cost-line__title-wrap")
    private static SelenideElement costTitle;

    @FindBy(className = "c-cost-line__text")
    private static SelenideElement totalActualPrice;

    @FindBy(xpath = "//*[contains(text(), 'Ваша корзина пока пуста' )]")
    private static SelenideElement emptyCart;


    private static final String XPATH_ITEM_CONTAINER = "//*[contains(@class,  'c-cart-item__wrapper'  )]";
    private static final String XPATH_ITEM_TITLE = "//*[contains(@class,  'c-cart-item__title'  )]";
    public static final String XPATH_ITEM_ACTUAL_PRICE = "//*[@class=  'c-cart-item__price'  ]";

    @CanIgnoreReturnValue
    public static CartPage getPage() {
        return page(CartPage.class);
    }

    public static SelenideElement getElem(String title) {
        switch (title) {
            case "Моя корзина": return myCart;
            case "Перейти к оформлению": return checkoutBtn;
            case "В корзине товар": return costTitle;
            case "Общая актуальная цена": return totalActualPrice;
            default: return null;
        }
    }

    public static boolean isEmptyCart() {
        return emptyCart.isDisplayed();
    }

    public static String getFirstItemActualPrice() {
        String xpath = XPATH_ITEM_CONTAINER + XPATH_ITEM_ACTUAL_PRICE;
        return $x(xpath).getText();
    }

    public static boolean isInCartProductList(String title) {
        String xpath = XPATH_ITEM_CONTAINER + XPATH_ITEM_TITLE;
        ElementsCollection elems = $$x(xpath);
        return !elems.filter(Condition.exactText(title)).isEmpty();
    }


}
