package ru.netology.aqa.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {}

    @Value
    public static class LoginCredentials {
        String username;
        String password;
    }

    public static LoginCredentials getVasyaCreds() {
        return new LoginCredentials("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(LoginCredentials loginCredentials) {
        return new VerificationCode("12345");
    }

    @Value
    public static class Card {
        String number;
        int balance;

        public String getMaskedNumber() {
            return "**** **** **** " + number.substring(number.length() - 4);
        }
    }

    public static Card getCard0001() {
        return new Card("5559 0000 0000 0001", 10_000);
    }

    public static Card getCard0002() {
        return new Card("5559 0000 0000 0002", 10_000);
    }
}
