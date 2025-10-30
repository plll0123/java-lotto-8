package util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ConverterTest {

    @Test
    void 문자를_숫자로_변환() {
        Converter converter = new Converter();
        List<Integer> numbers = Stream.of(" 1", "1 ", "1")
                .map(converter::stringToInteger)
                .toList();
        assertThat(numbers)
                .hasSize(3)
                .containsOnly(1);

        assertThatThrownBy(() -> converter.stringToInteger("1 1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.REQUIRE_NUMBER);
    }

    @Test
    void 문자를_숫자_배열로_반환() {
        Converter converter = new Converter();
        List<Integer> numbers = converter.stringToIntegers("1,2 , 3 , 4,5");
        assertThat(numbers)
                .hasSize(5)
                .contains(1, 2, 3, 4, 5);
    }

}