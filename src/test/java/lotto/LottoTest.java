package lotto;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import util.ErrorMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @Test
    void 로또_번호의_개수가_6개가_아니라면_예외를_던진다() {
        List.of(
                List.of(1, 2, 3, 4, 5, 6, 7),
                List.of(1, 2, 3, 4, 5)
        ).forEach(e -> {
            assertThatThrownBy(() -> new Lotto(e))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ErrorMessage.INVALID_LOTTO_NUMBER);
        });
    }

    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.LOTTO_NUMBER_DUPLICATE);
    }

    @ParameterizedTest
    @MethodSource("fixtures")
    void 로또_범위가_잘못되면_예외가_발생한다(int number) {
        assertThatThrownBy(() -> new Lotto(List.of(number)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.OUT_OF_RANGE);
    }

    private static Stream<Arguments> fixtures() {
        return Stream.of(
                Arguments.of(Lotto.MIN_LOTTO_NUMBER - 1),
                Arguments.of(Lotto.MAX_LOTTO_NUMBER + 1)
        );
    }

    @Test
    void 로또_상수값_검증() {
        assertThat(Lotto.AMOUNT).isEqualTo(1000);
        assertThat(Lotto.MIN_LOTTO_NUMBER).isEqualTo(1);
        assertThat(Lotto.MAX_LOTTO_NUMBER).isEqualTo(45);
        assertThat(Lotto.LOTTO_COUNT).isEqualTo(6);
    }

    @Test
    void 로또_번호_조회() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(lotto.matchesCount(new Lotto(1, 2, 3, 8, 9, 10))).isEqualTo(3);
        assertThat(lotto.matchesCount(new Lotto(1, 11, 12, 13, 14, 15))).isEqualTo(1);

        assertThat(lotto.contains(1)).isTrue();
        assertThat(lotto.contains(7)).isFalse();
    }

}
