package pageObject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.codeborne.selenide.Selenide.page;

public class WishPage {

    static {getPage();}

    @FindBy(tagName = "h1")
    private static SelenideElement h1;

    @FindBy(tagName = "h3")
    private static ElementsCollection productTitles;

    public static SelenideElement getH1() {
        return h1;
    }

    @CanIgnoreReturnValue
    public static WishPage getPage() {
        return page(WishPage.class);
    }

    public static List<String> getProductTitles() {
        return productTitles.texts();
    }
}
