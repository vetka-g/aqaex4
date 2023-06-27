package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryApplicationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    private String setDate(int addDays) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(addDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = futureDate.format(formatter);
        return formattedDate;
    }

    @Test
    void shouldFillTheFormCorrect() {
        String planDate = setDate(4);
        $("[data-test-id=city] .input__control").setValue("Омск");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planDate), Duration.ofSeconds(15)).shouldBe(visible);
    }
    @Test
    void shouldFillCityWrong() {
        String planDate = setDate(4);
        $("[data-test-id=city] .input__control").setValue("Нью-Йорк");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNotFillCity() {
        String planDate = setDate(5);
        $("[data-test-id=city] .input__control").setValue("");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFillDateTomorrow() {
        String planDate = setDate(1);
        $("[data-test-id=city] .input__control").setValue("Томск");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldFillNameInLatin() {
    String planDate = setDate(3);
    $("[data-test-id=city] .input__control").setValue("Тверь");
    $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
    $("[data-test-id=name] .input__control").setValue("Baba Yaga");
    $("[data-test-id=phone] .input__control").setValue("+79030000000");
    $("[data-test-id=agreement]").click();
    $(".button").click();
    $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
}

    @Test
    void shouldNotFillName() {
        String planDate = setDate(3);
        $("[data-test-id=city] .input__control").setValue("Тверь");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldFillPhoneWithError() {
        String planDate = setDate(3);
        $("[data-test-id=city] .input__control").setValue("Тверь");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+7903000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotFillPhone() {
        String planDate = setDate(4);
        $("[data-test-id=city] .input__control").setValue("Тверь");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldCheckTheBox() {
        String planDate = setDate(4);
        $("[data-test-id=city] .input__control").setValue("Самара");
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] .input__control").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] .input__control").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=agreement]").shouldBe(visible);

    }
}
