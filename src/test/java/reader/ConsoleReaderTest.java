package reader;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import util.ErrorMessage;

class ConsoleReaderTest {

    private String source;

    private final Reader reader = new ConsoleReader() {
        @Override
        protected String doRead() {
            return source;
        }
    };

    @AfterEach
    void tearDown() {
        source = "";
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 입력값은_null이나_빈문자열일수_없다(String source) {
        this.source = source;
        assertThatThrownBy(reader::read)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.EMPTY_STRING);
    }

}