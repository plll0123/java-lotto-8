package lotto;

import java.util.Arrays;
import java.util.List;
import util.ErrorMessage;

public class Converter {

    public static final String DELIMITER = ",";

    public int stringToInteger(String source) {
        try {
            return Integer.parseInt(source.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.REQUIRE_NUMBER);
        }
    }

    public List<Integer> stringToIntegers(String source) {
        return Arrays.stream(source.split(DELIMITER))
                .map(this::stringToInteger)
                .toList();
    }

}
