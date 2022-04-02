package hooks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.visible;

public class PageHooks {
    public static void shouldBeVisible(SelenideElement el) {
        Objects.requireNonNull(el).shouldBe(visible);
    }

    public static void shouldNotBeVisible(SelenideElement el) {
        Objects.requireNonNull(el).shouldNotBe(visible);
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
        Objects.requireNonNull(el).shouldBe(visible).click();
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

    public static void scrollIntoView(SelenideElement el) {
        Objects.requireNonNull(el).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"center\"}");
    }

}
