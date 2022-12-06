package util;

import java.util.Random;

public class Utils {
    private static final String[] CHARACTERS = {"A", "C", "T", "G"};
    private static final Random RM = new Random();

    public static String generateSequence(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARACTERS[RM.nextInt(CHARACTERS.length)]);
        }
        return stringBuilder.toString();
    }
}
