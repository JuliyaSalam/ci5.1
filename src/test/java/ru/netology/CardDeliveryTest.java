package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
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

    @BeforeEach

    public void setup() {
        open("http://localhost:9999/");
    }

    @Test
    public void happyPath() {
        UserData user = UserGenerator.generateUser(6);
        form.$x(".//span[@data-test-id='city']//input").val(user.getCity());
        form.$x(".//span[@data-test-id='date']//input[@class='input__control']").val(user.getDate());
        form.$x(".//span[@data-test-id='name']//input").val(user.getName());
        form.$x(".//span[@data-test-id='phone']//input").val(user.getPhone());
        form.$x(".//label[@data-test-id='agreement']").click();
        form.$x(".//button//ancestor::span[contains(text(), 'Запланировать')]").click();

        success.should(Condition.visible, Duration.ofSeconds(15));
        success.$x(".//div[@class='notification__content']").should(text("Встреча успешно запланирована на " + user.getDate()));
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
