package lotto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.ErrorMessage;

public class Lotto {

    public static final int AMOUNT = 1000;

    public static final int MIN_LOTTO_NUMBER = 1;

    public static final int MAX_LOTTO_NUMBER = 45;

    public static final int LOTTO_COUNT = 6;

    private final List<Integer> numbers;

    public Lotto(int... numbers) {
        this(Arrays.stream(numbers).boxed().toList());
    }

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

    public int matchesCount(Lotto other) {
        Set<Integer> otherLottoNumbers = new HashSet<>(other.getNumbers());
        return (int) numbers.stream()
                .filter(otherLottoNumbers::contains)
                .count();
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    private void validate(List<Integer> numbers) {
        numbers = numbers.stream()
                .distinct()
                .toList();
        if (numbers.size() != LOTTO_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER);
        }
        boolean exceedRange = numbers.stream().anyMatch(n -> n < MIN_LOTTO_NUMBER || n > MAX_LOTTO_NUMBER);
        if (exceedRange) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE);
        }
    }

}
