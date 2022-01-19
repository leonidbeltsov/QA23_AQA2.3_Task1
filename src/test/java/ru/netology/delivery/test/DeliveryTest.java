package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.*;

class DeliveryTest {

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
//        UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[data-test-id='city'] [class='input__control']").setValue(generateCity("ru"));
//        Стираем и вводим дату
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
//        Вводими имя
        $("[data-test-id='name'] [class='input__control']").setValue(generateName("ru"));
//        Вводим номер телефона
        $("[data-test-id='phone'] [class='input__control']").setValue(generatePhone("ru"));
//        Ставим галочку в чек-боксе
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
//        Кликаем по кнопке "Запланировать"
        $(".form-field .button").click();
//        Должно появиться всплывающее окно подтверждения
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + firstMeetingDate));

//        Стираем и вводим новое значение даты
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
//        Кликаем по кнопке "Запланировать"
        $(".form-field .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
//        Кликаем по кнопке "Перепланировать"
        $("[data-test-id='replan-notification'] *.button")
                .click();
//        Содержащее текст "Встреча успешно запланирована на (указана новая дата)"
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
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе
        $("[data-test-id='city'] [class='input__control']").setValue(validUser.getCity());
//        Стираем и вводим дату
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
//        Вводими имя
        $("[data-test-id='name'] [class='input__control']").setValue(validUser.getName());
//        Вводим номер телефона
        $("[data-test-id='phone'] [class='input__control']").setValue(validUser.getPhone());
//        Ставим галочку в чек-боксе
        $("[data-test-id='agreement'] [class='checkbox__box']").click();
//        Кликаем по кнопке "Запланировать"
        $(".form-field .button").click();
//        Должно появиться всплывающее окно подтверждения
        $("[data-test-id='success-notification'] [class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + firstMeetingDate));

//        Стираем и вводим новое значение даты
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
//        Кликаем по кнопке "Запланировать"
        $(".form-field .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
//        Кликаем по кнопке "Перепланировать"
        $("[data-test-id='replan-notification'] *.button")
                .click();
//        Содержащее текст "Встреча успешно запланирована на (указана новая дата)"
        $("[data-test-id=\"success-notification\"] div.notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на" + " " + secondMeetingDate));
    }
}
