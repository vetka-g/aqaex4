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
        $("[data-test-id=city] input").setValue("Омск");
        $("[data-test-id=date] input").doubleClick().sendKeys(planDate);
        $("[data-test-id=name] input").setValue("Лесник Трудовой-Заядлый");
        $("[data-test-id=phone] input").setValue("+79030000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planDate), Duration.ofSeconds(14)).shouldBe(visible);
    }


}
