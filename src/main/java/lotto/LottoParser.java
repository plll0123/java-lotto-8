package lotto;

import java.util.Arrays;
import java.util.List;
import util.ErrorMessage;

public class LottoParser {

    public static final String DELIMITER = ",";

    public int sourceToNumber(String source) {
        try {
            return Integer.parseInt(source.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.REQUIRE_NUMBER);
        }
    }

    public List<Integer> sourceToNumbers(String source) {
        return Arrays.stream(source.split(DELIMITER))
                .map(this::sourceToNumber)
                .toList();
    }

}
