package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] [class='input__control']").setValue(generateCity("ru"));
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [class='input__control']").setValue(generateName("ru"));
        $("[data-test-id='phone'] [class='input__control']").setValue(generatePhone("ru"));
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".form-field .button").click();
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + firstMeetingDate));
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(".form-field .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] *.button").click();
        $("[data-test-id=\"success-notification\"] div.notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + secondMeetingDate));
    }

    @Test
    @DisplayName("Should successful plan and replan meeting with validUser")
    void shouldSuccessfulPlanAndReplanMeetingWithValidUser() {
        UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] [class='input__control']").setValue(validUser.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[data-test-id='name'] [class='input__control']").setValue(validUser.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(validUser.getPhone());
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
        $(".form-field .button").click();
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + firstMeetingDate));
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(".form-field .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] *.button").click();
        $("[data-test-id=\"success-notification\"] div.notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + secondMeetingDate));
    }
}

// java -jar ./artifacts/app-card-delivery.jar