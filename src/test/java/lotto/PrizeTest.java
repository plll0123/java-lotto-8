package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PrizeTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            FIFTH| 3| false| 3개 일치 (5,000원)| 5_000
            FOURTH| 4| false| 4개 일치 (50,000원)| 50_000
            THIRD| 5| false| 5개 일치 (1,500,000원)| 1_500_000
            SECOND| 5| true| 5개 일치, 보너스 볼 일치 (30,000,000원)| 30_000_000
            FIRST| 6| false| 6개 일치 (2,000,000,000원)| 2_000_000_000
            """, delimiter = '|')
    void 등수별_속성_검사(
            Prize prize,
            int matchCount,
            boolean bonus,
            String description,
            int value
    ) {
        assertThat(prize)
                .extracting(Prize::getMatchCount, Prize::isNeedBonus, Prize::getDescription, Prize::getValue)
                .contains(matchCount, bonus, description, value);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 7})
    void 맞춘_번호수에_해당하는_등수가_없으면_예외가_발생한다(int matchCount) {
        assertThatThrownBy(() -> Prize.from(matchCount, false)).isInstanceOf(IllegalArgumentException.class);
    }

}