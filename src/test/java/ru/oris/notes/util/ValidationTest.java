package ru.oris.notes.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    @Test
    void validatesCredentials() {
        assertNull(Validation.login("student_01"));
        assertNotNull(Validation.login("x"));
        assertNull(Validation.password("correct horse"));
        assertNotNull(Validation.password("short"));
    }

    @Test
    void validatesRequiredNoteFields() {
        assertNull(Validation.note("Заголовок", "Текст"));
        assertNotNull(Validation.note("", "Текст"));
        assertNotNull(Validation.note("Заголовок", " "));
    }
}
