package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryApplicationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldFillTheFormCorrect() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ромашка Полевая-Лечебная");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void shouldFillNameWithLatin() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Romashka Polevaya");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldNotFillName() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldFillThePhoneWithError() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ромашка Полевая");
        form.$("[data-test-id=phone] input").setValue("+7912345678");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotFillThePhone() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ромашка Полевая");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldCheckTheBox() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ромашка Полевая");
        form.$("[data-test-id=phone] input").setValue("+7912345678");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $("[data-test-id=agreement]").shouldBe(visible);

    }
}
