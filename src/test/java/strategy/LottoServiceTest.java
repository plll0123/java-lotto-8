//package app;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import lotto.PaymentLotto;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import lotto.LottoParser;
//import reader.Reader;
//
//class LottoServiceTest {
//
//    private Reader reader;
//    private LottoParser lottoParser = new LottoParser();
//    private LottoStore lottoStore = new LottoStore(new RandomNumbersGenerateStrategy());
//    private LottoService lottoService;
//
//    @Test
//    void 로또_구매() {
//        reader = () -> "3000";
//        lottoService = new LottoService(reader, lottoParser, lottoStore);
//
//        PaymentLotto paymentLotto = lottoService.buyLotto();
//        assertThat(paymentLotto.lottos()).hasSize(3);
//    }
//
//    @ParameterizedTest
//    @CsvSource(textBlock = """
//            999, [ERROR] 금액은 천원 이상이어야 합니다.
//            1001, [ERROR] 금액은 천원단위여야 합니다.
//            """)
//    void 금액이_천원단위가_아니라면_예외를_던진다(int source, String errorMessage) {
//        reader = () -> String.valueOf(source);
//        lottoService = new LottoService(reader, lottoParser, lottoStore);
//
//        assertThatThrownBy(() -> lottoService.buyLotto())
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage(errorMessage);
//    }
//
//}