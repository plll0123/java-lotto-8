package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PrizeTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            FIFTH| 3| false| 3개 일치 (5,000)원| 5_000
            FOURTH| 4| false| 4개 일치 (50,000)원| 50_000
            THIRD| 5| false| 5개 일치 (1,500,000)원| 1_500_000
            SECOND| 5| true| 5개 일치, 보너스 볼 일치 (30,000,000)원| 30_000_000
            FIRST| 6| false| 6개 일치 (2,000,000,000)원| 2_000_000_000
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
    @CsvSource(textBlock = """
            1,
            2,
            4, true
            6, true
            7,
            """)
    void 번호_조합에_해당하는_등수가_없으면_예외를_던진다(int matchCount, Boolean bonus) {
        if (bonus == null) {
            bonus = false;
        }
        Boolean _bonus = bonus;
        assertThatThrownBy(() -> Prize.from(matchCount, _bonus)).isInstanceOf(IllegalArgumentException.class);
    }

}