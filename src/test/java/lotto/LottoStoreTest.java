package lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lotto.strategy.RandomNumbersGenerateStrategy;
import util.ErrorMessage;

class LottoStoreTest {

    private LottoStore lottoStore;

    @BeforeEach
    void setUp() {
        LottoMachine lottoMachine = new LottoMachine(new RandomNumbersGenerateStrategy());
        lottoStore = new LottoStore(lottoMachine);
    }

    @Test
    void 금액이_천원보다_작다면_에러를_던진다() {
        int source = Lotto.AMOUNT - 1;
        assertThatThrownBy(() -> lottoStore.sell(source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_AMOUNT);
    }

    @Test
    void 금액이_1000_단위가_아니라면_예외가_발생한다() {
        int source = Lotto.AMOUNT + 1;
        assertThatThrownBy(() -> lottoStore.sell(source))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.INVALID_AMOUNT_UNIT);
    }

    @Test
    void 로또_구매() {
        int count = 1;
        int source = Lotto.AMOUNT * count;
        PurchasedLotto purchasedLotto = lottoStore.sell(source);
        assertThat(purchasedLotto.values()).hasSize(count);
    }

}