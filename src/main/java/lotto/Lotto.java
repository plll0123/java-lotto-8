package lotto;

import java.util.List;
import util.ErrorMessage;

public class Lotto {

    public static final int AMOUNT = 1000;

    public static final int MIN_LOTTO_NUMBER = 1;

    public static final int MAX_LOTTO_NUMBER = 45;

    public static final int LOTTO_COUNT = 6;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    private void validate(List<Integer> numbers) {
        numbers = numbers.stream()
                .distinct()
                .toList();
        if (numbers.size() != 6) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER);
        }
        boolean exceedRange = numbers.stream().anyMatch(n -> n < MIN_LOTTO_NUMBER || n > MAX_LOTTO_NUMBER);
        if (exceedRange) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE);
        }
    }

}
