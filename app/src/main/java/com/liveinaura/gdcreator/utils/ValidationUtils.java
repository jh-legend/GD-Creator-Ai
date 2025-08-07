package com.liveinaura.gdcreator.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(final String password) {
        if (password == null || password.length() < 6) {
            return false;
        }

        int upperCaseCount = 0;
        int specialCharCount = 0;
        int digitCount = 0;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            } else if (!Character.isLetterOrDigit(c)) {
                specialCharCount++;
            }
        }

        return upperCaseCount >= 1 && specialCharCount >= 2 && digitCount >= 2;
    }
}
