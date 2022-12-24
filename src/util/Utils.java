package util;

import java.util.Random;

public class Utils {
    private static final String[] CHARACTERS = {"A", "C", "T", "G"};
    private static final String[] IDENTIFIERS_P1 = {"U", "F1", "D", "K", "F2", "B", "C", "A1", "A", "A2"};
    private static final String[] IDENTIFIERS_COUNTRY = {"BR", "BE", "FR", "CD", "AR", "TZ", "KR", "UG", "KE", "CM", "TD", "NL", "CO", "US", "UA", "JP", "RU", "MM", "UY", "EC", "GA", "YE", "AU", "TH", "BW", "ZA", "TZ", "IL", "ZM", "CN", "IN", "GE", "SE", "RW", "SN"};
    private static final Random RM = new Random();

    /**
     * Generates a random genome sequence with a ginven length
     *
     * @param length the length of the sequence
     * @return a random genome sequecne
     */
    public static String generateSequence(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARACTERS[RM.nextInt(CHARACTERS.length)]);
        }
        return stringBuilder.toString();
    }

    /**
     * Generates a random name / identifier for a genome
     *
     * @return the name / identifier
     */
    public static String generateIdentifier() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(">");
        for (int i = 0; i < 4; i++) {
            stringBuilder.append(RM.nextInt(10));
        }
        stringBuilder.append(".");
        stringBuilder.append(IDENTIFIERS_P1[RM.nextInt(IDENTIFIERS_P1.length)]);
        stringBuilder.append(".");
        stringBuilder.append(IDENTIFIERS_COUNTRY[RM.nextInt(IDENTIFIERS_COUNTRY.length)]);
        stringBuilder.append(".");
        for (int i = 0; i < RM.nextInt(4) + 3; i++) {
            stringBuilder.append(RM.nextInt(9));
        }
        return stringBuilder.toString();
    }
}
