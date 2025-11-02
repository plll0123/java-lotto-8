package lotto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lotto lotto = (Lotto) o;
        return Objects.equals(numbers, lotto.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numbers);
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    private void validate(List<Integer> numbers) {
        numbers.forEach(Lotto::validateRange);
        if (numbers.size() != LOTTO_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_LOTTO_NUMBER);
        }
        long distinctSize = numbers.stream()
                .distinct()
                .count();
        if (distinctSize != LOTTO_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_DUPLICATE);
        }
    }

    public static void validateRange(int number) {
        if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_RANGE);
        }
    }

}
