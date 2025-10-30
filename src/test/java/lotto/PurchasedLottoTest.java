package lotto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class PurchasedLottoTest {

    @Test
    void 등수별_당첨횟수_구하기() {
        Lotto winningLotto = new Lotto(1, 2, 3, 4, 5, 6);

        PurchasedLotto purchasedLotto = new PurchasedLotto(List.of(
                new Lotto(1, 2, 3, 8, 9, 10),
                new Lotto(1, 2, 3, 4, 5, 10),
                new Lotto(1, 2, 3, 4, 5, 9),
                new Lotto(1, 2, 3, 4, 5, 6)
        ));
        assertThat(purchasedLotto.getWinningDetails(winningLotto, 9))
                .containsEntry(Prize.FIFTH, 1L)
                .containsEntry(Prize.THIRD, 1L)
                .containsEntry(Prize.SECOND, 1L)
                .containsEntry(Prize.FIRST, 1L);
    }

}