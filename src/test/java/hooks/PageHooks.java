package hooks;

import com.codeborne.selenide.*;
import utils.Common;

import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**Класс-посредник между Steps и PageObjects.
 *
 * @author Maksim_Kachaev*/
public class PageHooks {

    public static void openLink(String link) {
        Selenide.open(link);
    }

    public static void shouldBeVisible(SelenideElement el) {
        Objects.requireNonNull(el).scrollIntoView(true).shouldBe(visible);
    }

    public static void shouldNotBeVisible(SelenideElement el) {
        Objects.requireNonNull(el).shouldNotBe(visible);
    }

    public static void shouldBeAtrr(SelenideElement el, String atr, String value) {
        Objects.requireNonNull(el).shouldBe(Condition.attribute(atr, value));
    }

    public static void shouldBeExactText(SelenideElement el, String text) {
        Objects.requireNonNull(el).shouldBe(Condition.exactText(text));
    }

    public static void collectionShouldBeVisibleNotEmpty(ElementsCollection elems) {
        elems.shouldBe(CollectionCondition.sizeGreaterThan(0));
        elems.forEach(el->el.shouldBe(visible));
    }

    public static void reloadPage() {
        Selenide.refresh();
    }

    public static void checkElemActive (SelenideElement el) {
        Objects.requireNonNull(el)
                .shouldBe(visible)
                .shouldNotBe(cssClass("disabled"));
    }

    public static void checkElemNotActive (SelenideElement elem) {
        SelenideElement el = Objects.requireNonNull(elem);
        if (Objects.requireNonNull(el.getAttribute("class")).contains("disabled"))
            el.shouldBe(visible, cssClass("disabled"));
        else if (Objects.equals(el.getAttribute("disabled"), "true"))
            el.shouldBe(visible);
    }

    public static boolean isDisplayed(SelenideElement el) {
        return Objects.requireNonNull(el).isDisplayed();
    }

    public static void click(SelenideElement el) {
        SelenideElement elem = Objects.requireNonNull(el);
        scrollIntoView(elem);
        elem.shouldBe(visible).click();
    }

    public static void send(SelenideElement el, String text) {
        Objects.requireNonNull(el).sendKeys(text);
    }

    public static String getText(SelenideElement el) {
       return Objects.requireNonNull(el).getText();
    }

    public static String getPageLink() {
        return WebDriverRunner.driver().url();
    }

    /**Возвращает текст H1 заголовка страницы*/
    public static String getH1Text() {
        return getTextNode($x("//h1"));
    }

    /**Возвращает текст конкретного веб элемента без детей*/
    private static String getTextNode(SelenideElement el)
    {
        String text = el.getText().trim();
        List<SelenideElement> children = el.$$x("./*");
        for (SelenideElement child : children)
        {
            text = text.replaceFirst(child.getText(), "").trim();
        }
        return text;
    }

    public static void scrollProductCardsWishOrComparePages() {
        int time = 1000;
        Common.waitResultBecomeStableWhileDoingSmth(
                ()-> $$x("//h3").size(),
                ()-> $$x("//h3").last().scrollIntoView(true),
                time
        );
        $x("//h1").scrollIntoView(false);
    }

    public static void scrollIntoView(SelenideElement el) {
        Objects.requireNonNull(el).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
    }

}
