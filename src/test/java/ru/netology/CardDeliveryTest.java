package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    SelenideElement form = $x("//form");
    SelenideElement success = $x("//div[@data-test-id='success-notification']");
    SelenideElement replan = $x(".//div[@data-test-id='replan-notification']");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void happyPath() {
        UserData user = UserGenerator.generateUser();
        form.$x(".//span[@data-test-id='city']//input").val(user.getCity());
        form.$x(".//span[@data-test-id='date']//input[@class='input__control']").val(UserGenerator.generateDate(3));
        form.$x(".//span[@data-test-id='name']//input").val(user.getName());
        form.$x(".//span[@data-test-id='phone']//input").val(user.getPhone());
        form.$x(".//label[@data-test-id='agreement']").click();
        form.$x(".//button//ancestor::span[contains(text(), 'Запланировать')]").click();

        success.should(Condition.visible, Duration.ofSeconds(15));
        success.$x(".//div[@class='notification__content']").should(text("Встреча успешно запланирована на " + UserGenerator.generateDate(3)));
        success.$x(".//button").click();
        success.should(hidden);

        form.$x(".//span[@data-test-id='date']//input[@class='input__control']").val(UserGenerator.generateDate(4));
        form.$x(".//button//ancestor::span[contains(text(), 'Запланировать')]").click();

        replan.should(Condition.visible, Duration.ofSeconds(15));
        replan.$x(".//span[contains(text(), 'Перепланировать')]//ancestor::button").click();
        replan.should(hidden);

        success.should(Condition.visible, Duration.ofSeconds(15));
        success.$x(".//div[@class='notification__content']").should(text("Встреча успешно запланирована на " + UserGenerator.generateDate(4)));
        success.$x(".//button").click();
        success.should(hidden);
    }

}
