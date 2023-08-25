package com.example.prog4.controller.validator.utils;

public class StringValidator {
    public static boolean isNotNullAndNotBlank(String s) {
        return s != null && !s.isBlank();
    }
}
