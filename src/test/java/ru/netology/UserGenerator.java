package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UserGenerator {
    public static UserData generateUser() {
        String city = generateCity();
        String name = generateName();
        String phone = generatePhone();
        UserData user = new UserData(city, name, phone);
        return user;
    }

    public static String generateCity() {
        String[] cities = new String[]{"Абакан", "Барнаул", "Воронеж", "Новосибирск", "Москва"};
        Faker faker = new Faker();
        int index = faker.number().numberBetween(0, cities.length - 1);
        String city = cities[index];
        return city;
    }

    public static String generateDate(int shiftDays) {
        String meetDate = LocalDate.now().plusDays(shiftDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return meetDate;
    }

    public static String generateName() {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone() {
        Faker faker = new Faker(new Locale("ru"));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }
}
