package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import util.ErrorMessage;

class LottoParserTest {

    @Test
    void 문자를_숫자로_변환() {
        LottoParser lottoParser = new LottoParser();
        List<Integer> numbers = Stream.of(" 1", "1 ", "1")
                .map(lottoParser::sourceToNumber)
                .toList();
        assertThat(numbers)
                .hasSize(3)
                .containsOnly(1);

        assertThatThrownBy(() -> lottoParser.sourceToNumber("1 1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.REQUIRE_NUMBER);
    }

    @Test
    void 문자를_숫자_배열로_반환() {
        LottoParser lottoParser = new LottoParser();
        List<Integer> numbers = lottoParser.sourceToNumbers("1,2 , 3 , 4,5");
        assertThat(numbers)
                .hasSize(5)
                .contains(1, 2, 3, 4, 5);
    }

}