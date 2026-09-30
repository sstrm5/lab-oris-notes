package ru.oris.notes.util;

public final class Validation {
    private Validation() { }

    public static String login(String value) {
        if (value == null || value.isBlank()) return "Введите логин";
        if (!value.matches("[A-Za-zА-Яа-яЁё0-9_.-]{3,64}"))
            return "Логин: от 3 до 64 символов, только буквы, цифры, точка, дефис и подчёркивание";
        return null;
    }

    public static String password(String value) {
        if (value == null || value.length() < 8) return "Пароль должен содержать не менее 8 символов";
        if (value.length() > 72) return "Пароль должен содержать не более 72 символов";
        return null;
    }

    public static String note(String name, String text) {
        if (name == null || name.isBlank()) return "Введите название заметки";
        if (name.length() > 160) return "Название не должно превышать 160 символов";
        if (text == null || text.isBlank()) return "Введите текст заметки";
        if (text.length() > 100_000) return "Текст заметки слишком длинный";
        return null;
    }
}
