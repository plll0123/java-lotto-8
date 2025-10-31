package lotto.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import lotto.Lotto;
import lotto.strategy.RandomNumbersGenerateStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomNumbersGenerateStrategyTest {

    @Test
    @DisplayName("로또 번호 생성 시 1~45 범위의 숫자 6개를 반환한다")
    void 로또_번호_규칙_테스트() {
        int repeatCount = 10000;
        IntStream.range(0, repeatCount)
                .forEach(i -> {
                    var numbersGenerator = new RandomNumbersGenerateStrategy();
                    var numbers = numbersGenerator.getNumbers();
                    assertThat(numbers)
                            .hasSize(Lotto.LOTTO_COUNT)
                            .doesNotHaveDuplicates()
                            .allSatisfy(number -> assertThat(number).isBetween(Lotto.MIN_LOTTO_NUMBER, Lotto.MAX_LOTTO_NUMBER));
                });
    }

}