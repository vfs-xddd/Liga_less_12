package pageObject;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class ProductCard {
    private static final String XPATH_PATTERN_TITLE = "//mvid-plp-product-title//*[contains(text(),  '?'  )]";
    private static final String XPATH_TITLE_TO_MAIN_PRICE = "/following::*[@class=  'price__main-value'  ][1]";
    private static final String XPATH_TITLE_TO_ADD_CART = "/following::*[@title= 'Добавить в корзину'  ][1]";
    private static final String XPATH_TITLE_TO_WISH_LIST = "/following::*[@title= 'Добавить в избранное'  ][1]";
    private static final String XPATH_TITLE_TO_COMPARES = "/following::*[@title= 'Добавить в сравнение'  ][1]";
    private final String titleCard;
    private final String xpathTitle;
    private final String xpathMainPrice;
    private final String xpathAddCart;
    private final String xpathWishList;
    private final String xpathCompares;

    ProductCard(String title) {
      xpathTitle = XPATH_PATTERN_TITLE.replace("?", title);
      titleCard = $x(xpathTitle).getText();
      xpathMainPrice = xpathTitle + XPATH_TITLE_TO_MAIN_PRICE;
      xpathAddCart = xpathTitle + XPATH_TITLE_TO_ADD_CART;
      xpathWishList = xpathTitle + XPATH_TITLE_TO_WISH_LIST;
      xpathCompares = xpathTitle + XPATH_TITLE_TO_COMPARES;
    }

    public String title() {
        return titleCard;
    }

    public SelenideElement getTitle() {
        return $x(xpathTitle);
    }

    public SelenideElement getMainPrice() {
        return $x(xpathMainPrice);
    }

    public SelenideElement getAddCartBtn() {
        return $x(xpathAddCart);
    }

    public SelenideElement getWishListBtn() {
        return $x(xpathWishList);
    }

    public SelenideElement getComparesBtn() {
        return $x(xpathCompares);
    }

    @Override
    public String toString() {
        return titleCard;
    }

}
