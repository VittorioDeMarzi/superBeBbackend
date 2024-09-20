package de.supercode.superBnB.entities;

import java.security.SecureRandom;

public class GenerateBookingsNumber {
    private static final String NUMERIC_STRING = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomNumeric(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(NUMERIC_STRING.length());
            result.append(NUMERIC_STRING.charAt(index));
        }
        return result.toString();
    }
}
